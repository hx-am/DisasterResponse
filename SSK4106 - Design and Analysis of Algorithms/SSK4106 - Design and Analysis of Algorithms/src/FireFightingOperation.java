// To find shortest path for nearest fire fighters to get to incident place

import java.awt.Desktop;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class FireFightingOperation extends Application{
	
	private Scene scene;
	
	private Button btMap = new Button("Show Real Map");
	private Button btGraph = new Button("Show Graph Model");
	
	private Label lbPlace = new Label("Which place is now on fire ?");
	private ToggleGroup group;
	private RadioButton rbAEON = new RadioButton("AEON Mall Bukit Indah");
	private RadioButton rbSutera = new RadioButton("Sutera Mall");
	private RadioButton rbParadigm = new RadioButton("Paradigm Mall");
	private RadioButton rbAngsana = new RadioButton("Angsana Johor Bahru Mall");
	private RadioButton rbMidValley = new RadioButton("MidValley SouthKey");
	private RadioButton rbIKEA = new RadioButton("IKEA Tebrau");
	
	private Label lbFree = new Label("Which fire station(s) is/are currently available ?");
	private CheckBox cbSkudai = new CheckBox("Skudai Fire Station");
	private CheckBox cbIP = new CheckBox("Iskandar Puteri Fire Station");
	private CheckBox cbLarkin = new CheckBox("Larkin Fire Station");
	private CheckBox cbTebrau = new CheckBox("Tebrau Fire Station");
	private CheckBox cbJJ = new CheckBox("Johor Jaya Fire Station");
	
	private Label lbRoute = new Label("Best Routes Ranked According to Shortest Paths Available: ");
	private TextFlow tfRoute = new TextFlow();
	private ScrollPane spane;
	
	private Button btReset = new Button("Reset");
	private Button btStart = new Button("Plan The Route");
	
	private int map[][];
	private ArrayList<ArrayList<Integer>> distances = new ArrayList<>();
	private ArrayList<ArrayList<String>> paths = new ArrayList<>();
	private int fire = -1;
	private ArrayList<Integer> free = new ArrayList<>();
	private String[] nodes;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {
		
		// Read map from file
		File fileIn1 = new File("C:\\Users\\User\\OneDrive\\Documents\\Eclipse\\SSK4106 - Design and Analysis of Algorithms\\src\\maps\\city_map1.txt");
		File fileIn2 = new File("C:\\Users\\User\\OneDrive\\Documents\\Eclipse\\SSK4106 - Design and Analysis of Algorithms\\src\\maps\\city_name1.txt");
		if (!fileIn1.exists() || !fileIn2.exists())
		{
			AlertBox.display("Error", fileIn1.getName() + " is not available !");
			System.exit(0);
		}
		else
		{
			// Read adjacent distances
			Scanner input1 = new Scanner(fileIn1);
			ArrayList<Integer> temp1 = new ArrayList<>();
			while (input1.hasNext())
				temp1.add(input1.nextInt());
			input1.close();
			int count = 0;
			map = new int[(int) Math.pow(temp1.size(), 0.5)][(int) Math.pow(temp1.size(), 0.5)];
			for (int i = 0; i < map.length; i++)
			{
				for (int j = 0; j < map[i].length; j++)
					map[i][j] = temp1.get(count++);
			}
			// Read place names
			Scanner input2 = new Scanner(fileIn2);
			ArrayList<String> temp2 = new ArrayList<>();
			while (input2.hasNext())
				temp2.add(input2.nextLine());
			input2.close();
			nodes = new String[temp2.size()];
			for (int i = 0; i < temp2.size(); i++)
				nodes[i] = temp2.get(i);
			
			// To calculate shortest paths to all destination from all sources
			for (int i = 0; i < map.length; i++)
			{
				Dijkstra source1 = new Dijkstra(map, i, nodes);
				// Add shortest distances from one source into arrayList accordingly
				distances.add(source1.getDistances());
				// Add shortest paths from one source into arrayList accordingly
				paths.add(source1.getPath());
			}
			
			BorderPane mainPane = new BorderPane();
			mainPane.setTop(getVbox());
			mainPane.setCenter(getGPane());
			mainPane.setBottom(getHbox(primaryStage));
			
			scene = new Scene(mainPane, 550, 790);
			primaryStage.setTitle("Fire and Rescue Department Headquarters Johor");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		}
		
	}
	
	private VBox getVbox() {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(0, 0, 0, 0));
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(5);
		
		// Title
		Text text = new Text("Area Distribution");
		text.setFont(Font.font("", FontWeight.NORMAL, 25));
		vbox.getChildren().add(text);
		// Icon
		Image icon = new Image("C:\\Users\\User\\OneDrive\\Documents\\Eclipse\\SSK4106 - Design and Analysis of Algorithms\\src\\images\\Bomba Logo.png");
		vbox.getChildren().add(new ImageView(icon));
		vbox.getChildren().add(new Text());
		// Show map button
		btMap.setPrefWidth(130);
		btMap.setOnAction(e -> {
			try {
				Desktop.getDesktop().open(new File("C:\\Users\\User\\OneDrive\\Documents\\Eclipse\\SSK4106 - Design and Analysis of Algorithms\\src\\images\\Real Map.png"));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		btMap.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.ENTER)
			{
				try {
					Desktop.getDesktop().open(new File("C:\\Users\\User\\OneDrive\\Documents\\Eclipse\\SSK4106 - Design and Analysis of Algorithms\\src\\images\\Real Map.png"));
					e.consume();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		vbox.getChildren().add(btMap);
		// Show graph button
		btGraph.setPrefWidth(130);
		btGraph.setOnAction(e -> {
			try {
				Desktop.getDesktop().open(new File("C:\\Users\\User\\OneDrive\\Documents\\Eclipse\\SSK4106 - Design and Analysis of Algorithms\\src\\images\\City Graph.png"));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		btGraph.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.ENTER)
			{
				try {
					Desktop.getDesktop().open(new File("C:\\Users\\User\\OneDrive\\Documents\\Eclipse\\SSK4106 - Design and Analysis of Algorithms\\src\\images\\City Graph.png"));
					e.consume();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		vbox.getChildren().add(btGraph);
		
		return vbox;
	}
	
	private GridPane getGPane() {
		
		/* Add items into pane */
		
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(20, 15, 20, 15));
		pane.setHgap(50);
		pane.setVgap(6);
		
		
		
		// To choose place which is on fire 
		lbPlace.setFont(Font.font("", FontWeight.NORMAL, 14));
		// Group the buttons together
		group = new ToggleGroup();
		rbAEON.setToggleGroup(group);
		rbSutera.setToggleGroup(group);
		rbParadigm.setToggleGroup(group);
		rbAngsana.setToggleGroup(group);
		rbMidValley.setToggleGroup(group);
		rbIKEA.setToggleGroup(group);
		pane.add(lbPlace,  0, 0);
		pane.add(rbAEON, 0, 2);
		pane.add(rbSutera, 0, 3);
		pane.add(rbParadigm, 0, 4);
		pane.add(rbAngsana, 0, 5);
		pane.add(rbMidValley, 0, 6);
		pane.add(rbIKEA, 0, 7);
		
		// To choose fire station(s) which is/are currently available
		lbFree.setFont(Font.font("", FontWeight.NORMAL, 14));
		pane.add(lbFree,  0, 10);
		cbSkudai.setTextFill(Color.RED);
		pane.add(cbSkudai, 0, 12);
		cbIP.setTextFill(Color.BROWN);
		pane.add(cbIP, 0, 13);
		cbLarkin.setTextFill(Color.BLUE);
		pane.add(cbLarkin, 0, 14);
		cbTebrau.setTextFill(Color.ORANGE);
		pane.add(cbTebrau, 0, 15);
		cbJJ.setTextFill(Color.GREEN);
		pane.add(cbJJ, 0, 16);
		
		// To show fire station(s) which is/are currently available
		lbRoute.setFont(Font.font("", FontWeight.NORMAL, 14));
		pane.add(lbRoute,  0, 19);
		spane = new ScrollPane(tfRoute);
		spane.setPrefWidth(512);	// Set pref width
		spane.setPrefHeight(150);	// Set pref width
		spane.setMaxWidth(512);		// Set max width
		spane.setMaxHeight(150);	// Set max width
		// Hide scroll bar if it is not needed
		spane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		spane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		// Set background color of spane
		spane.setStyle("-fx-background: rgb(255,255,255);\n -fx-background-color: rgb(0,0,0)");
		pane.add(spane, 0, 21);
		return pane;
		
	}
	
	private HBox getHbox(Stage primaryStage) {
		
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(0, 20, 20, 0));
		hbox.setAlignment(Pos.BASELINE_RIGHT);
		hbox.setSpacing(20);
		
		// Enable reset button only if anything is selected
		btReset.disableProperty().bind(group.selectedToggleProperty().isNull()
				.and(cbSkudai.selectedProperty().not())
				.and(cbIP.selectedProperty().not())
				.and(cbLarkin.selectedProperty().not())
				.and(cbTebrau.selectedProperty().not())
				.and(cbJJ.selectedProperty().not()));
		btReset.setOnAction(e -> {
			try {
				reset();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		btReset.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.ENTER)
			{
				try {
					reset();
					e.consume();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		// Enable start button only if both fire place and fire station(s) are selected
		btStart.disableProperty().bind(group.selectedToggleProperty().isNull().or
				((cbSkudai.selectedProperty().not())
				.and(cbIP.selectedProperty().not())
				.and(cbLarkin.selectedProperty().not())
				.and(cbTebrau.selectedProperty().not())
				.and(cbJJ.selectedProperty().not())));
		btStart.setOnAction((e) -> {
			tfRoute.getChildren().clear();
			calculateRoute(primaryStage);
		});
		btStart.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.ENTER)
			{
				try {
					tfRoute.getChildren().clear();
					calculateRoute(primaryStage);
					e.consume();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		hbox.getChildren().addAll(btReset, btStart);
		
		return hbox;
		
	}
	
	public void calculateRoute(Stage primaryStage) {
		
		fire = -1;
		// Read on fire place
		if (rbAEON.isSelected())
			fire = 5;
		else if (rbSutera.isSelected())
			fire = 6;
		else if (rbParadigm.isSelected())
			fire = 7;
		else if (rbAngsana.isSelected())
			fire = 8;
		else if (rbMidValley.isSelected())
			fire = 9;
		else if (rbIKEA.isSelected())
			fire = 10;
		
		free = new ArrayList<>();
		// Read available fire station
		if (cbSkudai.isSelected())
			free.add(0);
		if (cbIP.isSelected())
			free.add(1);
		if (cbLarkin.isSelected())
			free.add(2);
		if (cbTebrau.isSelected())
			free.add(3);
		if (cbJJ.isSelected())
			free.add(4);
		
		// Sort the fire station according to shortest routes
		int[] sort = new int[free.size()];
		int[] temp = new int[sort.length];
		for (int i = 0; i < sort.length; i++)
		{
			// Store unsorted array to be sorted later
			sort[i] = distances.get(free.get(i)).get(fire);
			// Store for temporary and used later for identifying the sequences
			temp[i] = free.get(i);
		}
		MergeSort ob = new MergeSort();
		ob.sort(sort, temp, 0, sort.length-1);
		
		// Print output
		Text[] text = new Text[free.size()];
		for (int i = 0; i < free.size(); i++)
		{
			String output = "";
			// Print fire station and destination
			output += "From " + nodes[temp[i]] + " to " + nodes[fire] + ": \n";
			// Print the shortest path
			output += "Shortest path: " + paths.get(temp[i]).get(fire) + "\n";
			// Print the shortest distance
			output += "Distance: " + distances.get(temp[i]).get(fire) + " km \n\n";
			text[i] = new Text(output);
			// Set color of text
			if (temp[i] == 0)
				text[i].setFill(Color.RED);
			else if (temp[i] == 1)
				text[i].setFill(Color.BROWN);
			else if (temp[i] == 2)
				text[i].setFill(Color.BLUE);
			else if (temp[i] == 3)
				text[i].setFill(Color.ORANGE);
			else if (temp[i] == 4)
				text[i].setFill(Color.GREEN);
			tfRoute.getChildren().add(text[i]);
		}
		
	}
	
	public void reset() {
		rbAEON.setSelected(false);
		rbSutera.setSelected(false);
		rbParadigm.setSelected(false);
		rbAngsana.setSelected(false);
		rbMidValley.setSelected(false);
		rbIKEA.setSelected(false);
		cbSkudai.setSelected(false);
		cbIP.setSelected(false);
		cbLarkin.setSelected(false);
		cbTebrau.setSelected(false);
		cbJJ.setSelected(false);
		tfRoute.getChildren().clear();
	}
	
}

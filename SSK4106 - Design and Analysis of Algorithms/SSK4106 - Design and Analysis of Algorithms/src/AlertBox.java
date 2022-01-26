import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
	
	public static void display(String title, String message) {
		
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(350);
		
		Label label = new Label();
		label.setText(message);
		Button btClose = new Button("OK");
		btClose.setOnAction(e -> window.close());
		btClose.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.ENTER)
			{
				window.close();
				e.consume();
			}
		});

		
		VBox vbox = new VBox(30);
		vbox.getChildren().addAll(label, btClose);
		vbox.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(vbox, 350, 150);
		window.setScene(scene);
		window.showAndWait();
		
	}
}

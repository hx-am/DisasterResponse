// Merge Sort

public class MergeSort {
	
	// Merges two subarrays of arr[].
    // First subarray is arr[l..m]
    // Second subarray is arr[m+1..r]
    public void merge(int arr[], int[] subArr, int l, int m, int r)
    {
    	// Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;
  
        /* Create temp arrays */
        int L[] = new int[n1];
        int R[] = new int[n2];
        int subL[] = new int [n1];
        int subR[] = new int [n1];
  
        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i)
        {
        	L[i] = arr[l + i];
        	subL[i] = subArr[l + i];
        }
        for (int j = 0; j < n2; ++j)
        {
        	R[j] = arr[m + 1 + j];
        	subR[j] = subArr[m + 1 + j];
        }
  
        /* Merge the temp arrays */
  
        // Initial indexes of first and second subarrays
        int i = 0, j = 0;
  
        // Initial index of merged subarray array
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                subArr[k] = subL[i];
                i++;
            }
            else {
                arr[k] = R[j];
                subArr[k] = subR[j];
                j++;
            }
            k++;
        }
  
        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            arr[k] = L[i];
            subArr[k] = subL[i];
            i++;
            k++;
        }
  
        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr[k] = R[j];
            subArr[k] = subR[j];
            j++;
            k++;
        }
    }
    
    // Main function that sorts arr[l..r] using
    // merge()
    public void sort(int arr[], int[] subArr, int l, int r)
    {
        if (l < r) {
            // Find the middle point
            int m =l+ (r-l)/2;
  
            // Sort first and second halves
            sort(arr, subArr, l, m);
            sort(arr, subArr, m + 1, r);
  
            // Merge the sorted halves
            merge(arr, subArr, l, m, r);
        }
    }
    
}

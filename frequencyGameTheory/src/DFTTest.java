public class DFTTest {
  public static void main(String[] args) {
    int[] array1 = {1, 0, 1, 0, 1, 0 , 1, 0 , 1, 0, 1, 0};

    print_stuff(array1);

    int[] array2 = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

    print_stuff(array2);

    int[] array3 = {1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0};
    print_stuff(array3);

    int[] array4 = {1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0};
    print_stuff(array4);

    int[] array5 = {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0};
    print_stuff(array5);

    int[] array6 = {1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0};
    print_stuff(array6);
    }

    public static void print_stuff(int[] array) {
    DFT.Complex[] fourier_data = DFT.fourier(array);
    double[] magnitudes = DFT.magnitudes(fourier_data);
    // Print this data out
    // for (int i = 0; i < fourier_data.length; i++) {
    //   // System.out.println("Coefficient " + i + ": " + fourier_data[i]);
    //   System.out.println("Coeefficient " + i + ": " + magnitudes[i]);
    // }

    // Visualize the magnitudes as a simple plot
    for (int i = 0; i < magnitudes.length; i++) {
      System.out.print(i + ": ");
      for (int j = 0; j < (int) magnitudes[i]; j++) {
      System.out.print("*");
      }
      System.out.println();
    }
  }
}

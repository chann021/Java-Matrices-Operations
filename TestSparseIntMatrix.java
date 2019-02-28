/* Author - Sam Channon, chann021@umn.edu
   Date Last Editted - 11/8/17
   This is a TestSparseIntMatrix class to create SparseIntMatrix objects and run
   the different tests to draw goldy and some U of Ms*/

public class TestSparseIntMatrix{

  public static void main(String[] args){

    //Instantiates a SparseIntMatrix object that takes data from the text file matrix1_data.
    SparseIntMatrix goldy = new SparseIntMatrix(1000, 1000, "matrix1_data.txt");
    MatrixViewer.show(goldy);

    //Instantiates two SpareIntMatrix objects that takes data from the text files matrix2_data and
    //matrix2_noise and then subtracts the noise data out of the data and results in 3 Ms.
    SparseIntMatrix matrix2data = new SparseIntMatrix(1000,1000,"matrix2_data.txt");
    SparseIntMatrix matrix2noise = new SparseIntMatrix(1000,1000,"matrix2_noise.txt");
    matrix2data.minus(matrix2noise);
    MatrixViewer.show(matrix2data);
  }

}

/* Author - Sam Channon, chann021@umn.edu
   Date Last Editted - 11/7/17
   This is a MatrixEntry class to create MatrixEntry objects to keep track of the
   location, data, and the next MatrixEntry object in their column and their row
   for the SparseIntMatrix class.*/

public class MatrixEntry{

  private int row;
  private int column;
  private int data;
  private MatrixEntry nextMatrixColumn;
  private MatrixEntry nextMatrixRow;

  public MatrixEntry(int r, int c, int d){
    row = r;
    column = c;
    data = d;
  }//Constuctor to create MatrixEntry Object with row value r, column value c, and data value d.

  public int getColumn(){
    return column;
  }//Getter method to return the value of column for a certain MatrixEntry Object.

  public void setColumn(int c){
    column = c;
  }//Setter method to set or change the value of column for a certain MatrixEntry Object.

  public int getRow(){
    return row;
  }//Getter method to return the value of row for a certain MatrixEntry Object.

  public void setRow(int r){
    row = r;
  }//Setter method to set or change the value of column for a certain MatrixEntry Object.

  public int getData(){
    return data;
  }//Getter method to return the value of data for a certain MatrixEntry Object.

  public void setData(int d){
    data = d;
  }//Setter method to set or change the value of data for a certain MatrixEntry Object.

  public MatrixEntry getNextColumn(){
    return nextMatrixColumn;
  }//Getter method to return the next MatrixEntry Object in the same column.

  public void setNextColumn(MatrixEntry me){
    nextMatrixColumn = me;
  }//Setter method to set or change the next MatrixEntry Object in the same column.

  public MatrixEntry getNextRow(){
    return nextMatrixRow;
  }//Getter method to return the next MatrixEntry Object in the same row.

  public void setNextRow(MatrixEntry me){
    nextMatrixRow = me;
  }//Setter method to set or change the next MatrixEntry Object in the same row.
}

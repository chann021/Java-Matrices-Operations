/* Author - Sam Channon, chann021@umn.edu
   Date Last Editted - 11/8/17
   This is a SparseIntMatrix class that takes in a file full of data for MatrixEntry
   objects(row,column,data) and places them into a linked list, creating a sparse
   matrix. This class consists of methods to get elements from the matrix(getElement),
   change or set elements in the matrix(setElement), remove an element(removeElement),
   get the number of columns(getNumCols), get the number of rows(getNumRows), get the
   array of row headers(getRowHeaders), get the array of column headers(getColumnHeaders),
   add two matrices together(plus), subtract two matrices(minus), remove an element in
   rowHeaders(removeRowHeader), remove an element in columnHeaders(removeColHeader), add
   an element to rowHeaders(addRowHeader), and to add an element to columnHeaders(addColumnHeader)*/


import java.io.File;
import java.util.Scanner;


public class SparseIntMatrix{

  private int numRows;
  private int numColumns;
  private MatrixEntry[] rowHeaders = new MatrixEntry[0];
  private MatrixEntry[] columnHeaders = new MatrixEntry[0];

//Constructor that sets the number of rows(numRows) and number of columns(numColumns) for the SparseIntMatrix object.
  public SparseIntMatrix(int rows, int cols){
    numRows = rows;
    numColumns = cols;
  }

/*Constructor that sets the number of rows(numRows), the number of columns(numColumns), and creates scanner objects
to read through the inputFile and uses the setElement method to place every line in the text file into the linked
list implementation of a sparse matrix.*/
  public SparseIntMatrix(int rows, int cols, String inputFile){

    numRows = rows;
    numColumns = cols;
    File input = new File(inputFile);
    Scanner counter;
    Scanner fileInput;

    //Makes sure no exceptions are found when trying to read the file.
    try {
      counter = new Scanner(input);
      fileInput = new Scanner(input);
    } catch(Exception e) {
      System.out.println("There was an error opening the file");
      return;
    }

    //Runs through the file to count the number of lines and stores this number in count.
    int count = 0;
    while(counter.hasNext()){
      counter.nextLine();
      count++;
    }

    counter.close();

    //Runs through file again and takes out every line, then splits each line into [row,column,data], and places
    //each line of data into the linked list by calling setElement.
    for(int i = 0; i < count; i++){
      String nextData = fileInput.next();
      String[] dataArray = nextData.split(",");
      this.setElement(Integer.parseInt(dataArray[0]), Integer.parseInt(dataArray[1]), Integer.parseInt(dataArray[2]));
    }
  }

  //Getter method to find the element at location r(row) and c(column) and return the data at that point or if it
  //doesnt exist returns 0.
  public int getElement(int r, int c){

    MatrixEntry findRow;
    Boolean foundCol = false;

    for(int i = 0; i < rowHeaders.length; i++){
      if(rowHeaders[i].getRow() == r){
        findRow = rowHeaders[i];
        while(foundCol == false){
          if(findRow.getColumn() == c){
            return findRow.getData();
          }else if(findRow.getNextRow() == null){
            return 0;
          }else{
            findRow = findRow.getNextRow();
          }
        }
      }
    }
    return 0;
  }

  //Setter method to add the element with row value r, column value c and data value d into the SparseIntMatrix
  //object.
  public Boolean setElement(int r, int c, int d){

    //Uses the parameters to create a MatrixEntry object.
    MatrixEntry newEntry = new MatrixEntry(r, c, d);

    Boolean addedRow = false;
    Boolean addedCol = false;

    //Makes sure the entry is within matrix boundaries.
    if(r <= 0 || r > getNumRows() || c <= 0 || c > getNumCols()){
      return false;
    }

    //Checks to see if the entered element is the first addition to the matrix and if so adds it in.
    if(rowHeaders.length == 0 && columnHeaders.length == 0){
      addRowHeader(newEntry);
      addColumnHeader(newEntry);
      newEntry.setNextRow(null);
      newEntry.setNextColumn(null);
      return true;

    }else{

      //Finds where the MatrixEntry object should be located in the rowHeaders linked list and edits the nextRow links
      //for the objects affected by the addition.
      for(int i = 0; i < rowHeaders.length; i++){

        //Begins by checking if the new element belongs in the rowHeaders array and if so edits the links.
        if(newEntry.getRow() == rowHeaders[i].getRow()){
          if(newEntry.getColumn() == rowHeaders[i].getColumn()){
            rowHeaders[i].setData(newEntry.getData());
            addedRow = true;
            break;
          }else if(newEntry.getColumn() < rowHeaders[i].getColumn()){
            newEntry.setNextRow(rowHeaders[i]);
            rowHeaders[i] = newEntry;
            addedRow = true;
            break;

          //After finding the correct row, this method then searches through the links to get the correct location based
          //on the objects column and updates the row links.
          }else{
            MatrixEntry nextMatrix = rowHeaders[i];
            MatrixEntry lastMatrix = rowHeaders[i];

            while(newEntry.getColumn() > nextMatrix.getColumn()){
              if(nextMatrix.getNextRow() == null){
                nextMatrix.setNextRow(newEntry);
                newEntry.setNextRow(null);
                addedRow = true;
                break;
              }else{
                lastMatrix = nextMatrix;
                nextMatrix = nextMatrix.getNextRow();
              }
            }
            if(addedRow == false){
              lastMatrix.setNextRow(newEntry);
              newEntry.setNextRow(nextMatrix);
              addedRow = true;
            }
            break;
          }
        }
      }
      if(addedRow == false){
        addRowHeader(newEntry);
        newEntry.setNextRow(null);
      }

      //Finds where the MatrixEntry object should be located in the columnHeaders linked list and edits the nextColumn links
      //for the objects affected by the addition.
      for(int i = 0; i < columnHeaders.length; i++){

        //Begins by checking if the new element belongs in the columnHeaders array and if so edits the links.
        if(newEntry.getColumn() == columnHeaders[i].getColumn()){
          if(newEntry.getRow() == columnHeaders[i].getRow()){
            columnHeaders[i].setData(newEntry.getData());
            addedCol = true;
            break;
          }else if(newEntry.getRow() < columnHeaders[i].getRow()){
            newEntry.setNextColumn(columnHeaders[i]);
            columnHeaders[i] = newEntry;
            addedCol = true;
            break;

          //After finding the correct column, this method then searches through the links to get the correct location based
          //on the objects row and updates the column links.
          }else{
            MatrixEntry nextMatrix = columnHeaders[i];
            MatrixEntry lastMatrix = columnHeaders[i];

            while(newEntry.getRow() > nextMatrix.getRow()){
              if(nextMatrix.getNextColumn() == null){
                nextMatrix.setNextColumn(newEntry);
                newEntry.setNextColumn(null);
                addedCol = true;
                break;
              }else{
                lastMatrix = nextMatrix;
                nextMatrix = nextMatrix.getNextColumn();
              }
            }
            if(addedCol == false){
              lastMatrix.setNextColumn(newEntry);
              newEntry.setNextColumn(nextMatrix);
              addedCol = true;
            }
            break;
          }
        }
      }
      if(addedCol == false){
        addColumnHeader(newEntry);
        newEntry.setNextColumn(null);
      }
    }
    return true;
  }

  //Method to remove an element in the linked list at location r(row) and c(column) and update the links.
  public Boolean removeElement(int r, int c){

    //Makes sure the entry is within matrix boundaries.
    if(r <= 0 || r > getNumRows() || c <= 0 || c > getNumCols()){
      return false;
    }

    for(int i = 0; i < rowHeaders.length; i++){
      //Checks to see if the element the user wants remove is located in the rowHeaders array and if so
      //removing it and updating the links.
      if(r == rowHeaders[i].getRow()){
        if(c == rowHeaders[i].getColumn()){
          if(rowHeaders[i].getNextRow() == null){
            removeRowHeader(rowHeaders[i]);
          }else{
            rowHeaders[i] = rowHeaders[i].getNextRow();
          }
          break;
        //After finding the correct row, this method then searches through the links to get the correct location based
        //on the objects column and removes the object while updating the row links.
        }else{
          MatrixEntry nextMatrix = rowHeaders[i];
          MatrixEntry lastMatrix = rowHeaders[i];
          while(c != nextMatrix.getColumn()){
            lastMatrix = nextMatrix;
            nextMatrix = nextMatrix.getNextRow();
          }
          lastMatrix.setNextRow(nextMatrix.getNextRow());
          break;
        }
      }
    }


    for(int i = 0; i < columnHeaders.length; i++){
      //Checks to see if the element the user wants remove is located in the columnHeaders array and if so
      //removing it and updating the links.
      if(c == columnHeaders[i].getColumn()){
        if(r == columnHeaders[i].getRow()){
          if(columnHeaders[i].getNextColumn() == null){
            removeColHeader(columnHeaders[i]);
          }else{
            columnHeaders[i] = columnHeaders[i].getNextColumn();
          }
          break;
        //After finding the correct column, this method then searches through the links to get the correct location based
        //on the objects row and removes the object while updating the column links.
        }else{
          MatrixEntry nextMatrix = columnHeaders[i];
          MatrixEntry lastMatrix = columnHeaders[i];
          while(r != nextMatrix.getRow()){
            lastMatrix = nextMatrix;
            nextMatrix = nextMatrix.getNextColumn();
          }
          lastMatrix.setNextColumn(nextMatrix.getNextColumn());
          break;
        }
      }
    }
    return true;
  }

  //Getter method to return the number of columns in the matrix.
  public int getNumCols(){
    return numColumns;
  }

  //Getter method to return the number of rows in the matrix.
  public int getNumRows(){
    return numRows;
  }

  //Getter method to return the array of row headers.
  public MatrixEntry[] getRowHeaders(){
    return rowHeaders;
  }

  //Getter method to return the array of column headers.
  public MatrixEntry[] getColumnHeaders(){
    return columnHeaders;
  }

  //Method to add two matrices together.
  public Boolean plus(SparseIntMatrix otherMat){

    //Checks to make sure both matrices are of the same size.
    if(otherMat.getNumCols() == getNumCols() && otherMat.getNumRows() == getNumRows()){
      MatrixEntry[] otherRH = otherMat.getRowHeaders();

      for(int i = 0; i < otherRH.length; i++){
        //Uses the method setElement to add each otherMat row head to the current matrix.
        int newData = (getElement(otherRH[i].getRow(), otherRH[i].getColumn()) + otherRH[i].getData());
        setElement(otherRH[i].getRow(), otherRH[i].getColumn(), newData);

        MatrixEntry matrix = otherRH[i];
        MatrixEntry nextMatrix;

        //Uses the links to get each MatrixEntry under each row header of otherMat and then add them to the current matrix with setElement.
        while(matrix.getNextRow() != null){
          nextMatrix = matrix.getNextRow();
          newData = (getElement(nextMatrix.getRow(), nextMatrix.getColumn()) + nextMatrix.getData());
          setElement(nextMatrix.getRow(), nextMatrix.getColumn(), newData);
          matrix = matrix.getNextRow();
        }
      }
      return true;
    }
    return false;
  }



  //Method to subtract one matrix from another.
  public Boolean minus(SparseIntMatrix otherMat){
    //Checks to make sure both matrices are of the same size.
    if(otherMat.getNumCols() == getNumCols() && otherMat.getNumRows() == getNumRows()){

      MatrixEntry[] otherRH = otherMat.getRowHeaders();
      for(int i = 0; i < otherRH.length; i++){
        int newData = (getElement(otherRH[i].getRow(), otherRH[i].getColumn()) - otherRH[i].getData());
        //Uses the removeElement and setElement methods to update the linked list.
        if(newData == 0){
          removeElement(otherRH[i].getRow(), otherRH[i].getColumn());
        }else{
          setElement(otherRH[i].getRow(), otherRH[i].getColumn(), newData);
        }

        MatrixEntry matrix = otherRH[i];
        MatrixEntry nextMatrix;

        //Uses the links to get each MatrixEntry under each row header of otherMat and then subtract their data from the data in the current matrix.
        while(matrix.getNextRow() != null){
          nextMatrix = matrix.getNextRow();
          newData = (getElement(nextMatrix.getRow(), nextMatrix.getColumn()) - nextMatrix.getData());
          if(newData == 0){
            removeElement(nextMatrix.getRow(), nextMatrix.getColumn());
          }else{
            setElement(nextMatrix.getRow(), nextMatrix.getColumn(), newData);
          }
          matrix = matrix.getNextRow();
        }
      }
      return true;
    }
    return false;
  }

  //Method to remove a row header(me) from the array of row headers.
  public void removeRowHeader(MatrixEntry me){
    MatrixEntry[] newRowHeaders = new MatrixEntry[rowHeaders.length - 1];
    int z = 0;
    for(int i = 0; i < rowHeaders.length; i++){
      //if statement to skip over the element that is to be removed.
      if(rowHeaders[i] == me){
        continue;
      //else statement to populate the new row headers array.
      }else{
        newRowHeaders[z] = rowHeaders[i];
        z++;
      }
    }
    //Sets the old row headers array to the updated one.
    rowHeaders = newRowHeaders;
  }

  //Method to remove a column header(me) from the array of column headers.
  public void removeColHeader(MatrixEntry me){
    MatrixEntry[] newColHeaders = new MatrixEntry[columnHeaders.length - 1];
    int z = 0;
    for(int i = 0; i < columnHeaders.length; i++){
      //if statement to skip over the element that is to be removed.
      if(columnHeaders[i] == me){
        continue;
      //else statement to populate the new column headers array.
      }else{
        newColHeaders[z] = columnHeaders[i];
        z++;
      }
    }
    //Sets the old column headers array to the updated one.
    columnHeaders = newColHeaders;
  }

  //Method to add a new row header to the array rowHeaders and uses a selection sort to sort them by row and column.
  public void addRowHeader(MatrixEntry me){
    //Adds the MatrixEntry to rowHeaders if theres nothing in it.
    if(rowHeaders.length == 0){
      rowHeaders = new MatrixEntry[1];
      rowHeaders[0] = me;
    }else{
      MatrixEntry[] newRowHeaders = new MatrixEntry[rowHeaders.length + 1];
      //Populates the newRowHeaders array with all MatrixEntrys from the origional array and adds the next MatrixEntry.
      for(int i = 0; i < rowHeaders.length; i++){
        newRowHeaders[i] = rowHeaders[i];
      }
      newRowHeaders[rowHeaders.length] = me;
      //Runs the newRowHeaders through a selection sort to order them based on row and column.
      for(int i = 0; i < newRowHeaders.length; i++){
        int index = i;
        for(int j = i + 1; j < newRowHeaders.length; j++){
          if(newRowHeaders[j].getRow() < newRowHeaders[index].getRow()){
            index = j;
          }
        }
        MatrixEntry smallRow = newRowHeaders[index];
        newRowHeaders[index] = newRowHeaders[i];
        newRowHeaders[i] = smallRow;
      }
      //Sets the old row headers array to the updated one.
      rowHeaders = newRowHeaders;
    }
  }

  //Method to add a new column header to the array columnHeaders and uses a selection sort to sort them by column and row.
  public void addColumnHeader(MatrixEntry me){
    //Adds the MatrixEntry to columnHeaders if theres nothing in it.
    if(columnHeaders.length == 0){
      columnHeaders = new MatrixEntry[1];
      columnHeaders[0] = me;
    }else{
      //Populates the newColHeaders array with all MatrixEntrys from the origional array and adds the next MatrixEntry.
      MatrixEntry[] newColHeaders = new MatrixEntry[columnHeaders.length + 1];
      for(int i = 0; i < columnHeaders.length; i++){
        newColHeaders[i] = columnHeaders[i];
      }
      newColHeaders[columnHeaders.length] = me;
      //Runs the newColHeaders through a selection sort to order them based on column and row.
      for(int i = 0; i < newColHeaders.length; i++){
        int index = i;
        for(int j = i + 1; j < newColHeaders.length; j++){
          if(newColHeaders[j].getColumn() < newColHeaders[index].getColumn()){
            index = j;
          }
        }
        MatrixEntry smallCol = newColHeaders[index];
        newColHeaders[index] = newColHeaders[i];
        newColHeaders[i] = smallCol;
      }
      //Sets the old column headers array to the updated one.
      columnHeaders = newColHeaders;
    }
  }
}

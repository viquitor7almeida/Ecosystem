package models;

public class Cells {
    public enum CellType{
     ALIVE_CELL(1), 
     DEAD_CELL(2), 
     PREDATOR_CELL(3), 
     WARRIOR_CELL(4), 
     ANGEL_CEll(5);

     private final int cellNumber;

     CellType(int cellNumber) {
        this.cellNumber = cellNumber;
     }
     
     public int getcellNumber(){
        return cellNumber;
        }
    }
}
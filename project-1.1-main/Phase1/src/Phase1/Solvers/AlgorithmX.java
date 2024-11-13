package Phase1.src.Phase1.Solvers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Phase1.src.Phase1.DataConstructor.PentominoDatabase;

public class AlgorithmX extends ISolver {
    ArrayList<int[]> solution = new ArrayList<>();
  
    public AlgorithmX(int width,int height,char[] input){
        super(width, height, input);
       
    }

    int[][] toField(int[] row){
        int index = 0;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                field[j][i] = row[index];
            }
        }
        return field;
    }
    @Override
    public boolean search(String solverName) {
       
        
        int[] results = new int[WIDTH*HEIGHT];
        Arrays.fill(results, -1);
        
        
        return false;
    }

   
    // private int[][] addBoards(int[] board1,int[][] board2){
        
    //     for (int i = 0; i < board1.length; i++) {
    //         for (int j = 0; j < board1[0].length; j++) {
    //             if(board1[i][j]!=-1 && board2[i][j]==-1 ){
    //                 board2[i][j] = board1[i][j];
    //             }
    //         }
    //     }
    //     return board2;
    // }
    protected boolean isCombinable(int[][] board1,int[][] board2){
        
        for (int i = 0; i < board1.length; i++) {
            for (int j = 0; j < board1[0].length; j++) {
                if(board1[i][j]!=-1 && board2[i][j]!=-1 ){
                    return false;
                }
            }
        }
        return true;
    }

  
    boolean algorithmX(int[][] matrix, int[] results,int col) {
        
        if (col >= results.length) {
            return true; //if the result board is full then you find a solution
        }
       
        for (int i = 0; i < matrix.length; i++) {
            if(matrix[i][col] !=-1){
                if(canAdd(matrix[i], results)){
                    int[] newResult = addRows(matrix[i],results);
                    int id = matrix[i][col];
                    int[][] removedDublicates = coverColumn(matrix,col);
                    int[][] removedPermutaion = removePermutaion(id,removedDublicates);
                    for (int j = i; j < removedPermutaion[0].length; j++) {
                        if(removedPermutaion[i][j] != -1){
                            for (int row = 0; row < removedPermutaion[0].length; row++) {
                                removedPermutaion[row][j] = -1;
                            }
                        }
                    }
                    
                    if(algorithmX(removedPermutaion,newResult, col+1)){
                        return true;
                    }
                    
                }

            }
               
        }
    
        return false;
    }
    
    private int[][] coverColumn(int[][] matrix, int colIndex) {
        int[][] copyBoard = new int[matrix.length][matrix[0].length];
        copyBoard= copyBoard();
        for (int i = 0; i < copyBoard.length; i++) {
            if (copyBoard[i][colIndex] != -1) {
                for (int j = 0; j < copyBoard[i].length; j++) {
                    if (copyBoard[i][j] != -1) {
                        copyBoard[i][j] = -1;
                    }
                }
            }
        }
        return copyBoard;
    }
    
    protected int[][] uncoverColumn(int[][] matrix, int colIndex,int id) {
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][colIndex] != -1) {
                for (int j = 0; j < matrix[i].length; j++) {
                    if (matrix[i][j] != -1) {
                        matrix[i][j] = id;
                    }
                }
            }
        }
        return matrix;
    }   
    
    private int[][] removePermutaion(int id, int[][] removedDublicates) {

        int[][] newBoard = copyBoard();
        for (int i = 0; i < newBoard.length; i++) {
            for (int j = 0; j < newBoard[0].length; j++) {
                if(newBoard[i][j]==id){
                    newBoard[i][j]= -1;
                }
            }
        }
        return newBoard;
    }


   
    protected int[][] removeDuplicate(int j, int[][] matrix){
        
        int up=j,down=j;
        int[][] newMat = copyBoard();
        while(true){

            if(!(up<0)){
                
                if(newMat[up][j] != -1){
                    for(int col = 0;col<newMat[0].length;col++){
                        newMat[up][col] = -1;
                    }

                    
                }
                up--;
            }
            if(!(down>newMat.length-1)){

                  if(newMat[down][j] != 1){
                    
                    for(int col = 0;col<newMat[0].length;col++)
                        newMat[down][col] = -1;
                }
                down++;
            }

            if(up<0 && down== newMat.length) 
                break; 
        }

       
        return newMat;
    }

         
    protected int[][] addDuplicate(int j, int matrix[][]){
        
        
        int id = matrix[1][j];
        
        

        for (int index = 0; index < matrix[0].length; index++) {
            if (matrix[1][index]==id){
                matrix[0][index] = 1;
            }
        }
        return matrix;
    }

         
   
  
    protected int[] boardToSingleRow(int[][] piece, int pentominoID,int x,int y) {
        int[] row  = new int[WIDTH*HEIGHT];
        
        emptyBoard();
        int[][] copyBoard = copyBoard();
        if(piece.length<=WIDTH&&x>=0&&piece[0].length<=HEIGHT&&y>=0&&isPlaceable(piece, pentominoID, x, y))
            copyBoard= addPiece(copyBoard, piece, pentominoID, x, y);
        
            
        int index = 0;
        for (int i = 0; i < copyBoard[0].length; i++) {
            for (int j = 0; j < copyBoard.length; j++) {
                
                if(copyBoard[j][i] == pentominoID)
                    row[index] = 1;
                index++;
            }
        }
        return row;

    }

    protected int findBestCol(int[][] matrix){
        int min=99999; 
        int minIndex=-1;
        for(int j=2;j<matrix[0].length;j++){
            int nr=0;
            for(int i=0;i<matrix.length;i++){
                if(matrix[i][j]==1 && matrix[j][0]!=0){
                    nr++;
                }

            }
        if(nr<min){
            min=nr;
            minIndex=j;
        }

        }
        return minIndex;
    }
   
    protected int[] getDublicate(int[] ids){
        int[] dublicateIndecies = new int[ids.length];
        for (int i = 0; i < ids.length; i++) {
            int temp = ids[i];
            int counter = 0;
            for (int j = 0; j < ids.length; j++) {
                if(ids[j] == temp)
                    counter++;
            }
            dublicateIndecies[i] = counter;
        }
        return dublicateIndecies;
    }

    public int[][] allPentominoMatrix(){
        int[] pentIDs = getPentIDs();
        List<int[]> rows = new ArrayList<>();

        for (int id=0; id<pentIDs.length; id++) {
            
            int mutations =  PentominoDatabase.data[pentIDs[id]].length;
            for (int mutation = 0; mutation < mutations; mutation++) {
                
            
                int[][] piece = PentominoDatabase.data[pentIDs[id]][mutation];
                
                for (int y = 0; y < HEIGHT; y++) {
                    for (int x = 0; x < WIDTH; x++) {
                        emptyBoard();
                        if(isPlaceable(piece, id, x, y)){
                            addPiece(piece, id, x, y);
                            int [] row = new int[(HEIGHT*WIDTH)+pentIDs.length];
                            int index = 0;

                            for (int i = 0; i <pentIDs.length; i++) {

                                if(id == i)
                                    row[i] = 1;
                                else 
                                    row[i] = 0;
                                index++;
                            }

                            for (int i = 0; i < field.length; i++) {
                                for (int j = 0; j < field[0].length; j++) {

                                    if(field[i][j] != -1)
                                        row[index] = 1;
                                    else
                                        row[index] = 0;
                                    index++;
                                }
                            }
                            
                            

                            rows.add(row);
                            
                            // System.out.println(Arrays.toString(row));
                            
                    }   
                }
            }
        }
    }
        int[][] matrix = new int[rows.size()][];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = rows.get(i);
        }
        
       return matrix;
    }
   
        


    

    boolean checkRowEmpty(int[] row){
        for (int j = 2; j < row.length; j++) {
            if(row[j]!=0)
                return false;
        }

        return true;
    }
    
    
    protected boolean checkRowFull(int[] row){
        
        for (int i = 0; i < row.length; i++) {
            if(row[i]==-1)
                return false;
        }
        return true;
    }

    private boolean canAdd(int[] row,int[] result){
       
        for (int i = 0; i < row.length; i++) {

            if((row[i]!=-1 && (result[i] !=-1)))
                return false;
        }
        return true;
    }

    private int[] addRows(int[] row,int[] result){
        int[] addedRows = new int[result.length];
        addedRows = Arrays.copyOf(result, result.length);
        for (int i = 0; i < row.length; i++) {
            if(addedRows[i] == -1)
                addedRows[i]= row[i];
        }
        return addedRows;
    }

    public static void main(String[] args) {
        
    }
}






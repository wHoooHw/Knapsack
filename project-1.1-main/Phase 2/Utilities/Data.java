package Utilities;

public class Data {
    
    public static final int[][][] PENTOMINOS = {
        {
            // pentomino representation X
                {0,1,0},
                {1,1,1},
                {0,1,0}
        },
        {
            // pentomino representation I
                {1},
                {1},
                {1},
                {1},
                {1}
        },
        {
            // pentomino representation Z
                {1,1,0},
                {0,1,0},
                {0,1,1}
        },
        {
            // pentomino representation T
                {1,1,1},
                {0,1,0},
                {0,1,0}
        },
        {
            // pentomino representation U
                {1,1},
                {1,0},
                {1,1}
        },
        {
            // pentomino representation V
                {1,1,1},
                {1,0,0},
                {1,0,0}
        },
        {
            // pentomino representation W
                {0,0,1},
                {0,1,1},
                {1,1,0}
        },
        {
            // pentomino representation Y
                {1,0},
                {1,1},
                {1,0},
                {1,0}
        },
        {
            // pentomino representation L
                {1,0},
                {1,0},
                {1,0},
                {1,1}
        },
        {
            // pentomino representation P
                {1,1},
                {1,1},
                {0,1}
        },
        {
            // Implement pentomino representation N
                {1,0},
                {1,0},
                {1,1},
                {0,1}
        },
        {
            // Implement pentomino representation F
                {0,1,0}, 
                {1,1,1},
                {1,0,0},
                
        }
    }; 

    public static final char[] PENTLETTERS = {'X','I','Z','T','U','V','W','Y','L','P','N','F'};  
  
    public static int[][] getShape(int pentID){
        int[][] shape = PENTOMINOS[pentID];
        return shape;
    }

    public static int[][] getShape(char charactar){
        for (int i = 0; i < PENTLETTERS.length; i++) {
            if(charactar==PENTLETTERS[i])
                return  PENTOMINOS[i];
        }
        return null;
    }

    public static int getID(char charactar){
        for (int i = 0; i < PENTLETTERS.length; i++) {
            if(charactar==PENTLETTERS[i])
                return  i;
        }
        return -1;
    }





}

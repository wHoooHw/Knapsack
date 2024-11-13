package Phase1.src.Phase1.Solvers.DLX;

import Phase1.src.Phase1.GUI.UI;

public class DLXMain {
    
    public static void main(String[] args) {
        char[] input = new char[]{'I','Y','N','F','X','U','V','P','T','W','Z','L'};
        DLXSolver so = new DLXSolver(12, 5, input);
        // System.out.println(Arrays.deepToString(so.matrix));
        // System.out.println(so.matrix.length + " " + so.matrix[0].length);
        so.createDLXStructure();
        so.search(null);//){
        UI ui = new UI(so.getWIDTH(),so.getHEIGHT(),50);
        ui.setState(so.getField());
        // }
    }
}

package Phase1.src.Phase1;


/**
 * The Main class serves as the entry point for the program and provides the main method for executing
 * the puzzle-solving application. It allows users to specify puzzle parameters, including the field's
 * dimensions, solver type, and input symbols.
 *
 * The program performs input validation, constructs the solver based on user input, and then displays
 * the result, including the time taken to solve the puzzle.
 * 
 * @author Group 40 (Abdulla) 
 * @version 1.0
 */
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import Phase1.src.Phase1.GUI.UI;
import Phase1.src.Phase1.Solvers.Backtracking;
import Phase1.src.Phase1.Solvers.ISolver;
import Phase1.src.Phase1.Solvers.DLX.DLXSolver;


public class Main {

    static Set<Character> validInput = new HashSet<>();

    /**
     * Validates whether the provided input string consists of valid symbols.
     *
     * @param inputStr The input string to validate.
     * @return true if the input string is valid; false otherwise.
     */
    public static boolean verifyValidInput(String inputStr) {
        if (inputStr.length() == 0)
            return false;

        for (char character : inputStr.toCharArray()) {
            if (!validInput.contains(character))
                return false;
        }
        return true;
    }

    /**
     * The main entry point of the program.
     *
     * @param args The command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        String v = "TWZLIYXUVPNF";

        for (char c : v.toCharArray()) {
            validInput.add(c);
        }
        String solverName;

        int height;
        int width;
        String inputStr;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please introduce an integer to set the height of the field");
            height = scanner.nextInt();
            System.out.println("Please enter an integer to set the width of the field");
            width = scanner.nextInt();
            System.out.println("Please enter the name of the solver choose among bad, good, and best for backtracking or use DLX for dancing links: ");
            scanner.nextLine();
            solverName = scanner.nextLine().toUpperCase();

            System.out.println("Enter a set of symbols");
            inputStr = scanner.nextLine().toUpperCase();
            if (inputStr.length() * 5 != height * width) {
                System.out.println("Boards Dimensions are not compatible with the pieces");
            } else if (!verifyValidInput(inputStr)) {
                System.out.println("You have an invalid symbol or duplicates. " +
                        "Try using only these letters {'T','W','Z','L','I','Y','X','U','V','P','N','F'}");
            } else{
                break;}
                scanner.close();
        }

        char[] input = inputStr.toCharArray();

        if (solverName.equalsIgnoreCase("DLX")) {
            ISolver solver = new DLXSolver(width, height, input);
            long time = System.currentTimeMillis();
            solver.search(solverName);
            System.out.println(System.currentTimeMillis() - time);
            UI ui = new UI(width, height, 50);
            ui.setState(solver.getField());
        } else {
            ISolver solver = new Backtracking(width, height, input);
            long time = System.currentTimeMillis();

            if (solver.search(solverName)) {
                System.out.println(System.currentTimeMillis() - time);
                UI ui = new UI(width, height, 50);
                ui.setState(solver.getField());
            } else
                System.out.println("No solution");
        }
        
    }
}

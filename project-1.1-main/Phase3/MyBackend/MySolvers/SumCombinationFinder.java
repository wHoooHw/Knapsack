package Phase3.MyBackend.MySolvers;

import java.util.ArrayList;

public class SumCombinationFinder {

    // Array of volumes
    static int[] vol = {16, 24,27};
    // Target sum to achieve
    static int targetSum = 1320;
    // List to store the result combination
    static ArrayList<Integer> resultCombination = new ArrayList<>();

    public static void main(String[] args) {
        // Find and print the combination
        findCombination(0, new ArrayList<>(), 0);

        if (!resultCombination.isEmpty()) {
            System.out.println("Combination: " + resultCombination.size());
        } else {
            System.out.println("No combination found.");
        }
    }

    // Recursive method to find the combination
    private static void findCombination(int index, ArrayList<Integer> currentCombination, int currentSum) {
        if (currentSum == targetSum) {
            // Combination found
            resultCombination = new ArrayList<>(currentCombination);
            return;
        }

        // Iterate through the volumes starting from the given index
        for (int i = index; i < vol.length; i++) {
            if (currentSum + vol[i] <= targetSum) {
                // Add the volume to the current combination
                currentCombination.add(vol[i]);
                // Recursively explore combinations
                findCombination(i, currentCombination, currentSum + vol[i]);
                // Backtrack: remove the last added volume to explore other possibilities
                currentCombination.remove(currentCombination.size() - 1);
            }
        }
    }
}
import java.util.Scanner;
import java.util.Random;

public class MatrixOperations {

    private static final int MAX_WIDTH = 20;
    private static final int MAX_HEIGHT = 20;
    private static final int RANDOM_MIN = -100;
    private static final int RANDOM_MAX = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows (max 20): ");
        int rows = getValidInput(scanner, MAX_HEIGHT);

        System.out.println("Enter the number of columns (max 20): ");
        int cols = getValidInput(scanner, MAX_WIDTH);

        System.out.println("Choose matrix creation method:");
        System.out.println("1. Manual input");
        System.out.println("2. Random generation");
        int choice = getValidInput(scanner, 2);

        int[][] matrix = (choice == 1) ? createMatrixManually(scanner, rows, cols) : createMatrixRandomly(rows, cols);

        printMatrix(matrix);

        int maxElement = findMax(matrix);
        int minElement = findMin(matrix);
        double average = calculateAverage(matrix);

        System.out.println("Maximum element: " + maxElement);
        System.out.println("Minimum element: " + minElement);
        System.out.println("Average value: " + average);

        scanner.close();
    }

    private static int getValidInput(Scanner scanner, int max) {
        int input;
        do {
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter an integer:");
                scanner.next();
            }
            input = scanner.nextInt();
            if (input <= 0 || input > max) {
                System.out.println("Please enter a value between 1 and " + max + ":");
            }
        } while (input <= 0 || input > max);
        return input;
    }

    private static int[][] createMatrixManually(Scanner scanner, int rows, int cols) {
        int[][] matrix = new int[rows][cols];
        System.out.println("Enter elements row by row:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf("Element [%d][%d]: ", i + 1, j + 1);
                matrix[i][j] = scanner.nextInt();
            }
        }
        return matrix;
    }

    private static int[][] createMatrixRandomly(int rows, int cols) {
        Random random = new Random();
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = RANDOM_MIN + random.nextInt(RANDOM_MAX - RANDOM_MIN + 1);
            }
        }
        return matrix;
    }

    private static void printMatrix(int[][] matrix) {
        System.out.println("Matrix:");
        for (int[] row : matrix) {
            for (int element : row) {
                System.out.printf("%5d", element);
            }
            System.out.println();
        }
    }

    private static int findMax(int[][] matrix) {
        int max = Integer.MIN_VALUE;
        for (int[] row : matrix) {
            for (int element : row) {
                if (element > max) {
                    max = element;
                }
            }
        }
        return max;
    }

    private static int findMin(int[][] matrix) {
        int min = Integer.MAX_VALUE;
        for (int[] row : matrix) {
            for (int element : row) {
                if (element < min) {
                    min = element;
                }
            }
        }
        return min;
    }

    private static double calculateAverage(int[][] matrix) {
        int sum = 0;
        int count = 0;
        for (int[] row : matrix) {
            for (int element : row) {
                sum += element;
                count++;
            }
        }
        return (double) sum / count;
    }
}

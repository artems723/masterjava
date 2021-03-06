package ru.javaops.masterjava.matrix;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

/**
 * gkislin
 * 03.07.2016
 */
public class MatrixUtil {

    // TODO implement parallel multiplication matrixA*matrixB
    public static int[][] concurrentMultiply(int[][] matrixA, int[][] matrixB, ExecutorService executor) throws InterruptedException, ExecutionException {
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];

        for (int j = 0; j < matrixSize; j++) {
            int p = j;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    int thatColumn[] = new int[matrixSize];
                    for (int k = 0; k < matrixSize; k++) {
                        thatColumn[k] = matrixB[k][p];
                    }
                    for (int i = 0; i < matrixSize; i++) {
                        int thisRow[] = matrixA[i];
                        int sum = 0;
                        for (int k = 0; k < matrixSize; k++) {
                            sum += thisRow[k] * thatColumn[k];
                        }
                        matrixC[i][p] = sum;
                    }
                }
            });
        }
        return matrixC;
    }

    // TODO optimize by https://habrahabr.ru/post/114797/
    public static int[][] singleThreadMultiply(int[][] matrixA, int[][] matrixB) {
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];

//        int matrixBT[][] = create(matrixSize);
//        for (int i =0; i < matrixSize; i++) {
//            for (int j = 0; j < matrixSize; j++) {
//                matrixBT[j][i] = matrixB[i][j];
//            }
//        }
//
//        for (int i = 0; i < matrixSize; i++) {
//            for (int j = 0; j < matrixSize; j++) {
//                int sum = 0;
//                for (int k = 0; k < matrixSize; k++) {
////                    sum += matrixA[i][k] * matrixB[k][j];
//                    sum += matrixA[i][k] * matrixBT[j][k];
//                }
//                matrixC[i][j] = sum;
//            }
//        }

        int thatColumn[] = new int[matrixSize];

        for (int j = 0; j < matrixSize; j++) {
            for (int k = 0; k < matrixSize; k++) {
                thatColumn[k] = matrixB[k][j];
            }

            for (int i = 0; i < matrixSize; i++) {
                int thisRow[] = matrixA[i];
                int sum = 0;
                for (int k = 0; k < matrixSize; k++) {
                    sum += thisRow[k] * thatColumn[k];
                }
                matrixC[i][j] = sum;
            }
        }

        return matrixC;
    }

    public static int[][] create(int size) {
        int[][] matrix = new int[size][size];
        Random rn = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = rn.nextInt(10);
            }
        }
        return matrix;
    }

    public static boolean compare(int[][] matrixA, int[][] matrixB) {
        final int matrixSize = matrixA.length;
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (matrixA[i][j] != matrixB[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}

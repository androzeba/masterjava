package ru.javaops.masterjava.matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * gkislin 03.07.2016
 */
public class MatrixUtil {

  // TODO implement parallel multiplication matrixA*matrixB
  public static int[][] concurrentMultiply(int[][] matrixA, int[][] matrixB,
      ExecutorService executor) throws InterruptedException, ExecutionException {
    final int matrixSize = matrixA.length;
    final int[][] matrixC = new int[matrixSize][matrixSize];

    List<Future<List<int[]>>> futureList = new ArrayList<>(matrixSize);

    for (int i = 0; i < matrixSize / 10; i++) {
      int[] rowA1 = matrixA[i];
      int[] rowA2 = matrixA[i + matrixSize / 10];
      int[] rowA3 = matrixA[i + matrixSize / 10 * 2];
      int[] rowA4 = matrixA[i + matrixSize / 10 * 3];
      int[] rowA5 = matrixA[i + matrixSize / 10 * 4];
      int[] rowA6 = matrixA[i + matrixSize / 10 * 5];
      int[] rowA7 = matrixA[i + matrixSize / 10 * 6];
      int[] rowA8 = matrixA[i + matrixSize / 10 * 7];
      int[] rowA9 = matrixA[i + matrixSize / 10 * 8];
      int[] rowA10 = matrixA[i + matrixSize / 10 * 9];
      Future<List<int[]>> future = executor.submit(() -> {
        int[] rowC1 = new int[matrixSize];
        int[] rowC2 = new int[matrixSize];
        int[] rowC3 = new int[matrixSize];
        int[] rowC4 = new int[matrixSize];
        int[] rowC5 = new int[matrixSize];
        int[] rowC6 = new int[matrixSize];
        int[] rowC7 = new int[matrixSize];
        int[] rowC8 = new int[matrixSize];
        int[] rowC9 = new int[matrixSize];
        int[] rowC10 = new int[matrixSize];
        List<int[]> resList = new ArrayList<>();
        for (int j = 0; j < matrixSize; j++) {
          int sum1 = 0;
          int sum2 = 0;
          int sum3 = 0;
          int sum4 = 0;
          int sum5 = 0;
          int sum6 = 0;
          int sum7 = 0;
          int sum8 = 0;
          int sum9 = 0;
          int sum10 = 0;
          for (int k = 0; k < matrixSize; k++) {
            sum1 += rowA1[k] * matrixB[k][j];
            sum2 += rowA2[k] * matrixB[k][j];
            sum3 += rowA3[k] * matrixB[k][j];
            sum4 += rowA4[k] * matrixB[k][j];
            sum5 += rowA5[k] * matrixB[k][j];
            sum6 += rowA6[k] * matrixB[k][j];
            sum7 += rowA7[k] * matrixB[k][j];
            sum8 += rowA8[k] * matrixB[k][j];
            sum9 += rowA9[k] * matrixB[k][j];
            sum10 += rowA10[k] * matrixB[k][j];
          }
          rowC1[j] = sum1;
          rowC2[j] = sum2;
          rowC3[j] = sum3;
          rowC4[j] = sum4;
          rowC5[j] = sum5;
          rowC6[j] = sum6;
          rowC7[j] = sum7;
          rowC8[j] = sum8;
          rowC9[j] = sum9;
          rowC10[j] = sum10;
        }
        resList.add(rowC1);
        resList.add(rowC2);
        resList.add(rowC3);
        resList.add(rowC4);
        resList.add(rowC5);
        resList.add(rowC6);
        resList.add(rowC7);
        resList.add(rowC8);
        resList.add(rowC9);
        resList.add(rowC10);
        return resList;
      });
      futureList.add(i, future);
    }
    for (int i = 0; i < matrixSize / 10; i++) {
      matrixC[i] = futureList.get(i).get().get(0);
      matrixC[i + matrixSize / 10] = futureList.get(i).get().get(1);
      matrixC[i + matrixSize / 10 * 2] = futureList.get(i).get().get(2);
      matrixC[i + matrixSize / 10 * 3] = futureList.get(i).get().get(3);
      matrixC[i + matrixSize / 10 * 4] = futureList.get(i).get().get(4);
      matrixC[i + matrixSize / 10 * 5] = futureList.get(i).get().get(5);
      matrixC[i + matrixSize / 10 * 6] = futureList.get(i).get().get(6);
      matrixC[i + matrixSize / 10 * 7] = futureList.get(i).get().get(7);
      matrixC[i + matrixSize / 10 * 8] = futureList.get(i).get().get(8);
      matrixC[i + matrixSize / 10 * 9] = futureList.get(i).get().get(9);
    }

    return matrixC;
  }

  // TODO optimize by https://habrahabr.ru/post/114797/
  public static int[][] singleThreadMultiply(int[][] matrixA, int[][] matrixB) {
    final int matrixSize = matrixA.length;
    final int[][] matrixC = new int[matrixSize][matrixSize];

    int[] matrixColumn = new int[matrixSize];
    for (int i = 0; i < matrixSize; i++) {
      for (int j = 0; j < matrixSize; j++) {
        matrixColumn[j] = matrixB[j][i];
      }
      for (int j = 0; j < matrixSize; j++) {
        int[] matrixRow = matrixA[j];
        int sum = 0;
        for (int k = 0; k < matrixSize; k++) {
          sum += matrixRow[k] * matrixColumn[k];
        }
        matrixC[j][i] = sum;
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


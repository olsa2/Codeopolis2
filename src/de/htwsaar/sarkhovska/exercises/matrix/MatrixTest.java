package de.htwsaar.sarkhovska.exercises.matrix;

public class MatrixTest {
    public static void main(String[] args) {
        Matrix<Integer> mi = new Matrix<>(3,3,0);
        for (int i = 0; i < 3; i++) {
            mi.set(i, 0, i+1);
            mi.set(i, 1, i+1);
            mi.set(i, 2, i+1);
        }

        Matrix<Double> md = new Matrix<>(3,3,0.0);
        for (int i = 0; i < 3; i++) {
            md.set(i, 0, 1.0 * (i+1));
            md.set(i, 1, 1.0* (i+1));
            md.set(i, 2, 1.0* (i+1));
        }
        Matrix<Double> addition = mi.add(md);
        System.out.println(addition);
        Matrix<Double> multiply = mi.multiplyByScalar(1.1f);
        System.out.println(multiply);
    }
}

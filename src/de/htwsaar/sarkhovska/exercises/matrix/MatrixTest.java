package de.htwsaar.sarkhovska.exercises.matrix;

public class MatrixTest {
    public static void main(String[] args) {
        Matrix mi = new Matrix(3,3);
        for (int i = 0; i < 3; i++) {
            mi.set(i, 0, i+1);
            mi.set(i, 1, i+1);
            mi.set(i, 2, i+1);
        }
        System.out.println("Integer matrix");
        System.out.println(mi);

        Matrix md = new Matrix(3,3);
        for (int i = 0; i < 3; i++) {
            md.set(i, 0, 1.0 * (i+1));
            md.set(i, 1, 1.0* (i+1));
            md.set(i, 2, 1.0* (i+1));
        }
        System.out.println("Double matrix");
        System.out.println(md);

        Matrix addition = mi.add(md);
        System.out.println("Addition");
        System.out.println(addition);

        Matrix multiply = mi.multiplyByScalar(2f);
        System.out.println("Multiply by scalar " + 2f);
        System.out.println(multiply);
    }
}

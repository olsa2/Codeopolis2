package de.htwsaar.sarkhovska.exercises.matrix;

public class Matrix  {
    private final Number[][] matrix;


    public Matrix(int rows, int cols) {
        matrix = new Number[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = 0; //zero;
            }
        }
    }

    public  int getRowNumber() {
        return matrix.length;
    }

    public  int getColNumber() {
        return matrix[0].length;
    }

    public  void set(int row, int col, Number value) {
        matrix[row][col] = value;
    }

    public  Number get(int row, int col) {
        return matrix[row][col];
    }

    public Matrix add(Matrix other) {
        if (other == null) {
            throw new IllegalArgumentException("Other matrix cannot be null");
        }
        if (other.getRowNumber() != getRowNumber() || other.getColNumber() != getColNumber()) {
            throw new IllegalArgumentException("Other matrix has not the same size");
        }

        Matrix result = new Matrix(getRowNumber(),getColNumber());

        for (int i = 0; i < getRowNumber(); i++) {
            for (int j = 0; j < getColNumber(); j++) {
                Double d = matrix[i][j].doubleValue() + other.matrix[i][j].doubleValue();
                result.set(i,j,d);
            }
        }
        return result;
    }

    public Matrix multiplyByScalar(Number value) {
        Matrix result = new Matrix(getRowNumber(),getColNumber());
        for (int i = 0; i < getRowNumber(); i++) {
            for (int j = 0; j < getColNumber(); j++) {
                result.set(i,j,matrix[i][j].doubleValue() * value.doubleValue());
            }
        }
        return result;
    }

    public String   toString() {
        String result = "";
        for (int i = 0; i < getRowNumber(); i++) {
            for (int j=0; j< getColNumber(); ++j) {
                if (j>0) {
                    result += "\t";
                }
                result = result + matrix[i][j];
            }
            result += "\n";
        }
        return result;
    }

}

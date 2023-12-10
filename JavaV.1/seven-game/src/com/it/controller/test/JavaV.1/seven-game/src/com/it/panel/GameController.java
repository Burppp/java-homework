package com.it.panel;

import com.it.constant.Constant;

import com.it.hander.CirclesHandler;
import com.it.panel.MyPanel;
import com.it.pojo.Info;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.Locale.FilteringMode;

import Jama.Matrix;
/**
 */
public class GameController {
    private JFrame jFrame;
    private MyPanel myPanel;
    public static Matrix crossMatrix;
    public static Matrix targetMatrix;
    public static Matrix CoefficientMatrix;

    void init(GameController gameController) {
        jFrame = new JFrame("");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myPanel = new MyPanel(gameController);
        jFrame.add(myPanel);

        jFrame.setSize(Constant.FRAME_WIDTH,Constant.FRAME_HEIGHT);
        jFrame.setVisible(true);
    }

    public static void main(String[] args) {
        GameController gameController = new GameController();

        GameController.crossMatrix = new Matrix(6, 6);
        GameController.targetMatrix = new Matrix(6 * 6, 1);
        GameController.CoefficientMatrix = new Matrix(6 * 6,6 * 6);

        gameController.init(gameController);

        gameController.creatRandomMatrix();

        //gameController.computerOperate();

        gameController.MatrixOptiomalSolution();
    }

    public void MatrixOptiomalSolution()
    {
        Matrix OptionmalSolutionMatrix = new Matrix(6 * 6, 1);
        OptionmalSolutionMatrix = gaussianElimination(CoefficientMatrix, targetMatrix);
        for(int i = 0;i < 6 * 6;i++)
        {
            System.out.printf("%.0f ", OptionmalSolutionMatrix.get(i, 0));
            if(i % 6 == 5)
                System.out.print("\n");
        }
    }

    public static Matrix gaussianElimination(Matrix A, Matrix b)
    {
        Matrix augmenMatrix = concatMatrix(A, b);

        int cols, rows;
        cols = augmenMatrix.getColumnDimension();
        rows = augmenMatrix.getRowDimension();

        for(int i = 0;i < augmenMatrix.getRowDimension();i++)
        {
            int pivotRow = i;
            for(int j = 0;j < augmenMatrix.getRowDimension();j++)
                if(Math.abs(augmenMatrix.get(j, i)) > Math.abs(augmenMatrix.get(pivotRow, i)))
                    pivotRow = j;

            augmenMatrix = swapMatrixRows(augmenMatrix, i, pivotRow);

            double even = 2;
            if(StrictMath.IEEEremainder(augmenMatrix.get(i, i), even) != 0)
                for(int k = 0;k < cols;k++)
                    augmenMatrix.set(i, k, augmenMatrix.get(i, k) / StrictMath.IEEEremainder(augmenMatrix.get(i, i), even));

            for(int j = 0;j < rows;j++)
                if(j != i)
                    for(int k = 0;k < cols;k++)
                        augmenMatrix.set(j, k, augmenMatrix.get(j, k) - augmenMatrix.get(j, i) * augmenMatrix.get(i, k));

            for(int j = 0;j < rows;j++)
                for(int k = 0;k < cols;k++)
                    augmenMatrix.set(j, k, StrictMath.IEEEremainder(augmenMatrix.get(j, k), even));

        }

        Matrix solutionMatrix = new Matrix(rows, 1);
        solutionMatrix.setMatrix(0, rows - 1, 0, 0, augmenMatrix.getMatrix(0, rows - 1, cols - 1, cols - 1));

        return solutionMatrix;
    }

    public static Matrix swapMatrixRows(Matrix matrix, int row1, int row2)
    {
        int cols = matrix.getColumnDimension();

        for(int i = 0;i < cols;i++)
        {
            double temp = matrix.get(row1, i);
            matrix.set(row1, i, matrix.get(row2, i));
            matrix.set(row2, i, temp);
        }

        return matrix;
    }

    public static Matrix concatMatrix(Matrix A, Matrix b)
    {
        if(A.getRowDimension() != A.getColumnDimension() || A.getRowDimension() != b.getRowDimension())
        {
            System.err.println("error:Matrices A and b do not match");
            return null;
        }

        int A_cols, rows, b_cols;
        A_cols = A.getRowDimension();
        rows = A.getRowDimension();
        b_cols = b.getColumnDimension();

        Matrix augmentedMatrix = new Matrix(rows, A_cols + b_cols);

        augmentedMatrix.setMatrix(0, rows - 1, 0, A_cols - 1, A);
        augmentedMatrix.setMatrix(0, rows - 1, A_cols, A_cols + b_cols - 1, b);

        return augmentedMatrix;
    }

    public void creatRandomMatrix() {
        int[] randPoint = {-1, -1, -1, -1, -1, -1};
        Info[] init = new Info[6];
        for(int i = 0;i < 6;i++)
        {
            init[i] = new Info(-1, -1);
            randPoint[i] = ((int)(Math.random() * 100)) % 36;
            int x = CirclesHandler.circles[randPoint[i]].getX();
            int y = CirclesHandler.circles[randPoint[i]].getY();
            init[i].setX(x);
            init[i].setY(y);

            CirclesHandler.handInfo(init[i]);

            crossMatrix.set(randPoint[i] / 6, randPoint[i] % 6, 1);
            targetMatrix.set(randPoint[i], 0, 1);
        }

        myPanel.rePrintCircle();

        int[][] templateMatrix =
                {
                        {1,1,1,1,1,1,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0},
                        {1,1,1,1,1,1,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0},
                        {1,1,1,1,1,1,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0},
                        {1,1,1,1,1,1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0},
                        {1,1,1,1,1,1,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0},
                        {1,1,1,1,1,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1},
                        {1,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0},
                        {0,1,0,0,0,0,1,1,1,1,1,1,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0},
                        {0,0,1,0,0,0,1,1,1,1,1,1,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0},
                        {0,0,0,1,0,0,1,1,1,1,1,1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0},
                        {0,0,0,0,1,0,1,1,1,1,1,1,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0},
                        {0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1},
                        {1,0,0,0,0,0,1,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0},
                        {0,1,0,0,0,0,0,1,0,0,0,0,1,1,1,1,1,1,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0},
                        {0,0,1,0,0,0,0,0,1,0,0,0,1,1,1,1,1,1,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0},
                        {0,0,0,1,0,0,0,0,0,1,0,0,1,1,1,1,1,1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0},
                        {0,0,0,0,1,0,0,0,0,0,1,0,1,1,1,1,1,1,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0},
                        {0,0,0,0,0,1,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1},
                        {1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,1,0,0,0,0,0},
                        {0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,1,1,1,1,1,1,0,1,0,0,0,0,0,1,0,0,0,0},
                        {0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,1,1,1,1,1,0,0,1,0,0,0,0,0,1,0,0,0},
                        {0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,1,1,1,1,1,1,0,0,0,1,0,0,0,0,0,1,0,0},
                        {0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,1,1,1,1,1,1,0,0,0,0,1,0,0,0,0,0,1,0},
                        {0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,1,0,0,0,0,0,1},
                        {1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0},
                        {0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,1,1,1,1,1,1,0,1,0,0,0,0},
                        {0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,1,1,1,1,1,0,0,1,0,0,0},
                        {0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,1,1,1,1,1,1,0,0,0,1,0,0},
                        {0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,1,1,1,1,1,1,0,0,0,0,1,0},
                        {0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,1},
                        {1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,1,1,1,1,1},
                        {0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,1,1,1,1,1,1},
                        {0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,1,1,1,1,1},
                        {0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,1,1,1,1,1,1},
                        {0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,1,1,1,1,1,1},
                        {0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,1,1,1,1,1,1}
                };

        for(int i = 0;i < CoefficientMatrix.getRowDimension();i++)
            for(int j = 0;j < CoefficientMatrix.getColumnDimension();j++)
                CoefficientMatrix.set(i, j, templateMatrix[i][j]);
    }
}
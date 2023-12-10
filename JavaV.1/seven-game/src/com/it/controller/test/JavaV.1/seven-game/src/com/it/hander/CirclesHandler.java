package com.it.hander;

import com.it.constant.Constant;
import com.it.pojo.Circle;
import com.it.pojo.Info;

import java.awt.*;

//用于初始化圆及处理相应变化变化的类
public class CirclesHandler {
    public static Circle[] circles;

    static {
        circles = new Circle[36];
        int i = 0;
        for (Circle circle : circles) {
            circle = new Circle();
            circles[i] = circle;
            circle.setX((i % 6) * Constant.CIRCLE_SIZE + (i % 6) * Constant.CIRCLE_INTERVAL);
            circle.setY(i / 6 * Constant.CIRCLE_SIZE + i / 6 * Constant.CIRCLE_INTERVAL);
            circle.setColor(Color.white);
            circle.setUpdate(false);
            circle.setAlpha(255);
            i++;
        }
    }
    //用于处理鼠标点击造成圆状态改变的事件
    public static void handInfo(Info info) {
        int x = info.getX();
        int y = info.getY();
        int ind = -1;

        for (int i = 0; i < 36; i++) {
            if (x >= circles[i].getX() && x <= circles[i].getX() + Constant.CIRCLE_SIZE
                    && y >= circles[i].getY() && y <= circles[i].getY() + Constant.CIRCLE_SIZE) {
                ind = i;
                break;
            }
        }

        if (ind == -1) return;

        //鼠标点击检测合法，变换所在行列
        int row = ind / 6;
        int col = ind % 6;
        for (int i = 0; i < 36; i++) {
            if (i / 6 == row || i % 6 == col) {
                if (circles[i].getColor() == Color.white)
                    circles[i].setColor(Color.green);
                else
                    circles[i].setColor(Color.white);

                circles[i].setUpdate(true);
            }
        }
    }

    public static void shutUpdate() {
        for (int i = 0; i < 36; i++) {
            if (circles[i].isUpdate())
                circles[i].setAlpha(0);

            circles[i].setUpdate(false);
        }
    }

    public static boolean ok() {
        boolean ok = true;
        for (int i = 0; i < 36; i++) {
            Circle circle = circles[i];

            int alpha = circle.getAlpha();

            if (alpha != 255) {
                if (alpha + 10 <= 255)
                    circle.setAlpha(alpha + 10);
                else
                    circle.setAlpha(255);
                ok = false;
            }
        }

        return ok;
    }
}

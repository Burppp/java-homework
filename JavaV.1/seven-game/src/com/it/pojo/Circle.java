package com.it.pojo;


import java.awt.*;

public class Circle {
    public static final int r=200;
    private int x;
    private int y;
    private Color color;
    //透明度
    private int alpha;
    //是否刚刚更新过
    private boolean update;

    public Circle() {
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public int getX() {
        return x;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

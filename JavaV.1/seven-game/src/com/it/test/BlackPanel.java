package com.it.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BlackPanel extends JPanel {

    private Color circleColor = Color.WHITE; // 初始圆的颜色为白色

    public BlackPanel() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;
                int radius = Math.min(getWidth(), getHeight()) / 4;

                int mouseX = e.getX();
                int mouseY = e.getY();

                // 判断点击位置是否在圆形区域内
                if (Math.pow(mouseX - centerX, 2) + Math.pow(mouseY - centerY, 2) <= Math.pow(radius, 2)) {
                    circleColor = Color.GREEN; // 点击圆形区域后将圆的颜色设为绿色
                    repaint(); // 重新绘制面板
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(getWidth(), getHeight()) / 4;

        g.setColor(circleColor); // 使用当前圆的颜色
        g.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
    }

    public static void main(String[] args) {
            JFrame frame = new JFrame("Black Panel with Circle");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            BlackPanel panel = new BlackPanel();
            frame.add(panel);

            frame.setSize(500, 500);
            frame.setVisible(true);

    }
}

package com.it.test;

import com.it.constant.Constant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlackPanelTime extends JPanel {

    private Color circleColor = Color.WHITE; // 初始圆的颜色为白色
    private Timer timer;
    private float alpha = 1.0f; // 初始透明度为1

    public BlackPanelTime() {
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
                    timer.start(); // 启动定时器，开始颜色渐变
                }
            }
        });

        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (alpha > 0.0f) {
                    alpha -= 0.01f; // 每次减小透明度0.01
                    repaint(); // 重新绘制面板
                } else {
                    timer.stop(); // 透明度为0后停止定时器
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

        Color transparentColor = new Color(circleColor.getRed(), circleColor.getGreen(), circleColor.getBlue(), (int) (alpha * 255));
        g.setColor(transparentColor); // 使用当前圆的颜色和透明度
        g.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Black Panel with Circle");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            BlackPanelTime panel = new BlackPanelTime();
            frame.add(panel);

            frame.setSize(Constant.FRAME_WIDTH, Constant.FRAME_HEIGHT);
            frame.setVisible(true);
        });
    }
}

package com.it.panel;

import com.it.constant.Constant;
import com.it.hander.CirclesHandler;
import com.it.pojo.Circle;
import com.it.pojo.Info;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyPanel extends JPanel {
    private Circle[] circles = CirclesHandler.circles;

    private com.it.panel.GameController gameController;
    private static Image img;
    static {
        String path="src/resources/background.png";

        File file = new File(path);
        BufferedImage read=null;
//        try {
//             read= ImageIO.read(file);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        img=(Image) read;
    }

    public void getGameController(com.it.panel.GameController externalGameController)
    {
        gameController = externalGameController;
    }

    public MyPanel(com.it.panel.GameController externalGameController) {
        getGameController(externalGameController);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Info info = new Info(-1, -1);
                info.setX(e.getX());
                info.setY(e.getY());

                CirclesHandler.handInfo(info);

                rePrintCircle();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }
    public void rePrintCircle(){
        repaint();

        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CirclesHandler.shutUpdate();
                new Timer(1, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        repaint();
                        boolean ok = CirclesHandler.ok();
                        if(ok)
                            ((Timer) e.getSource()).stop();
                    }
                }).start();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        /*g.setColor(Color.black);
        g.fillRect(0,0,getWidth(),getHeight());*/
        g.drawImage(img,0,0,getWidth(),getHeight(),this);



        for (Circle circle : circles) {

            Color c;
            if (!circle.isUpdate())
                c=new Color(circle.getColor().getRed(),circle.getColor().getGreen(),circle.getColor().getBlue(),circle.getAlpha());
            else
                c=Color.blue;
            g.setColor(c);
            if (circle.getColor() != Color.white||circle.isUpdate())
                g.fillOval(circle.getX(), circle.getY(), Constant.CIRCLE_SIZE, Constant.CIRCLE_SIZE);

            g.setColor(Color.white);
            g.drawOval(circle.getX(), circle.getY(), Constant.CIRCLE_SIZE, Constant.CIRCLE_SIZE);

        }

    }



}

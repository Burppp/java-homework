package com.it.controller;

import com.it.constant.Constant;
import com.it.hander.CirclesHandler;
import com.it.panel.MyPanel;
import com.it.pojo.Info;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;


/**
 */
public class GameController {
    private JFrame jFrame;
    private MyPanel myPanel;

    void init() {
        jFrame = new JFrame("");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myPanel = new MyPanel();
        jFrame.add(myPanel);

        jFrame.setSize(Constant.FRAME_WIDTH,Constant.FRAME_HEIGHT);
        jFrame.setVisible(true);
    }

    public static void main(String[] args) {
        GameController gameController = new GameController();

        gameController.init();
        
        gameController.creatRandomMatrix();

        gameController.computerOperate();
    }
    
    public void creatRandomMatrix() {
    	int[] randPoint = {-1, -1, -1, -1, -1};
    	Info[] init = new Info[5];
    	for(int i = 0;i < 5;i++)
    	{
    		init[i] = new Info(-1, -1);
    		randPoint[i] = ((int)(Math.random() * 100)) % 25;
    		int x = CirclesHandler.circles[randPoint[i]].getX();
    		int y = CirclesHandler.circles[randPoint[i]].getY();
    		init[i].setX(x);
    		init[i].setY(y);
    		
    		CirclesHandler.handInfo(init[i]);
    	}
    	myPanel.rePrintCircle();
    }

    public void computerOperate() {

        new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rand = ((int) (Math.random() * 100)) % 25;
                int x = CirclesHandler.circles[rand].getX();
                int y = CirclesHandler.circles[rand].getY();

                Info info = new Info(-1, -1);
                info.setY(y);
                info.setX(x);

                CirclesHandler.handInfo(info);
                myPanel.rePrintCircle();
            }
        }).start();

    }
}

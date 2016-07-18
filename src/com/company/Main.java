package com.company;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            public void run(){
                JFrame frame = new MainFrame("");
                frame.setSize(600,750);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
               // frame.setBackground(Color.getHSBColor(80,60,180));
            }
        });

    }
}
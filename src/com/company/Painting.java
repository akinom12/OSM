package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Monika on 2016-07-16.
 */
public class Painting extends JPanel {
    private Image image;
    public Painting(Image image){
        Dimension size = getPreferredSize();
        size.width = 500;
        size.height = 500;
        setPreferredSize(size);
        this.image = image;

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
         g.drawImage(image, 0, 0, null);
        g.setColor(Color.BLUE);
    }
}




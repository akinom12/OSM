package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by Monika on 2016-06-05.
 */
public class ImageFrame extends JFrame{

    public ImageFrame(File file){
        try{
            NewImage image = new NewImage(file);
            setLayout(new BorderLayout());
            add(image);
            setTitle(file.getName());
            setSize(600,600);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setVisible(true);
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

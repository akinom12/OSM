package com.company;

import org.dcm4che2.data.Tag;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Monika on 2016-05-29.
 */
public class MainFrame extends JFrame {

    private JMenuBar menuBar;
    private JMenuItem close;
    private Tree tree;
    public  MainFrame(String title){
        setLayout(new BorderLayout());
        try {
            tree = new Tree(Tag.PatientID);
            JScrollPane scrollPane = new JScrollPane();
            scrollPane.add(tree);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Container c= getContentPane();
       c.add(tree, BorderLayout.WEST);


        menuBar=new JMenuBar();
        close = new JMenuItem("Zamknij");
        setJMenuBar(menuBar);

        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
            }

    }


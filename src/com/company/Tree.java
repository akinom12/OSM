package com.company;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.util.CloseUtils;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Monika on 2016-05-29.
 */

public class Tree extends JPanel {
    private JTree tree;
    private JButton openButton;
    private Map<String, ArrayList<File>> fileMap;
    private DefaultMutableTreeNode root = new DefaultMutableTreeNode("Images");
    private List<File> fileList = new ArrayList<>();
    public Tree(int tag) {

        Dimension size = getPreferredSize();
        size.width = 300;
        size.height = 400;
        setPreferredSize(size);
        //  setBackground(Color.getHSBColor(80,60,180));

        setBackground(Color.white);
        setBorder(BorderFactory.createTitledBorder("Tree"));
        GridBagConstraints gc=new GridBagConstraints();
        gc.anchor = GridBagConstraints.WEST;
        //add more children
        File folder = new File("./dcmimages/");
        File[] filesAndDirectiories = folder.listFiles();
        for (int i = 0; i < filesAndDirectiories.length; i++) {
            if (filesAndDirectiories[i].isFile()) {
                fileList.add(filesAndDirectiories[i]);
            }
    }
        setDefaultMutableTreeNode(tag);

        openButton = new JButton("Wyswietl obraz");

        gc.gridx=0;
        gc.gridy=0;
        add(openButton, gc);

        gc.gridx=1;
        gc.gridy=1;
        add(tree, gc);

        openButton.setVisible(true);


        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode selectedElement
                            = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
               // String fileName = selectedElement.getUserObject().toString().replace(".\\dcmimages\\",""); //Windows
                String fileName = selectedElement.getUserObject().toString().replace("./dcmimages/",""); //Ubuntu
                //System.out.println(fileName);
                for (File file : fileList) {
                    //   System.out.println(file.getName());
                        if (file.getName().equals(fileName)) {
                            new ImageFrame(file);
                            break;
                        }
                    }
            }
        });
        repaint();

    }

    public void setDefaultMutableTreeNode(int tag) {
        fileMap = createMap(fileList, tag);
        root.removeAllChildren();
        root = new DefaultMutableTreeNode("Images");
        //create the root node
        for (String nodeName : fileMap.keySet()) {
            System.out.println(nodeName);
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
            root.add(node);
            for (File file : fileMap.get(nodeName)) {
                node.add(new DefaultMutableTreeNode(file));
                System.out.println(file.getName());
            }
        }
        tree = new JTree(root);
        tree.add(new Scrollbar());
        tree.repaint();
        add(tree);tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.setVisible(true);
        repaint();
    }

    private String getTagValue(File file, int tag){
        DicomInputStream dis = null;
        DicomObject dcm;
        String tagValue = null;
        try {
            dis = new DicomInputStream(file);
            dcm = dis.readDicomObject();
            tagValue = dcm.getString(tag);
            System.out.println(tagValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            CloseUtils.safeClose(dis);
        }
        return tagValue;
    }

       private Map<String, ArrayList<File>> createMap(List<File> fileList, int tag){
        Map<String,ArrayList<File>> map = new HashMap<>();
        String tagValue;
        for(File file : fileList){
            tagValue = getTagValue(file,tag);
            if(map.containsKey(tagValue)){
                map.get(tagValue).add(file);
            } else {
                ArrayList<File> list= new ArrayList<>();
                list.add(file);
                map.put(tagValue,list);
            }
        }
        return map;
    }


}



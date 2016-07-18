package com.company;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.util.CloseUtils;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * Created by Monika on 2016-06-05.
 */
public class NewImage extends JPanel{
    private Graphics2D g2d;
    private BufferedImage dis=null;
    private BufferedImage dis1=null;
    private Painting painting;
    //public static final int PixelData = 2145386512;
    public NewImage(File file) throws Exception{
        super();
       // dis = ImageIO.read(file);
        dis1 = ImageIO.read(file);
        setBackground(Color.white);
        paintFloatComponent(file);
        readTag(file);
        repaint();
    }
  /*@Override
    public  void paintComponent(Graphics g){
        g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        int w=this.getWidth();
        int h=this.getHeight();
        dis = ScaledImage(dis1, w, h);
        g2d.drawImage(dis,null,0,0);
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
    }*/

    public void paintFloatComponent(File dicomFile) {
        DicomObject dicomObject;
        DicomInputStream dicomInputStream;
        try {

            dicomInputStream=new DicomInputStream(dicomFile);
            dicomObject = dicomInputStream.readDicomObject();
           /* int[] Arr;
            Arr = dicomObject.getInts(Tag.PixelData);*/
            int height=dicomObject.getInt(Tag.Rows);
            int width = dicomObject.getInt(Tag.Columns);

           /* FloatBuffer fb = new FlodicomObject.getFloats(Tag.PixelData);
            Arr = new float[dicomObject.getFloats(Tag.PixelData)];*/

            byte[] bytes = dicomObject.getBytes(Tag.PixelData);
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            float[] Arr = new float[height*width*2];
            int a=0;
           for (int i =0 ;i<height*width*1.9; i++){
                float fb = buffer.getFloat(i);
                 Arr[a]=fb;
               a++;
            }
/*int a =0;
            for (int i=height*width; i>0; i--) {
                float fb = buffer.getFloat(i);
                Arr[a] = fb;
                a++;
            }*/
            System.out.print("fArr length: " + Arr.length + " liczba kolumn: "+ width + " liczba wierszy: "+ height);

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            WritableRaster raster = (WritableRaster) image.getData();
            raster.setPixels(0,0,width,height,Arr);

            image.setData(raster);

            painting = new Painting(image);
            add(painting);
            System.out.print("fArr length: " + Arr.length + " liczba kolumn: "+ width + " liczba wierszy: "+ height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public BufferedImage ScaledImage(Image img, int w, int h){
        BufferedImage resizedImage=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2=resizedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(img,0,0,w,h,null);
        g2.dispose();
        return resizedImage;
    }


    private void readTag (File file){
        DicomInputStream dis = null;
        DicomObject dcm = null;
        try {
            dis = new DicomInputStream(file);
            dcm = dis.readDicomObject();
        } catch (IOException e) {
            System.out.println("Error while reading DICOM tags");
        } finally {
            if (dis != null) {
                CloseUtils.safeClose(dis);
            }

        }
        drawText(dcm);
    }

    public void drawText(DicomObject dcm){
        if (dcm!=null)
        {
            JLabel text = new JLabel("<html>Modalnosc: " + dcm.getString(Tag.Modality)+ "<br>Wiek pacjenta: " + dcm.getString(Tag.PatientAge)+
                    "<br>Data badania: " + dcm.getString(Tag.StudyDate)+ "<br>Opis serii: " + dcm.getString(Tag.SeriesDescription)+" </html>");
            add(text);
        }
}

}

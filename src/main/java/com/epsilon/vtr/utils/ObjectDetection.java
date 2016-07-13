package com.epsilon.vtr.utils;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

public class ObjectDetection {
    public void run() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("\nRunning FaceDetector");

        CascadeClassifier upperBodyDetector = new CascadeClassifier("C:/Users/asabtharishi/workspace1/vtr/vtr/target/classes/haarcascade_frontalface_alt.xml");
        Mat image = Highgui
                .imread("C:\\Users\\asabtharishi\\Desktop\\files\\files\\5.jpg");

        MatOfRect detections = new MatOfRect();
        upperBodyDetector.detectMultiScale(image, detections);

        System.out.println(String.format("Detected %s faces", detections.toArray().length));
        int x = 0;
        int y= 0;
        for (Rect rect : detections.toArray()) {
            x = rect.x/3;
            y = rect.y + rect.height + (rect.y + rect.height) /10;
        }

        String filename = "C:\\Users\\asabtharishi\\Desktop\\files\\files\\output.png";
        System.out.println(String.format("Writing %s", filename));
        Highgui.imwrite(filename, image);

        doAction(x, y);
        System.out.println("done");

    }

    private void doAction(int x, int y){
        try{
            File path = new File("C:\\Users\\asabtharishi\\Desktop\\files\\files\\");

            // load source images

            BufferedImage image = ImageIO.read(new File("C:\\Users\\asabtharishi\\Desktop\\files\\files\\output.png"));

            BufferedImage overlay = ImageIO.read(new File(path, "shirt.jpg"));

            //image = createResizedCopy(image, 691, 720, false);
            overlay = createResizedCopy(overlay, x+y+((x+y)/2), x+y+((x+y)/4), false);

            // BufferedImage image=src.getBufferedImage();
            // BufferedImage overlay =tmp.getBufferedImage();
            // create the new image, canvas size is the max. of both image sizes
            int w = Math.max(image.getWidth(), overlay.getWidth());
            int h = Math.max(image.getHeight(), overlay.getHeight());
            //int w=tmp.width();
            // int h=tmp.height();
            BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

            // paint both images, preserving the alpha channels
            Graphics g = combined.getGraphics();
            g.drawImage(image, 0, 0, null);
            g.drawImage(overlay, x/2, y, null);

            // Save as new image

            ImageIO.write(combined, "PNG", new File(path, "combined.png"));
        }catch(Exception e)
        {
            System.out.println("exception ");
        }
    }

    BufferedImage createResizedCopy(Image originalImage,
            int scaledWidth, int scaledHeight,
            boolean preserveAlpha)
    {
        System.out.println("resizing...");
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }


    public static void main(String[] args) {
        new ObjectDetection().run();
    }
}

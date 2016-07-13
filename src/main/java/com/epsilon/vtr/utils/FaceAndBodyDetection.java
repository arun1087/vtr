package com.epsilon.vtr.utils;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

public class FaceAndBodyDetection {

    public void run() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("\nRunning FaceDetector");

        CascadeClassifier upperBodyDetector = new CascadeClassifier("C:/Users/asabtharishi/workspace1/vtr/vtr/target/classes/haarcascade_frontalface_alt.xml");
        Mat image = Highgui
                .imread("C:\\Users\\asabtharishi\\Desktop\\files\\files\\3.jpg");

        MatOfRect detections = new MatOfRect();
        upperBodyDetector.detectMultiScale(image, detections);

        System.out.println(String.format("Detected %s faces", detections.toArray().length));

        for (Rect rect : detections.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0), 3);
        }

        for (Rect rect : detections.toArray()) {
            rect.width = rect.width *2 + (rect.width/3);
            rect.height = rect.height *2 + rect.height/2;
            rect.x = rect.x - (rect.x/2);
            rect.y = rect.y*5;
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0), 3);
        }

        String filename = "C:\\Users\\asabtharishi\\Desktop\\files\\files\\ouput.png";
        System.out.println(String.format("Writing %s", filename));
        Highgui.imwrite(filename, image);
    }

    public static void main(String[] args) {
        new FaceAndBodyDetection().run();
    }
}




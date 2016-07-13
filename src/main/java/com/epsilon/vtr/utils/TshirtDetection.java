package com.epsilon.vtr.utils;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

public class TshirtDetection {
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

        int first = 0;
        int second = 0;
        int third = 0;
        int fourth = 0;
        Rect rectObj = new Rect();
        for (Rect rect : detections.toArray()) {
            rect.x = rect.x - (rect.x/2);//472
            rect.y = rect.y*5;//3250
            rect.width = rect.width *2 + (rect.width/3);//1782
            rect.height = rect.width;//1910


            rectObj = rect;

            first = rect.x;
            second = rect.x + rect.width;
            third = rect.y;
            fourth = rect.y + rect.height;

            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0), 3);
        }

        String filename = "C:\\Users\\asabtharishi\\Desktop\\files\\files\\ouput.png";
        System.out.println(String.format("Writing %s", filename));
        Highgui.imwrite(filename, image);

        Mat b = Highgui.imread("C:\\Users\\asabtharishi\\Desktop\\files\\files\\ouput.png");

        // Small watermark image
        Mat a = Highgui.imread("C:\\Users\\asabtharishi\\Desktop\\files\\files\\new.jpg");

        Mat bSubmat = b.submat(1340, 3122, 472, 2254);

        a.copyTo(bSubmat);
        Highgui.imwrite("C:\\Users\\asabtharishi\\Desktop\\files\\files\\check.jpg", bSubmat);


     Highgui.imwrite("C:\\Users\\asabtharishi\\Desktop\\files\\files\\merged.jpg", b);
     System.out.println("done");

    }


    public static void main(String[] args) {
        new TshirtDetection().run();
    }
}

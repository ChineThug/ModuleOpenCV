package fr.pa1007.reco;

import org.opencv.core.*;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import static org.opencv.highgui.HighGui.toBufferedImage;
import static org.opencv.imgproc.Imgproc.rectangle;


public class Video extends JLabel {

    static {
        System.load("libs\\" + Core.NATIVE_LIBRARY_NAME);
    }

    private VideoCapture vc;
    private Mat          frame;

    public Video() {
        this.vc = new VideoCapture(0);
        this.frame = new Mat();
    }

    public void start() {
        while (true) {
            if (this.vc.read(frame)) {
                CascadeClassifier faceDetector = new CascadeClassifier("haarcascade_hand_v1.xml");
                MatOfRect         faceDetections = new MatOfRect();
                faceDetector.detectMultiScale(this.frame, faceDetections);
                System.out.println(String.format("Detected %s hand(s)", faceDetections.toArray().length));
                for (Rect rect : faceDetections.toArray()) {
                    rectangle(
                            this.frame,
                            new Point(rect.x, rect.y),
                            new Point(rect.x + rect.width, rect.y + rect.height),
                            new Scalar(0, 255, 0)
                    );
                    this.setText("Perimeter = " + (2 * rect.width + 2 * rect.height));
                }
                ImageIcon image = new MirrorImageIcon(toBufferedImage(this.frame));
                this.setIcon(image);
                this.repaint();
            }
        }
    }
}


package fr.pa1007.reco;

import javax.swing.*;
import java.awt.*;

public class Affichage {

    public Affichage(){
        JFrame fenetre = new JFrame("Webcam PC");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Video v = new Video();
        v.setPreferredSize(new Dimension(1000, 500));
        fenetre.setContentPane(v);
        fenetre.pack();
        fenetre.setVisible(true);
        v.start();
    }

    public static void main(String[] args) {
        new Affichage();
    }
}

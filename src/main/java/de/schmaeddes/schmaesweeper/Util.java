package de.schmaeddes.schmaesweeper;

import javax.swing.*;
import java.awt.*;

public class Util {

    public static Icon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}

package de.schmaeddes.schmaesweeper;

import javax.swing.*;
import java.awt.*;

public class Util {

    public static Icon resizeIcon(ImageIcon icon, Dimension dimension) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(dimension.width, dimension.height,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}

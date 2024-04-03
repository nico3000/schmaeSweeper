package de.schmaeddes.schmaesweeper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class MineField extends JButton {

    private MineFieldType type;
    private int id;
    private boolean revealed;

    public MineField(int id) throws IOException {
        super();
//        this.ui = new MineFieldUI();
        this.id = id;
    }

    public static MineField fromType(MineFieldType type, int id) throws IOException {
        MineField mineField = new MineField(id);
        mineField.setSize(90, 80);
        mineField.setType(type);

        BufferedImage buttonIcon = ImageIO.read(new File("src/main/resources/blank.png"));
        mineField.setIcon(resizeIcon(new ImageIcon(buttonIcon), 50, 50));

        if (type.equals(MineFieldType.MINE)) {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/mineIcon.png")));
            mineField.setDisabledIcon(resizeIcon(icon, 50, 50));
        }

        if (type.equals(MineFieldType.ONE)) {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/one.png")));
            mineField.setDisabledIcon(resizeIcon(icon, 50, 50));
        }

        if (type.equals(MineFieldType.TWO)) {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/two.png")));
            mineField.setDisabledIcon(resizeIcon(icon, 50, 50));
        }

        if (type.equals(MineFieldType.THREE)) {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/three.png")));
            mineField.setDisabledIcon(resizeIcon(icon, 50, 50));
        }

        if (type.equals(MineFieldType.FOUR)) {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/four.png")));
            mineField.setDisabledIcon(resizeIcon(icon, 50, 50));
        }

        mineField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    ImageIcon icon = null;
                    try {
                        icon = new ImageIcon(ImageIO.read(new File("src/main/resources/flag.png")));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    mineField.setIcon(resizeIcon(icon, 50, 50));
                }
            }
        });

        mineField.addActionListener(e -> {
            mineField.setEnabled(false);
        });

        return mineField;
    }

    public MineFieldType getType() {
        return this.type;
    }

    public void setType(MineFieldType type) {
        this.type = type;
    }

    public int getId() {
        return this.id;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    private static Icon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    public enum MineFieldType {
        MINE(9),
        EMPTY(0),
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4);

        public int value;

        MineFieldType(int value) {
            this.value = value;
        }
    }
}


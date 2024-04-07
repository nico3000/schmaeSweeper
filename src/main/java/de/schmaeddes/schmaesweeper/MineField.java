package de.schmaeddes.schmaesweeper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static de.schmaeddes.schmaesweeper.Util.resizeIcon;

public class MineField extends JButton {

    private MineFieldButtonType type;
    private final int id;
    private boolean revealed;

    private final Dimension size = new Dimension(25, 25);

    public MineField(int id) throws IOException {
        super();
        this.id = id;
        setPreferredSize(size);
        setMinimumSize(size);

        resetButton();
    }

    public int getId() {
        return this.id;
    }

    public MineFieldButtonType getType() {
        return this.type;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public void resetButton() throws IOException {
        BufferedImage buttonIcon = ImageIO.read(new File("src/main/resources/blank.png"));

        setEnabled(true);
        setRevealed(false);
        setIcon(resizeIcon(new ImageIcon(buttonIcon), size));

        for (ActionListener listener : getActionListeners()) {
            removeActionListener(listener);
        }

        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    ImageIcon icon = null;
                    try {
                        icon = new ImageIcon(ImageIO.read(new File("src/main/resources/flag.png")));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    setIcon(resizeIcon(icon, size));
                }
            }
        });

        addActionListener(e -> setEnabled(false));
    }

    public void setToType(MineFieldButtonType type) throws IOException {
        this.type = type;

        if (type.equals(MineFieldButtonType.MINE)) {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/mineIcon.png")));
            setDisabledIcon(resizeIcon(icon, size));
        }

        if (type.equals(MineFieldButtonType.ONE)) {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/one.png")));
            setDisabledIcon(resizeIcon(icon, size));
        }

        if (type.equals(MineFieldButtonType.TWO)) {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/two.png")));
            setDisabledIcon(resizeIcon(icon, size));
        }

        if (type.equals(MineFieldButtonType.THREE)) {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/three.png")));
            setDisabledIcon(resizeIcon(icon, size));
        }

        if (type.equals(MineFieldButtonType.FOUR)) {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/four.png")));
            setDisabledIcon(resizeIcon(icon, size));
        }

        if (type.equals(MineFieldButtonType.EMPTY)) {
            setDisabledIcon(null);
        }
    }

    public enum MineFieldButtonType {
        MINE(9),
        EMPTY(0),
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4);

        public int value;

        MineFieldButtonType(int value) {
            this.value = value;
        }
    }
}

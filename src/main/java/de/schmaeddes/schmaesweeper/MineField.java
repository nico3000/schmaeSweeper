package de.schmaeddes.schmaesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import static de.schmaeddes.schmaesweeper.Util.resizeIcon;

public class MineField extends JButton {
    private static final Dimension size = new Dimension(25, 25);
    private static final Icon blankIcon = resizeIcon(new ImageIcon(MineField.class.getResource("/blank.png")), size);
    private static final Icon flagIcon = resizeIcon(new ImageIcon(MineField.class.getResource("/flag.png")), size);
    private static final Icon mineIcon = resizeIcon(new ImageIcon(MineField.class.getResource("/mineIcon.png")), size);
    private static final Icon oneIcon = resizeIcon(new ImageIcon(MineField.class.getResource("/one.png")), size);
    private static final Icon twoIcon = resizeIcon(new ImageIcon(MineField.class.getResource("/two.png")), size);
    private static final Icon threeIcon = resizeIcon(new ImageIcon(MineField.class.getResource("/three.png")), size);
    private static final Icon fourIcon = resizeIcon(new ImageIcon(MineField.class.getResource("/four.png")), size);

    private MineFieldButtonType type;
    private final int id;
    private boolean revealed;

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
        setEnabled(true);
        setRevealed(false);
        setIcon(blankIcon);

        for (ActionListener listener : getActionListeners()) {
            removeActionListener(listener);
        }

        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    setIcon(flagIcon);
                }
            }
        });

        addActionListener(e -> setEnabled(false));
    }

    public void setToType(MineFieldButtonType type) throws IOException {
        this.type = type;
        setDisabledIcon(switch (type) {
            case MINE -> mineIcon;
            case ONE -> oneIcon;
            case TWO -> twoIcon;
            case THREE -> threeIcon;
            case FOUR -> fourIcon;
            case EMPTY -> null;
        });
    }

    public enum MineFieldButtonType {
        MINE(9),
        EMPTY(0),
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4);

        public final int value;

        MineFieldButtonType(int value) {
            this.value = value;
        }
    }
}

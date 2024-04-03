package de.schmaeddes.schmaesweeper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static de.schmaeddes.schmaesweeper.MineField.MineFieldType.*;
import static de.schmaeddes.schmaesweeper.MineField.MineFieldType.EMPTY;

public class MainWindow extends JFrame {

    private static final List<Integer> mineIds = MineIdGenerator.generateBombIds(81, 10);
    private static List<MineField> mineFields = new ArrayList<>();

    private JPanel mainPanel;
    private JLabel numberOfBombsField;
    private JLabel timeField;
    private JPanel mineGrid;
    private JPanel infoPanel;
    private JButton restartButton;

    Timer timer = new Timer(1000, e -> addSecondToTimer());

    public MainWindow() throws IOException {
        timeField.setText("0");

        setContentPane(mainPanel);
        setTitle("SchmaeSweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Spielfeld bauen


        numberOfBombsField.setText("10");


        restartButton.addActionListener(e -> {
            try {
                blablubb();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


        blablubb();

        pack();
        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new MainWindow();
    }


    private void buildMineGrid() {
        mineGrid.setLayout(new GridLayout(9,9));

        for (int i = 1; i <= 81; i++) {

        }

    }

    public void blablubb() throws IOException {


        for (int i = 1; i <= 81; i++) {

            if (mineIds.contains(i)){
                MineField mineField = MineField.fromType(MINE, i);

                ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/mineIconRed.png")));
                Image resizedImage = icon.getImage().getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
                icon.setImage(resizedImage);

                mineField.setContentAreaFilled(false);
                mineField.addActionListener(e -> {
                    revealAllMines();
                    mineField.setDisabledIcon(icon);
                    timer.stop();

                });



                mineGrid.add(mineField);
                mineFields.add(mineField);
            } else {
                MineField mineField;

                int numberOfBombs = getNumberOfBombs(i);
                switch (numberOfBombs) {
                    case(1) -> mineField = MineField.fromType(ONE, i);
                    case(2) -> mineField = MineField.fromType(TWO, i);
                    case(3) -> mineField = MineField.fromType(THREE, i);
                    case(4) -> mineField = MineField.fromType(FOUR, i);
                    default -> {
                            mineField = MineField.fromType(EMPTY, i);
                            mineField.addActionListener(e ->
                                    revealAdjacentEmptyFields(mineField.getId()));

                    }
                }

                mineField.addActionListener(e -> {
                    if (!timer.isRunning()) {
                        timer.start();
                    }
                });

                mineField.setLayout(null);
                mineGrid.add(mineField);
                mineFields.add(mineField);

            }
        }
    }

    private static void revealAdjacentEmptyFields(int id) {
        List<Integer> surroundedFields = getSurroundedFields(id);
        getMineFieldFromId(id).setRevealed(true);

        for (int field : surroundedFields) {
            MineField mineField = getMineFieldFromId(field);
            mineField.setEnabled(false);


            if (mineField.getType().equals(EMPTY) && !mineField.isRevealed()) {
                System.out.println("Found another empty: " + mineField.getId());
                revealAdjacentEmptyFields(mineField.getId());
            }
        }
    }

    private static void revealAllMines() {
        for(MineField mineField : mineFields) {
            if (mineField.getType().equals(MINE)) {
                mineField.setEnabled(false);
            }
        }
    }

    private static int getNumberOfBombs(int id) {
        List<Integer> surroundedFields = getSurroundedFields(id);

        int sum = 0;

        for (int field : surroundedFields) {
            if (mineIds.contains(field)) {
                sum++;
            }
        }

        return sum;
    }

    private static List<Integer> getSurroundedFields(int id) {
        List<Integer> surroundedFields = new ArrayList<>();
        if (id % 9 != 1) {

            if (id > 9) {
                // top left
                surroundedFields.add(id - 10);
            }
            // left
            surroundedFields.add(id - 1);

            if (id < 73) {
                // bottom left
                surroundedFields.add(id + 8);
            }
        }

        if (id % 9 != 0) {

            if (id > 9) {
                // top right
                surroundedFields.add(id - 8);
            }
            // right
            surroundedFields.add(id + 1);

            if (id < 73) {
                // bottom right
                surroundedFields.add(id + 10);
            }
        }

        if (id > 9) {
            // top
            surroundedFields.add(id - 9);
        }

        if (id < 73) {
            // bottom
            surroundedFields.add(id + 9);
        }

        return surroundedFields;
    }

    private static MineField getMineFieldFromId(int id) {
        return mineFields.stream().filter(x -> x.getId() == id).findFirst().get();
    }

    private void addSecondToTimer() {
        timeField.setText(Integer.toString(Integer.parseInt(timeField.getText()) + 1));
    }

    public void startTimer() {
        this.timer.start();
    }
}

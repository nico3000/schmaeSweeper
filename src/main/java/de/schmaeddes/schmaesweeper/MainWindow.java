package de.schmaeddes.schmaesweeper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame {

    private static List<Integer> mineIds = MineIdGenerator.generateBombIds(81, 10);
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

        numberOfBombsField.setText("10");


        restartButton.addActionListener(e -> {
            try {
                newGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        mineGrid.setLayout(new GridLayout(9,9));
        buildMineGrid();

        newGame();
        pack();
        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new MainWindow();
    }


    private void buildMineGrid() throws IOException {
        mineGrid.setLayout(new GridLayout(9,9));

        for (int i = 1; i <= 81; i++) {
            MineField mineField = new MineField(i);

            mineGrid.add(mineField);
            mineFields.add(mineField);
        }
    }

    public void newGame() throws IOException {
        mineIds = MineIdGenerator.generateBombIds(81, 10);

        for (MineField mineField : mineFields) {

            mineField.resetButton();

            if (mineIds.contains(mineField.getId())){

                mineField.setToType(MineField.MineFieldButtonType.MINE);

                ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/mineIconRed.png")));
                Image resizedImage = icon.getImage().getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
                icon.setImage(resizedImage);

                mineField.setContentAreaFilled(false);
                mineField.addActionListener(e -> {
                    revealAllMines();
                    mineField.setDisabledIcon(icon);
                    timer.stop();
                });

            } else {
                int numberOfBombs = getNumberOfBombs(mineField.getId());
                switch (numberOfBombs) {
                    case(1) -> mineField.setToType(MineField.MineFieldButtonType.ONE);
                    case(2) -> mineField.setToType(MineField.MineFieldButtonType.TWO);
                    case(3) -> mineField.setToType(MineField.MineFieldButtonType.THREE);
                    case(4) -> mineField.setToType(MineField.MineFieldButtonType.FOUR);
                    default -> {
                        mineField.setToType(MineField.MineFieldButtonType.EMPTY);
                        mineField.addActionListener(e ->
                                revealAdjacentEmptyFields(mineField.getId()));

                    }
                }

                mineField.addActionListener(e -> {
                    if (!timer.isRunning()) {
                        timer.start();
                    }
                });
            }
        }
    }

    public static void revealAdjacentEmptyFields(int id) {
        List<Integer> surroundedFields = getSurroundedFields(id);
        getMineFieldFromId(id).setRevealed(true);

        for (int field : surroundedFields) {
            MineField mineField = getMineFieldFromId(field);
            mineField.setEnabled(false);


            if (mineField.getType().equals(MineField.MineFieldButtonType.EMPTY) && !mineField.isRevealed()) {
                System.out.println("Found another empty: " + mineField.getId());
                revealAdjacentEmptyFields(mineField.getId());
            }
        }
    }

    private static void revealAllMines() {
        for(MineField mineField : mineFields) {
            if (mineField.getType().equals(MineField.MineFieldButtonType.MINE)) {
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
}

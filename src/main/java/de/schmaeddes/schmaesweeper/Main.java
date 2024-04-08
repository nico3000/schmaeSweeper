package de.schmaeddes.schmaesweeper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {

    private static List<Integer> mineIds = MineIdGenerator.generateBombIds(81, 10);
    private static final List<MineField> mineFields = new ArrayList<>();

    private final JPanel mineGrid = new JPanel(new GridLayout(16, 16));
    private final InfoPanel infoPanel = new InfoPanel(10);
    private final JButton restartButton = new JButton("Restart");

    public Main() throws IOException {
        JFrame mainWindow = new JFrame("SchmaeSweeper");

        mainWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setLayout(new BorderLayout());

        JPanel headPanel = new JPanel(new FlowLayout());
        headPanel.add(infoPanel);
        headPanel.add(restartButton);

        mainWindow.add(headPanel, BorderLayout.NORTH);
        mainWindow.add(mineGrid, BorderLayout.CENTER);

        restartButton.addActionListener(e -> {
            try {
                newGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        buildMineGrid();

        newGame();
        mainWindow.pack();
        mainWindow.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new Main();
    }

    private void buildMineGrid() throws IOException {
        mineGrid.setBackground(Color.darkGray);

        Dimension dimension = new Dimension(320,320);

        mineGrid.setPreferredSize(dimension);
        mineGrid.setMinimumSize(dimension);

        for (int i = 1; i <= 256; i++) {
            MineField mineField = new MineField(i);

            mineGrid.add(mineField);
            mineFields.add(mineField);
        }
    }

    public void newGame() throws IOException {
        mineIds = MineIdGenerator.generateBombIds(81, 10);

        ImageIcon icon = new ImageIcon(ImageIO.read(MineField.class.getResourceAsStream("/mineIconRed.png")));
        Image resizedImage = icon.getImage().getScaledInstance(20, 20,  Image.SCALE_SMOOTH);
        for (MineField mineField : mineFields) {

            mineField.resetButton();

            if (mineIds.contains(mineField.getId())){

                mineField.setToType(MineField.MineFieldButtonType.MINE);

                icon.setImage(resizedImage);

                mineField.setContentAreaFilled(false);
                mineField.addActionListener(e -> {
                    revealAllMines();
                    mineField.setDisabledIcon(icon);
                    infoPanel.stopTimer();
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

                mineField.addActionListener(e -> infoPanel.startTimer());
            }
        }
    }

    public void revealAdjacentEmptyFields(int id) {
        List<Integer> surroundedFields = getSurroundedFields(id, 16);
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

    private void revealAllMines() {
        for(MineField mineField : mineFields) {
            if (mineField.getType().equals(MineField.MineFieldButtonType.MINE)) {
                mineField.setEnabled(false);
            }
        }
    }

    private int getNumberOfBombs(int id) {
        List<Integer> surroundedFields = getSurroundedFields(id, 16);

        int sum = 0;

        for (int field : surroundedFields) {
            if (mineIds.contains(field)) {
                sum++;
            }
        }

        return sum;
    }

    private List<Integer> getSurroundedFields(int id, int rows) {
        List<Integer> surroundedFields = new ArrayList<>();
        int maxRow = rows * (rows - 1);

        if (id % rows != 1) {

            if (id > rows) {
                // top left
                surroundedFields.add(id - (rows + 1));
            }
            // left
            surroundedFields.add(id - 1);

            if (id <= maxRow) {
                // bottom left
                surroundedFields.add(id + (rows - 1));
            }
        }

        if (id % rows != 0) {

            if (id > rows) {
                // top right
                surroundedFields.add(id - (rows - 1));
            }
            // right
            surroundedFields.add(id + 1);

            if (id <= maxRow) {
                // bottom right
                surroundedFields.add(id + (rows + 1));
            }
        }

        if (id > rows) {
            // top
            surroundedFields.add(id - rows);
        }

        if (id <= maxRow) {
            // bottom
            surroundedFields.add(id + rows);
        }

        return surroundedFields;
    }

    private static MineField getMineFieldFromId(int id) {
        return mineFields.stream().filter(x -> x.getId() == id).findFirst().orElseThrow();
    }

}

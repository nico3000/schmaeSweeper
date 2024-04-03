//package de.schmaeddes.schmaesweeper;
//
//import javax.swing.*;
//import java.awt.*;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import static de.schmaeddes.schmaesweeper.MineField.MineFieldType.*;
//
//
//public class Main {
//
//    private static final List<Integer> mineIds = MineIdGenerator.generateBombIds(81, 10);
//    private static final List<MineField> mineFields = new ArrayList<>();
//
//    public static void oldMethod() throws IOException {
//        JFrame frame = new JFrame("Schmaesweeper");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        JPanel infoPanel = new JPanel();
//        infoPanel.setLayout(new GridLayout(2, 2));
//        infoPanel.add(new JLabel("Feld 1"));
//        infoPanel.add(new JLabel("Feld 2"));
//        infoPanel.add(new JLabel("Feld 3"));
//        infoPanel.add(new JLabel("Feld 4"));
//
//        infoPanel.setSize(new Dimension(540, 200));
//
//
//        JPanel gridPanel = new JPanel();
//        gridPanel.add(infoPanel);
//
////        jPanel.setVisible(true);
//        gridPanel.setSize(new Dimension(540, 540));
//        gridPanel.setLayout(new GridLayout(9, 9));
//
//        for (int i = 1; i <= 81; i++) {
//
//            if (mineIds.contains(i)){
//                MineField mineField = MineField.fromType(MINE, i);
//
//                mineField.setContentAreaFilled(false);
//                mineField.addActionListener(e -> revealAllMines());
//
//                gridPanel.add(mineField);
//                mineFields.add(mineField);
//            } else {
//                MineField mineField;
//
//                int numberOfBombs = getNumberOfBombs(i);
//                switch (numberOfBombs) {
//                    case(1) -> mineField = MineField.fromType(ONE, i);
//                    case(2) -> mineField = MineField.fromType(TWO, i);
//                    case(3) -> mineField = MineField.fromType(THREE, i);
//                    case(4) -> mineField = MineField.fromType(FOUR, i);
//                    default -> mineField = MineField.fromType(EMPTY, i);
//                }
//
//                mineField.setForeground(Color.blue);
//
//                mineField.setLayout(null);
//                gridPanel.add(mineField);
//                mineFields.add(mineField);
//
//            }
//        }
//
////        window.getContentPane().getComponent(10).setForeground(Color.blue);
//
//        frame.add(gridPanel);
//        frame.pack();
//        frame.setVisible(true);
////        window.revalidate();
//    }
//
//    private static void revealAllMines() {
//        for(MineField mineField : mineFields) {
//            if (mineField.getType().equals(MINE)) {
//                mineField.setEnabled(false);
//            }
//        }
//    }
//
//    private static int getNumberOfBombs(int id) {
//        List<Integer> surroundedFields = new ArrayList<>();
//        if (id % 9 != 1) {
//            // top left
//            surroundedFields.add(id - 10);
//            // left
//            surroundedFields.add(id - 1);
//            // bottom left
//            surroundedFields.add(id + 8);
//        }
//
//        if (id % 9 != 0) {
//            // top right
//            surroundedFields.add(id - 8);
//            // right
//            surroundedFields.add(id + 1);
//            // bottom right
//            surroundedFields.add(id + 10);
//        }
//
//        // top
//        surroundedFields.add(id - 9);
//        // bottom
//        surroundedFields.add(id + 9);
//
//        int sum = 0;
//
//        for (int field : surroundedFields) {
//            if (mineIds.contains(field)) {
//                sum++;
//            }
//        }
//
//        return sum;
//    }
//
//
//
////    private static List<Integer> generateBombIds(int size, int numberOfBombs) {
////        List<Integer> randomInts = new ArrayList<>();
////        Random random = new Random();
////
////        while(randomInts.size() != numberOfBombs) {
////            int randomNumber = random.nextInt(size - 1);
////
////            if (!randomInts.contains(randomNumber)) {
////                randomInts.add(randomNumber);
////            }
////        }
////
////        return randomInts.stream().sorted().toList();
////    }
//}

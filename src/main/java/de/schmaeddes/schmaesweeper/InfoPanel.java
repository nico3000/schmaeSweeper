package de.schmaeddes.schmaesweeper;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {

    private JLabel bombCounter;
    private JLabel timeCounter = new JLabel("0");
    Timer timer = new Timer(1000, e -> addSecondToTimer());

    public InfoPanel(int numberOfBombs) {
        this.bombCounter = new JLabel("10");

        setLayout(new GridLayout(2, 2));

        add(new JLabel("Bombs:"));
        add(bombCounter);

        add(new JLabel("Time:"));
        add(timeCounter);

        this.bombCounter.setText(Integer.toString(numberOfBombs));
    }

    public void startTimer() {
        this.timer.start();
    }

    public void stopTimer() {
        this.timer.stop();
    }

    private void addSecondToTimer() {
        timeCounter.setText(Integer.toString(Integer.parseInt(timeCounter.getText()) + 1));
    }

}

package de.schmaeddes.schmaesweeper;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MineIdGenerator {

    private MineIdGenerator(){}

    public static List<Integer> generateBombIds(int size, int numberOfBombs) {
        Set<Integer> randomInts = new HashSet<>();
        Random random = new Random();

        while (randomInts.size() != numberOfBombs) {
            randomInts.add(random.nextInt(size - 1));
        }

        return randomInts.stream().sorted().toList();
    }

}

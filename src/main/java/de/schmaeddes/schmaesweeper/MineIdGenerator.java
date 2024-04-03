package de.schmaeddes.schmaesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MineIdGenerator {

    public static List<Integer> generateBombIds(int size, int numberOfBombs) {
        List<Integer> randomInts = new ArrayList<>();
        Random random = new Random();

        while(randomInts.size() != numberOfBombs) {
            int randomNumber = random.nextInt(size - 1);

            if (!randomInts.contains(randomNumber)) {
                randomInts.add(randomNumber);
            }
        }

        return randomInts.stream().sorted().toList();
    }

}

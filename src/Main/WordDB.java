package Main;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class WordDB {

    private ArrayList<String[]> riddles;
    int currentIndex;

    public WordDB() {
        try {
            riddles = new ArrayList<>();

            InputStream filePath = getClass().getClassLoader().getResourceAsStream("minigame/data.txt");
            if (filePath == null) {
                throw new FileNotFoundException("data.txt not found in resources/minigame/");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    riddles.add(new String[]{parts[0].trim(), parts[1].trim()});
                }
            }

            Collections.shuffle(riddles);
            currentIndex = 0;

        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public String[] loadChallenge() {
        if (currentIndex < riddles.size()) {
            String[] pair = riddles.get(currentIndex++);
            return new String[]{pair[0], pair[1].toUpperCase()};
        } else {
            return null; // No more riddles
        }
    }
    public void reset() {
        currentIndex = 0;
        Collections.shuffle(riddles);
    }
}

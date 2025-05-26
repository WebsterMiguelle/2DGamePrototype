package Main;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class WordDB {

    private HashMap<String, String[]> wordList;
    private ArrayList<String> categories;

    public WordDB() {
        try{
            wordList = new HashMap<>();
            categories = new ArrayList<>();

            InputStream filePath = getClass().getClassLoader().getResourceAsStream("minigame/data.txt");
            if (filePath == null) {
                throw new FileNotFoundException("data.txt not found in resources/minigame/");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(filePath));

            String line;
            while((line = reader.readLine()) != null){
                String[] parts = line.split(",");

                String category = parts[0];
                categories.add(category);

                String values[] = Arrays.copyOfRange(parts, 1, parts.length);
                wordList.put(category, values);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public String[] loadChallenge() {
        Random random = new Random();

        String category = categories.get(random.nextInt(categories.size()));

        String[] categoryValues = wordList.get(category);
        String word = categoryValues[random.nextInt(categoryValues.length)];

        return new String[]{category.toUpperCase(), word.toUpperCase()};
    }

    public static String hideWords(String words) {
        String hiddenWord = "";
        for (int i = 0; i < words.length(); i++) {
            if(!(words.charAt(i) == ' ')) {
                hiddenWord += words.charAt(i) == '_' ? "_ " : words.charAt(i) + " ";
            }else{
                hiddenWord += "  ";
            }
        }
        return hiddenWord;
    }
}
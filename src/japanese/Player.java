package japanese;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Player {
    private final String path = "player_results.dat";
    private final boolean isNewPlayer;
    private HashMap<String, int[]> results = new HashMap<>(2);

    Player(boolean isNewPlayer) {
        this.isNewPlayer = isNewPlayer;
        if (isNewPlayer) {
            newFile();
        } else {
            if (!new File(path).exists()) {
                newFile();
            }
            loadResults();
        }
    }

    public void newFile() {
        if (isNewPlayer) {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(
                    new FileOutputStream(path))) {
                int countOfMoras = getCountofMoras();
                int[] resultsArray = new int[countOfMoras];
                results.put("hiragana", resultsArray);
                results.put("katakana", resultsArray);
                outputStream.writeObject(results);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadResults() {
        try (ObjectInputStream inputStream = new ObjectInputStream(
                new FileInputStream(path))) {
            results = (HashMap<String, int[]>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateResults(String alphabetName, int[] newResults) {
        results.replace(alphabetName, newResults);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                new FileOutputStream(path))) {
            outputStream.writeObject(results);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, int[]> getResults() {
        return results;
    }

    public int getCountofMoras(){
        String rmjPath = "/romaji.txt";
        InputStream rmjStream = getClass().getResourceAsStream(rmjPath);
        final ArrayList<String> romaji = new ArrayList<>();
        Scanner scForRmj = new Scanner(rmjStream, "UTF-8");
        while (scForRmj.hasNext()) {
            romaji.add(scForRmj.next()); //те саме
        }
        scForRmj.close();
        return romaji.size();
    }
}
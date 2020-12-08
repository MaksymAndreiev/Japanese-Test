package japanese;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ArchaicAlphabet {
    private final String alphabetName; //змінна, що відповідає за один з двох алфавітів японської
    private String currentMora; //та мора (слог) алфавіту, яку треба відгадати (яп)
    private String guessingMora; //та мора, яку задає користувач, коли відгадує (лат)
    private final ArrayList<String> mora = new ArrayList<>(); //масив для всіх мор одного з алфавіту
    private final ArrayList<String> romaji = new ArrayList<>(); //масив ромаджі (латинізація японської)
    private final HashMap<String, String> moraMap = new HashMap<>(); //словник {мора:ромаджі}

    ArchaicAlphabet(String alphabetName) {
        this.alphabetName = alphabetName;
        this.currentMora = getCurrentMora();
    }

    public String getAlphabetName() {
        return alphabetName;
    }

    public void setCurrentMora(String currentMora) {
        this.currentMora = currentMora;
    }

    public String getCurrentMora() {
        return currentMora;
    }

    public void setGuessingMora(String guessingMora) {
        this.guessingMora = guessingMora;
    }

    public String getGuessingMora() {
        return guessingMora;
    }

    public HashMap<String, String> getMoraMap() {
        return moraMap;
    }

    public String getRomajiByIndex(int index) {
        return romaji.get(index);
    }

    public int getIndexOfMora(String moraForIndex) {
        return mora.indexOf(moraForIndex);
    }

    public int getIndexOfRomaji(String romajiForIndex) {
        return romaji.indexOf(romajiForIndex);
    }

    public String getMoraByIndex(int index) {
        return mora.get(index);
    }

    //вигружає алфавіт, вказаний в конструкторі, з файлу, робить словник типу {мора:ромаджі}
    public ArrayList<String> loadAlphabet() {
        //шлях до мор одного з алфавітів
        String jpPath = String.format("src\\japanese\\%s.txt", this.alphabetName);
        String jpArchPath = String.format("src\\japanese\\old_%s.txt", this.alphabetName);
        //шлях до мор ромаджі
        String rmjPath = "src\\japanese\\romaji.txt";
        String rmjArchPath = "src\\japanese\\old_romaji.txt";

        try {
            Scanner scForJp = new Scanner(new File(jpPath));
            Scanner scForArchJp = new Scanner(new File(jpArchPath));
            Scanner scForRmj = new Scanner(new File(rmjPath));
            Scanner scForArchRmj = new Scanner(new File(rmjArchPath));
            while (scForJp.hasNext()) {
                mora.add(scForJp.next()); //з файлу кожна мора додається у масив
            }
            while (scForArchJp.hasNext()){
                mora.add(scForArchJp.next());
            }
            while (scForRmj.hasNext()) {
                romaji.add(scForRmj.next()); //те саме
            }
            while (scForArchRmj.hasNext()){
                romaji.add(scForArchRmj.next());
            }
            scForJp.close();
            scForRmj.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < mora.size(); i++) {
            if (!mora.get(i).equals("null")) {
                if (moraMap.containsValue(romaji.get(i))){
                    ////////////видалити нові еквіваленти за ромаджі
                }
                moraMap.put(mora.get(i), romaji.get(i)); //робить словник з відповідних мор японською та латиницею
            }
        }
        return mora;
    }
}
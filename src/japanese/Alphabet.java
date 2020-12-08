package japanese;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Alphabet {
    private final String alphabetName; //змінна, що відповідає за один з двох алфавітів японської
    private String currentMora; //та мора (слог) алфавіту, яку треба відгадати (яп)
    private String guessingMora; //та мора, яку задає користувач, коли відгадує (лат)
    private final ArrayList<String> mora = new ArrayList<>(); //масив для всіх мор одного з алфавіту
    private final ArrayList<String> romaji = new ArrayList<>(); //масив ромаджі (латинізація японської)
    private final HashMap<String, String> moraMap = new HashMap<>(); //словник {мора:ромаджі}

    Alphabet(String alphabetName) {
        this.alphabetName = alphabetName;
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

    /*public int getCountOfMoras(){
        return mora.size();
    }*/

    //вигружає алфавіт, вказаний в конструкторі, з файлу, робить словник типу {мора:ромаджі}
    public ArrayList<String> loadAlphabet() {
        //шлях до мор одного з алфавітів
        String jpPath = String.format("/%s.txt", this.alphabetName);

        //шлях до мор ромаджі
        String rmjPath = "/romaji.txt";

        InputStream jpStream = getClass().getResourceAsStream(jpPath);
        InputStream rmjStream = getClass().getResourceAsStream(rmjPath);


        Scanner scForAlph = new Scanner(jpStream, "UTF-8");
        Scanner scForRmj = new Scanner(rmjStream, "UTF-8");
        while (scForAlph.hasNext()) {
            mora.add(scForAlph.next()); //з файлу кожна мора додається у масив
        }
        while (scForRmj.hasNext()) {
            romaji.add(scForRmj.next()); //те саме
        }
        scForAlph.close();
        scForRmj.close();

        for (int i = 0; i < mora.size(); i++) {
            if (!mora.get(i).equals("null")||!romaji.get(i).equals("null")) {
                moraMap.put(mora.get(i), romaji.get(i)); //робить словник з відповідних мор японською та латиницею
            }
        }
        return mora;
    }
}
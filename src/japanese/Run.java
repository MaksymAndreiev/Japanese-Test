package japanese;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Run {
    private final Player player;

    Run(Player player) {
        this.player = player;
    }

    public void setCurrentMora(Alphabet alphabet, ArrayList<String> mora) {
        //вибирає випадкову мору, яка залежить від часу, щоб кожний екземпляр мав унікальне значення
        Random random = new Random(System.currentTimeMillis());
        String attempt = mora.get(random.nextInt(mora.size()));
        int counter = 0, i = 0;
        while (getResultOfMora(alphabet, attempt) > i) {
            int index = alphabet.getIndexOfMora(attempt);
            if (index == 45) {
                attempt = alphabet.getMoraByIndex(0);
            } else {
                attempt = alphabet.getMoraByIndex(index + 1);
            }
            counter++;
            //усього getCountofMoras() мор, якщо лічильник більше getCountofMoras(), то всі мори були вгадані і+1 та більше разів
            if (counter > player.getCountofMoras()) {
                i++; //тепер шукаємо тільки ті, що були вгадані і(після і++) раз. Якщо всі були вгадані більше 1 разу,
                // то все продовжується до нескінченності, поки не буде, що хоча б одна мора була вгадана і раз,
                // коли всі інші і+1
            }
        }
        alphabet.setCurrentMora(attempt);
    }

    //варіант з тестом
    public HashMap<Integer, String> chooseToAnswer(Alphabet alphabet) {
        Random random = new Random();
        HashMap<Integer, String> posElement = new HashMap<>(4); // словник типу {позиція:мора}
        boolean flagOfSmthIsWrong;

        int[] position = {1, 2, 3, 4}; //можливі позиції в тесті
        int pos = random.nextInt(4); //позиція на якій буде знаходитись правильна відповідь

        //змінна, яка зберігає індекс правильної відповіді
        int indexOfCurrentMora = alphabet.getIndexOfMora(alphabet.getCurrentMora());

        for (int i : position) {
            flagOfSmthIsWrong = false;
            if (pos + 1 == i) { //в словник розміщується правильна відповідь
                posElement.put(i, alphabet.getRomajiByIndex(indexOfCurrentMora));
            } else { //інші позиції заповнюються неправильними варіантами
                //випадок коли рандом видає ту саму мору, котра є відповіддю
                String romaji = alphabet.getRomajiByIndex(random.nextInt(player.getCountofMoras()));

                //якщо випадковий варіант неправильної відповіді є правильним
                if (romaji.equals(alphabet.getRomajiByIndex(indexOfCurrentMora))) {
                    flagOfSmthIsWrong = true;
                    //вибір нового варіанту
                    if (indexOfCurrentMora + pos < player.getCountofMoras()) {
                        posElement.put(i, alphabet.getRomajiByIndex(indexOfCurrentMora + pos));
                    } else {
                        int newPosition = indexOfCurrentMora + pos - player.getCountofMoras();
                        posElement.put(i, alphabet.getRomajiByIndex(newPosition));
                    }
                }
                //випадок повторення з попереднім варіантом

                for (int j = 1; j <= i; j++) {
                    if (romaji.equals(posElement.get(j)) && i > 1) {
                        flagOfSmthIsWrong = true;
                        //завжди є перевірка щоб будь-який новий індекс не співпадал з правильним
                        int index = alphabet.getIndexOfRomaji(posElement.get(j));
                        if (index + pos < player.getCountofMoras() && index + pos != indexOfCurrentMora) {
                            posElement.put(i, alphabet.getRomajiByIndex(index + pos));
                        } else {
                            int newPosition;
                            if (index + pos > 45) {
                                newPosition = index + pos - player.getCountofMoras();
                            } else {
                                newPosition = index + pos + random.nextInt(player.getCountofMoras() - index - pos);
                                if (newPosition > 45) {
                                    newPosition = newPosition - player.getCountofMoras();
                                }
                            }
                            if (newPosition != indexOfCurrentMora) {
                                posElement.put(i, alphabet.getRomajiByIndex(newPosition));
                            } else {
                                posElement.put(i, alphabet.getRomajiByIndex(newPosition + 1));
                            }
                        }
                    }
                }
                if (!flagOfSmthIsWrong) {
                    posElement.put(i, romaji);
                }
            }
        }
        return posElement;
    }

    public void setResult(Alphabet alphabet, String mora) {
        int[] res = player.getResults().get(alphabet.getAlphabetName());
        res[alphabet.getIndexOfMora(mora)]++;
        player.updateResults(alphabet.getAlphabetName(), res);
    }

    public int getResultOfMora(Alphabet alphabet, String mora) {
        int[] res = player.getResults().get(alphabet.getAlphabetName().toLowerCase());
        return res[alphabet.getIndexOfMora(mora)];
    }
}
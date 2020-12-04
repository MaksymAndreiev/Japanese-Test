package japanese;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Random;


public class StartClass {

    JFrame frame = new JFrame("Simple alphabet test");
    private final Dimension screenSize;

    private JPanel panel0;
    private JPanel panel1;

    private JPanel panel2;

    private JPanel panel3;
    private JLabel moraLabel;
    private JTextField answerField;

    private JPanel panel4;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;

    private JPanel panel5;
    private JLabel results;


    private Player player;
    private Alphabet alphabet;
    private Run run;

    private int countOfMatches;
    private int retries;
    private int countOfReturn = 0;
    private boolean firstTime;
    private boolean noFont;

    public static void main(String[] args) {

        new StartClass();
    }

    public StartClass() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        URL iconURL = getClass().getResource("/torii-gate.png");
        Image icon = new ImageIcon(iconURL).getImage();
        frame.setIconImage(icon);

        Toolkit kit = Toolkit.getDefaultToolkit();
        screenSize = kit.getScreenSize();

        createPanel2();
        createPanel5();

        if (new File("player_results.dat").exists()) {
            firstTime = false;
            createPanel1();
            frame.getContentPane().add(panel1);
        } else {
            firstTime = true;
            createPanel0();
            frame.getContentPane().add(panel0);
        }
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void switchPanel(JPanel panel) {
        SwingUtilities.invokeLater(() -> {
            frame.getContentPane().removeAll();
            frame.add(panel);
            panel.setPreferredSize(frame.getContentPane().getSize());
            panel.setLocation(frame.getContentPane().getLocation());
            panel.setVisible(true);
            frame.pack();
            frame.repaint();
        });
    }

    private void createPanel0() {
        panel0 = new JPanel();
        panel0.setBackground(new Color(-1));
        panel0.setLayout(new GridBagLayout());
        panel0.setPreferredSize(new Dimension(screenSize.width / 2, screenSize.height / 2));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton start_button = new JButton();
        start_button.setBackground(new Color(-1));
        start_button.setBorderPainted(false);
        start_button.setContentAreaFilled(false);
        start_button.setEnabled(true);
        start_button.setFocusPainted(false);
        Font start_buttonFont = this.getFont("beer money", -1, 36, start_button.getFont());
        if (start_buttonFont != null) start_button.setFont(start_buttonFont);
        start_button.setForeground(new Color(-3407872));
        start_button.setText("Почати тест");
        panel0.add(start_button, gbc);

        StartClicked new_test = new StartClicked(true);
        start_button.addActionListener(new_test);
    }

    private void createPanel1() {
        panel1 = new JPanel();
        panel1.setBackground(new Color(-1));
        panel1.setLayout(new GridBagLayout());
        if (countOfReturn == 0) {
            panel1.setPreferredSize(new Dimension(screenSize.width / 2, screenSize.height / 2));
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton continueButton = new JButton();
        continueButton.setBackground(new Color(-1));
        continueButton.setBorderPainted(false);
        continueButton.setContentAreaFilled(false);
        continueButton.setEnabled(true);
        continueButton.setFocusPainted(false);
        Font continue_buttonFont = this.getFont("beer money", -1, 36, continueButton.getFont());
        if (continue_buttonFont != null) continueButton.setFont(continue_buttonFont);
        continueButton.setForeground(new Color(-3407872));
        continueButton.setText("Продовжити");
        panel1.add(continueButton, gbc);

        JButton newButton = new JButton();
        newButton.setBackground(new Color(-1));
        newButton.setBorderPainted(false);
        newButton.setContentAreaFilled(false);
        newButton.setEnabled(true);
        newButton.setFocusPainted(false);
        Font new_buttonFont = this.getFont("beer money", -1, 36, newButton.getFont());
        if (new_buttonFont != null) newButton.setFont(new_buttonFont);
        newButton.setForeground(new Color(-3407872));
        newButton.setText("Скинути результати");
        panel1.add(newButton, gbc);

        StartClicked cont = new StartClicked(false);
        StartClicked new_ = new StartClicked(true);

        continueButton.addActionListener(cont);
        newButton.addActionListener(new_);
    }

    private void createPanel2() {
        panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        panel2.setBackground(new Color(-1));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton hiraganaButton = new JButton();
        hiraganaButton.setBackground(new Color(-1));
        hiraganaButton.setBorderPainted(false);
        hiraganaButton.setContentAreaFilled(false);
        hiraganaButton.setEnabled(true);
        hiraganaButton.setFocusPainted(false);
        Font hiragana_buttonFont = this.getFont("beer money", -1, 36, hiraganaButton.getFont());
        if (hiragana_buttonFont != null) hiraganaButton.setFont(hiragana_buttonFont);
        hiraganaButton.setForeground(new Color(-3407872));
        hiraganaButton.setText("Хірагана");
        panel2.add(hiraganaButton, gbc);

        JButton katakanaButton = new JButton();
        katakanaButton.setBackground(new Color(-1));
        katakanaButton.setBorderPainted(false);
        katakanaButton.setContentAreaFilled(false);
        katakanaButton.setEnabled(true);
        katakanaButton.setFocusPainted(false);
        Font katakana_buttonFont = this.getFont("beer money", -1, 36, katakanaButton.getFont());
        if (katakana_buttonFont != null) katakanaButton.setFont(katakana_buttonFont);
        katakanaButton.setForeground(new Color(-3407872));
        katakanaButton.setText("Катакана");
        panel2.add(katakanaButton, gbc);

        AlphabetClicked h_clicked = new AlphabetClicked("hiragana");
        AlphabetClicked k_clicked = new AlphabetClicked("katakana");

        hiraganaButton.addActionListener(h_clicked);
        katakanaButton.addActionListener(k_clicked);
    }

    private void createPanel3() {
        panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        panel3.setBackground(new Color(-1));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        moraLabel = new JLabel();
        Font moraLabelFont = this.getFont("UD Digi Kyokasho NK-B", -1, 72, moraLabel.getFont());
        if (moraLabelFont != null) moraLabel.setFont(moraLabelFont);
        moraLabel.setForeground(new Color(-3407872));
        moraLabel.setHorizontalAlignment(0);
        panel3.add(moraLabel, gbc);

        answerField = new JTextField(3);
        Font answer_fieldFont = this.getFont(null, -1, 36, answerField.getFont());
        if (answer_fieldFont != null) answerField.setFont(answer_fieldFont);
        panel3.add(answerField, gbc);
        answerField.addActionListener(new TextWritten());
    }

    private void createPanel4() {
        ButtonGroup buttonGroup = new ButtonGroup();
        panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        panel4.setBackground(new Color(-1));


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        moraLabel = new JLabel();
        Font moraLabelFont = this.getFont("UD Digi Kyokasho NK-B", -1, 72, moraLabel.getFont());
        if (moraLabelFont != null) moraLabel.setFont(moraLabelFont);
        moraLabel.setForeground(new Color(-3407872));
        moraLabel.setHorizontalAlignment(0);
        panel4.add(moraLabel, gbc);

        radioButton1 = new JRadioButton();
        radioButton1.setText("RadioButton");
        radioButton1.setContentAreaFilled(false);
        radioButton1.setFocusPainted(false);
        Font radioButtonFont = this.getFont(null, -1, 28, radioButton1.getFont());
        if (radioButtonFont != null) radioButton1.setFont(radioButtonFont);
        radioButton1.setForeground(new Color(-16777216));
        buttonGroup.add(radioButton1);
        panel4.add(radioButton1, gbc);

        radioButton2 = new JRadioButton();
        radioButton2.setText("RadioButton");
        radioButton2.setContentAreaFilled(false);
        radioButton2.setFocusPainted(false);
        radioButtonFont = this.getFont(null, -1, 28, radioButton2.getFont());
        if (radioButtonFont != null) radioButton2.setFont(radioButtonFont);
        radioButton2.setForeground(new Color(-16777216));
        buttonGroup.add(radioButton2);
        panel4.add(radioButton2, gbc);


        radioButton3 = new JRadioButton();
        radioButton3.setText("RadioButton");
        radioButton3.setContentAreaFilled(false);
        radioButton3.setFocusPainted(false);
        radioButtonFont = this.getFont(null, -1, 28, radioButton3.getFont());
        if (radioButtonFont != null) radioButton3.setFont(radioButtonFont);
        radioButton3.setForeground(new Color(-16777216));
        buttonGroup.add(radioButton3);
        panel4.add(radioButton3, gbc);


        radioButton4 = new JRadioButton();
        radioButton4.setText("RadioButton");
        radioButton4.setContentAreaFilled(false);
        radioButton4.setFocusPainted(false);
        radioButtonFont = this.getFont(null, -1, 28, radioButton4.getFont());
        if (radioButtonFont != null) radioButton4.setFont(radioButtonFont);
        radioButton4.setForeground(new Color(-16777216));
        buttonGroup.add(radioButton4);
        panel4.add(radioButton4, gbc);

        ButtonSelected buttonSelected = new ButtonSelected();
        radioButton1.addActionListener(buttonSelected);
        radioButton2.addActionListener(buttonSelected);
        radioButton3.addActionListener(buttonSelected);
        radioButton4.addActionListener(buttonSelected);
    }

    private void createPanel5() {
        panel5 = new JPanel();
        panel5.setLayout(new GridBagLayout());
        panel5.setBackground(new Color(-1));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;

        results = new JLabel();
        Font resultsFont = this.getFont("beer money", -1, 36, results.getFont());
        if (resultsFont != null) results.setFont(resultsFont);
        results.setForeground(new Color(-3407872));
        panel5.add(results, gbc);

        JButton returnToMenu = new JButton();
        returnToMenu.setBackground(new Color(-1));
        returnToMenu.setBorderPainted(false);
        returnToMenu.setContentAreaFilled(false);
        returnToMenu.setEnabled(true);
        returnToMenu.setFocusPainted(false);
        Font returnToMenuFont = this.getFont("beer money", -1, 36, returnToMenu.getFont());
        if (returnToMenuFont != null) returnToMenu.setFont(returnToMenuFont);
        returnToMenu.setForeground(new Color(-3407872));
        returnToMenu.setText("Повернутися до головного меню");
        returnToMenu.addActionListener(new ReturnToMenuButtonListener());
        panel5.add(returnToMenu, gbc);

        JButton continueTest = new JButton();
        continueTest.setBackground(new Color(-1));
        continueTest.setBorderPainted(false);
        continueTest.setContentAreaFilled(false);
        continueTest.setEnabled(true);
        continueTest.setFocusPainted(false);
        Font continueTestFont = this.getFont("beer money", -1, 36, continueTest.getFont());
        if (continueTestFont != null) continueTest.setFont(continueTestFont);
        continueTest.setForeground(new Color(-3407872));
        continueTest.setText("Пройти тест ще раз");
        continueTest.addActionListener(new ContinueTestListener());
        panel5.add(continueTest, gbc);
    }

    private void setPlayer(Player player) {
        this.player = player;
    }

    public void setAlphabet(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    public void setRun(Run run) {
        this.run = run;
    }

    private Font getFont(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    class StartClicked implements ActionListener {
        private final boolean isNewPlayer;

        public StartClicked(boolean isNewPlayer) {
            this.isNewPlayer = isNewPlayer;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setPlayer(new Player(isNewPlayer));
            setRun(new Run(player));
            switchPanel(panel2);
        }
    }

    class AlphabetClicked implements ActionListener {
        private final String alphabetName;

        public AlphabetClicked(String alphabetName) {
            this.alphabetName = alphabetName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            retries = 0;
            if (player != null) {
                setAlphabet(new Alphabet(alphabetName));
                run.setCurrentMora(alphabet, alphabet.loadAlphabet());
            }
            Random random = new Random(System.currentTimeMillis());
            if (random.nextInt(2) == 1) {
                createPanel3();
                moraLabel.setText(alphabet.getCurrentMora());
                switchPanel(panel3);
            } else {
                createPanel4();
                moraLabel.setText(alphabet.getCurrentMora());
                HashMap<Integer, String> mapOfChoices = run.chooseToAnswer(alphabet);
                radioButton1.setText(mapOfChoices.get(1));
                radioButton2.setText(mapOfChoices.get(2));
                radioButton3.setText(mapOfChoices.get(3));
                radioButton4.setText(mapOfChoices.get(4));
                switchPanel(panel4);
            }
        }
    }

    class TextWritten implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            retries++;
            alphabet.setGuessingMora(answerField.getText());
            if (alphabet.getMoraMap().get(alphabet.getCurrentMora()).equals(alphabet.getGuessingMora())) {
                countOfMatches++;
                run.setResult(alphabet, alphabet.getCurrentMora());
            }
            if (retries < 5) {
                run.setCurrentMora(alphabet, alphabet.loadAlphabet());
                Random random = new Random(System.currentTimeMillis());
                if (random.nextInt(2) == 1) {
                    createPanel3();
                    moraLabel.setText(alphabet.getCurrentMora());
                    switchPanel(panel3);
                } else {
                    createPanel4();
                    moraLabel.setText(alphabet.getCurrentMora());
                    HashMap<Integer, String> mapOfChoices = run.chooseToAnswer(alphabet);
                    radioButton1.setText(mapOfChoices.get(1));
                    radioButton2.setText(mapOfChoices.get(2));
                    radioButton3.setText(mapOfChoices.get(3));
                    radioButton4.setText(mapOfChoices.get(4));
                    switchPanel(panel4);
                }
            } else {
                results.setText("Ваш результат: " + countOfMatches / (double) (retries) * 100 + "%");
                switchPanel(panel5);
                retries = 0;
                countOfMatches = 0;
            }
        }
    }

    class ButtonSelected implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            retries++;
            JRadioButton radioButton = (JRadioButton) e.getSource();
            alphabet.setGuessingMora(radioButton.getText());
            if (alphabet.getMoraMap().get(alphabet.getCurrentMora()).equals(alphabet.getGuessingMora())) {
                countOfMatches++;
                run.setResult(alphabet, alphabet.getCurrentMora());
            }
            if (retries < 5) {
                run.setCurrentMora(alphabet, alphabet.loadAlphabet());
                Random random = new Random(System.currentTimeMillis());
                if (random.nextInt(2) == 1) {
                    createPanel3();
                    moraLabel.setText(alphabet.getCurrentMora());
                    switchPanel(panel3);
                } else {
                    createPanel4();
                    moraLabel.setText(alphabet.getCurrentMora());
                    HashMap<Integer, String> mapOfChoices = run.chooseToAnswer(alphabet);
                    radioButton1.setText(mapOfChoices.get(1));
                    radioButton2.setText(mapOfChoices.get(2));
                    radioButton3.setText(mapOfChoices.get(3));
                    radioButton4.setText(mapOfChoices.get(4));
                    switchPanel(panel4);
                }
            } else {
                results.setText("Ваш результат: " + countOfMatches / (double) (retries) * 100 + "%");
                switchPanel(panel5);
                retries = 0;
                countOfMatches = 0;
            }
        }
    }

    class ContinueTestListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Random random = new Random(System.currentTimeMillis());
            if (random.nextInt(2) == 1) {
                createPanel3();
                moraLabel.setText(alphabet.getCurrentMora());
                switchPanel(panel3);
            } else {
                createPanel4();
                moraLabel.setText(alphabet.getCurrentMora());
                HashMap<Integer, String> mapOfChoices = run.chooseToAnswer(alphabet);
                radioButton1.setText(mapOfChoices.get(1));
                radioButton2.setText(mapOfChoices.get(2));
                radioButton3.setText(mapOfChoices.get(3));
                radioButton4.setText(mapOfChoices.get(4));
                switchPanel(panel4);
            }
        }
    }

    class ReturnToMenuButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            countOfReturn++;
            if (firstTime){
                createPanel1();
            }
            switchPanel(panel1);
        }
    }
}
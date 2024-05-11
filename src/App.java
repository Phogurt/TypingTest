//imports
import javax.swing.*;
import java.awt.Font;
import java.awt.event.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class App implements ActionListener, KeyListener {
    //defining static variables
    static Timer timer = null;
    static JTextField inputBox;
    static JFrame f;
    static KeyListener[] keyListeners;
    static JTextArea results;
    static JTextArea textArea;
    static JLabel timerLabel;
    static String[] sample;
    static String[] words;
    public static int time;


    public static void game(int set) throws Exception {
        sample=sampleText(set);
        int width = 800, height = 550;
        f = new JFrame("Typing Test");
        //initializing fonts
        Font font1 = new Font("Arial", Font.PLAIN, 30);
        Font font2 = new Font("Arial", Font.PLAIN, 20);
        Font font3 = new Font("Arial", Font.BOLD, 30);

        //setting up title
        JLabel l1 = new JLabel("Welcome to our typing test. Press any key to start.");
        l1.setBounds(25, 35, width - 60, 40);
        l1.setFont(font3);
        //setting up text area
        textArea = new JTextArea();
        textArea.setBounds(15, 120, width - 50, 215);
        textArea.setFont(font1);

        inputBox = new JTextField();
        inputBox.setSize(width - 50, 50);
        inputBox.setLocation(15, 365);
        inputBox.setFont(font2);

        timerLabel = new JLabel();
        timerLabel.setBounds(375, 85, width - 50, 30);
        timerLabel.setFont(font3);

        JButton startButton = new JButton("Restart");
        startButton.setBounds(410, height - 100, 100, 30);
        JButton backButton = new JButton("Back");
        backButton.setBounds(290, height - 100, 100, 30);
        // Initialize keyListeners array
        keyListeners = inputBox.getKeyListeners();

        App app = new App(); // Create an instance of App to use non-static methods
        startButton.addActionListener(app);
        backButton.addActionListener(app);
        inputBox.addKeyListener(app);

        f.add(backButton);
        f.add(timerLabel);
        f.add(textArea);
        f.add(l1);
        f.add(inputBox);
        f.add(startButton);
        f.setSize(width, height);
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        words = generateWords();
    }

    public void actionPerformed(ActionEvent e) {
        // Checking if test already started so we don't start it twice
        JButton button = (JButton)e.getSource();
        if(button.getText()=="Restart") {
            if (timer != null && timer.isRunning()) {
                timer.stop();
            }
            if (keyListeners.length == 0) {
                inputBox.addKeyListener(this);
            }
            inputBox.setText("");
            words = generateWords();
            textArea.setVisible(true);
            inputBox.setVisible(true);
        }
        else if (button.getText()=="Back"){
            menu.main(null);
            f.dispose();
        }
    }

    public void keyTyped(KeyEvent e) {
        if (timer == null || !timer.isRunning()) {
            startTimer();
        }
    }

    // Implementing dummy methods to fulfill requirements
    public void keyPressed(KeyEvent e) {
    }
    public void keyReleased(KeyEvent e) {
    }

    public static void startTimer() {
        timer = new Timer(1000, new TimerListener());
        timer.start();
    }

    static class TimerListener implements ActionListener {
        int elaTime = time;

        public void actionPerformed(ActionEvent e) {
            if (elaTime == 0) {
                timer.stop();
                testOver(inputBox.getText(), words);
            } else {
                elaTime--;
            }
            timerLabel.setText(String.valueOf(elaTime));
        }
    }

    public static void testOver(String text, String[] words) {
        KeyListener[] keyListeners = inputBox.getKeyListeners();
        int correct = 0;
        int errors = 0;
        double accuracy;
        double wpm;
        if (keyListeners.length > 0) {
            inputBox.removeKeyListener(keyListeners[0]); // Remove the first KeyListener associated with the text field
        }
        String[] input = text.split(" ");
        for (int i = 0; i < input.length; i++) {
            if(i>=words.length){
                errors+=input[i].length();
                continue;
            }
            for (int j = 0; j < Math.min(input[i].length(), words[i].length()); j++) {
                if (input[i].charAt(j) == words[i].charAt(j)) {
                    correct++;
                } else
                    errors++;
            }
        
            if (input[i].length() <= words[i].length() && i < input.length - 1)
                errors += words[i].length() - input[i].length();
            else if (input[i].length() > words[i].length()) errors += input[i].length() - words[i].length();
        }
        accuracy = Math.round((correct / (double) (correct + errors)*100.0))/100.0;
        wpm = Math.round(((correct / (5.0 * time)) * 60)*100.0)/100.0;
        inputBox.setVisible(false);
        String r = " WPM: " + wpm + "\n Accuracy: " + accuracy * 100 + "%";
        textArea.setEditable(true);
        textArea.setText(r);
        textArea.setEditable(false);
        textArea.setVisible(true);
    }

    public static String[] sampleText(int set) {
        String[] sets={"lib\\1000commonWords.txt","lib\\5000commonWords.txt","lib\\1000spanish.txt"};
        try {
            File wordsSet = new File(sets[set]);
            String[] words = new String[Integer.valueOf(sets[set].substring(4,8))];
            Scanner myReader;
            myReader = new Scanner(wordsSet);
            int c=0;
            while (myReader.hasNextLine()) {
                words[c] = myReader.nextLine();
                c++;
            }
            myReader.close();
            return words;
        } catch (FileNotFoundException e) {
            String[] error = {"error"};
            e.printStackTrace();
            return error;
        }
    }

    public static String[] generateWords() {
        int textLength = 0;
        String word;
        String[] words = new String[50];
        textArea.setEditable(true);
        textArea.setText("");
        for (int i = 0; i < 50; i++) {
            word = words[i] = sample[(int) (Math.random() * sample.length-1) + 1];
            textArea.append(" " + word);
            textLength += word.length() + 1;
            if (textLength > 48) {
                textArea.append("\n");
                textLength = 0;
            }
        }
        textArea.setEditable(false);
        return words;
    }
}
import javax.swing.*;
import java.awt.Font;
import java.awt.event.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class App implements ActionListener, KeyListener {
    static Timer timer = null;
    static JTextField inputBox;
    static JFrame f;
    static KeyListener[] keyListeners;
    static JTextArea results;
    static JTextArea textArea;
    static String[] sample=sampleText();
    static String[]words;
    static int time=10;

    public static void main(String[] args) throws Exception {
        int width = 800, height = 550;
        f = new JFrame("Typing Test");
        Font font1=new Font("Arial",Font.PLAIN,30);
        Font font2=new Font("Arial",Font.PLAIN,20);
        Font font3=new Font("Arial",Font.BOLD,30);

        JLabel l1 = new JLabel("Welcome to our typing test. Press any key to start.");
        l1.setBounds(25,50,width-60, 40);
        l1.setFont(font3);

        textArea=new JTextArea();
        textArea.setBounds(15,100,width-50,215);
        textArea.setFont(font1);

        results=new JTextArea();
        results.setBounds(15,100,width-50,220);
        results.setVisible(false);
        results.setFont(font1);

        inputBox = new JTextField();
        inputBox.setSize(width - 50, 50);
        inputBox.setLocation(15, 345);
        inputBox.setFont(font2);

        JButton startButton = new JButton("Restart");
        startButton.setBounds(width / 2 - 10 - 50, height - 100, 100, 30);

        // Initialize keyListeners array
        keyListeners = inputBox.getKeyListeners();

        App app = new App(); // Create an instance of App to use non-static methods
        startButton.addActionListener(app);
        inputBox.addKeyListener(app);
        f.add(results);
        f.add(textArea);
        f.add(l1);
        f.add(inputBox);
        f.add(startButton);
        f.setSize(width, height);
        f.setLayout(null);
        //f.
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        words=generateWords();
    }

    public void actionPerformed(ActionEvent e) {
        // Checking if test already started so we don't start it twice
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        if (keyListeners.length == 0) {
            inputBox.addKeyListener(this);
        }
        inputBox.setText("");
        words=generateWords();
        textArea.setVisible(true);
        inputBox.setVisible(true);
        results.setVisible(false);
    }

    public void keyTyped(KeyEvent e) {
        if (timer == null || !timer.isRunning()) {
            startTimer(time);
        }
    }

    // Implementing dummy methods to fulfill requirements
    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    
    public static void startTimer(int time) {
        timer = new Timer(time * 1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                testOver(inputBox.getText(),words);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public static void testOver(String text,String[] words) {
        KeyListener[] keyListeners = inputBox.getKeyListeners();
        int correct = 0;
        int errors=0;
        double accuracy;
        double wpm;
        if (keyListeners.length > 0) {
            inputBox.removeKeyListener(keyListeners[0]); // Remove the first KeyListener associated with the text field
        }
        String[] input=text.split(" ");
        for(int i=0; i<input.length; i++){
            for (int j=0;j<Math.min(input[i].length(),words[i].length());j++){
                if(input[i].charAt(j)==words[i].charAt(j)){
                    correct++;
                }
                else errors++; 
            }
            if(input[i].length()<=words[i].length()&&i<input.length-1) errors+=words[i].length()-input[i].length();
            else if (input[i].length()>words[i].length()) errors+=input[i].length()-words[i].length();
        }
        accuracy=correct/(double)(correct+errors);
        wpm=(correct/(5.0*time))*60;
        textArea.setVisible(false);
        inputBox.setVisible(false);
        results.setEditable(true);
        String r=" WPM: "+wpm+"\n Accuracy: "+accuracy*100+"%";
        System.out.println(r);
        results.setText(r);
        results.setEditable(false);
        results.setVisible(true);
        System.out.println(text);
        System.out.println(correct);
        System.out.println(errors);
        System.out.println(accuracy);
        System.out.println(wpm);
        
    }
    public static String[] sampleText(){
        
        try {
            File words1000 = new File("lib\\1000commonWords.txt");
            String[] words=new String[996];
            Scanner myReader;
            myReader = new Scanner(words1000);
            for(int i=0;i<996;i++) {
                words[i] = myReader.nextLine();
            }
            myReader.close();
            return words;
        } catch (FileNotFoundException e) {
            String[] error={"error"};
            e.printStackTrace();
            return error;
        }  
    } 
    public static String[] generateWords(){
        int textLength=0;
        String word;
        String[]words=new String[50];
        textArea.setEditable(true);
        textArea.setText("");
        for(int i = 0; i<50;i++){
            word=words[i]=sample[(int)(Math.random()*996)+1];
            textArea.append(" "+word);
            textLength+=word.length()+1;
            if(textLength>48){
                textArea.append("\n");
                textLength=0;
            }
        }
        textArea.setEditable(false);
        return words;
    } 
}

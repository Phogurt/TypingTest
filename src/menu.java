//imports
import javax.swing.*;
import java.awt.Font;
import java.awt.event.*;

public class menu extends JFrame{
    //defining static variables
    static JFrame menu;
    static JLabel l,l1,l2;
    static JComboBox<String> wordSets;
    static JComboBox<Integer> times;
    static int set;
    static int time;
    public static void main(String[] args) {
        //initializing fonts
        Font font2 = new Font("Arial", Font.PLAIN, 20);
        Font font3 = new Font("Arial", Font.BOLD, 30);
        
        // Create components
        menu = new JFrame("Selector");
        l = new JLabel("Select the word set");
        l.setFont(font2);
        l1= new JLabel("Choose the time constraint");
        l1.setFont(font2);
        l2=new JLabel("Welcome To Our Typing Test");
        l2.setFont(font3);
        String[] sets = { "1000 most common","5000 most common","Spanish"};
        Integer[] timeConstraints={15,30,60,120};
        times = new JComboBox<Integer>(timeConstraints);
        wordSets = new JComboBox<String>(sets);
        wordSets.setSelectedIndex(0);
        JButton confirm = new JButton("Start Game");

        //if start game button is pressed
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Pass the selected time and wordset to the App class then close the menu
                    App.time = (int)times.getSelectedItem();
                    set = wordSets.getSelectedIndex();
                    App.game(set);
                    menu.dispose();
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        // Set positions and sizes
        l.setBounds(120, 170, 250, 30);
        l1.setBounds(410, 170, 250, 30);
        l2.setBounds(180,20,430,100);
        times.setBounds(410, 200, 250, 30);
        wordSets.setBounds(120, 200, 250, 30);
        confirm.setBounds(320,350,140,30);

        // Add components to the window
        menu.add(l);
        menu.add(l1);
        menu.add(l2);
        menu.add(times);
        menu.add(wordSets);
        menu.add(confirm);

        // Set window properties
        menu.setLayout(null);
        menu.setSize(800, 550);
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}

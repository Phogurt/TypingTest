import javax.swing.*;
import java.awt.event.*;

public class menu extends JFrame{

    static JFrame menu;
    static JLabel l,l1,l2;
    static JComboBox<String> wordSets;
    static JComboBox<Integer> times;
    static int set;
    static int time;
    public static void main(String[] args) {
        menu = new JFrame("Selector");

        // Create components
        l = new JLabel("Select the word set");
        l1= new JLabel("Choose the time constraint");
        l2=new JLabel("Welcome To Our Typing Test");
        String[] sets = { "1000 most common","5000 most common","Spanish","Polish","Korean"};
        Integer[] timeConstraints={15,30,60,120};
        times = new JComboBox<Integer>(timeConstraints);
        wordSets = new JComboBox<String>(sets);
        wordSets.setSelectedIndex(0);

        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    App.time = (int)times.getSelectedItem();
                    set = wordSets.getSelectedIndex();
                    App.game(set);
                    menu.dispose();
                    // Pass the selected time to the App class
                    
                } catch (Exception ex) {
                    ex.printStackTrace(); // Print the stack trace of the exception
                    // Handle the exception as needed
                }
                
            }
        });
        // Set positions and sizes
        l.setBounds(10, 200, 150, 30);
        l1.setBounds(430, 200, 200, 30);
        l2.setBounds(250,20,300,100);
        times.setBounds(600, 200, 150, 30);
        wordSets.setBounds(130, 200, 150, 30);
        confirm.setBounds(340,350,100,30);

        // Add components to the frame
        menu.add(l);
        menu.add(l1);
        menu.add(l2);
        menu.add(times);
        menu.add(wordSets);
        menu.add(confirm);

        // Set frame properties
        menu.setLayout(null);
        menu.setSize(800, 550);
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}

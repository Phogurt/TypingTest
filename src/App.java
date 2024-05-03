import javax.swing.*;
import java.awt.event.*;  

public class App {
    static Timer timer;
    static JTextField t;
    public static void main(String[] args) throws Exception {
        int width=400,height=400;
        JFrame f=new JFrame();
        JLabel l1= new JLabel("Welcome to our typing test. Press any key to start.");
        l1.setBounds(width/2-10-150,50,300,30);
        
        t=new JTextField();
        t.setSize(width-50,50);
        t.setLocation(15,100);

        JButton startButton=new JButton("Restart");  
        startButton.setBounds(width/2-10-50,height-100, 100,30);

        startTimer();
        startButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                //checking if test already started so we dont start it twice
                if(timer.isRunning()){
                    timer.stop();
                }
                //10 second timer
                t.setText("");
                startTimer();
            }
        });
        


        f.add(l1);
        f.add(t);
        f.add(startButton);
        f.setSize(width,height);
        f.setLayout(null);  
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
    public static void startTimer(){
        int elaTime = 30;
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.start();
                if (elaTime == 0) {
                    timer.stop();
                    testOver();
                }
                else{
                    elaTime--;
                }
                PlayFrame.timerLabel.setText(seconds);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    public static void testOver(String text){
        System.out.println(text);
    }

    public static void goodT(){

    }
    
}

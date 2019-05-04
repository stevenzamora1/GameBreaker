package breakout;


import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {

        JFrame jframe = new JFrame();
        Gameplay gameplay = new Gameplay();

        jframe.setTitle("Breakout");
        jframe.setBounds(10, 10, 700, 600);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setResizable(false);

        jframe.add(gameplay);
        jframe.setVisible(true);
    }

}

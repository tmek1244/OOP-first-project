package agh.cs.project1.visualization;

import javax.swing.*;

import java.awt.*;

public class Visualization extends JFrame{
    public Visualization() {

        initUI();
    }

    private void initUI() {
        add(new Board());
        setResizable(false);
        setBackground(Color.BLACK);
        pack();

        setTitle("Evolution");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Visualization ex = new Visualization();
            ex.setVisible(true);
        });
    }
}

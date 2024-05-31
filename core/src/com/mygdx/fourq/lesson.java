package com.mygdx.fourq;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class lesson implements ActionListener{
    JFrame frame;
    JLabel lessonL;
    JButton returnButton;
    
    public lesson() {
        frame = new JFrame("Lesson GUI");
        lessonL = new JLabel(new ImageIcon("/Users/macbookpro/Documents/School/CS Stuff/4th-Qua/assets/lesson.png")); // Replace with the actual path
        returnButton = new JButton("Go Back!");
    }
    
        public void setFrame() {
        // Set up the layout
        frame.setLayout(new GraphPaperLayout(new Dimension(20, 40)));

        // Add components to the layout
        frame.add(returnButton, new Rectangle(0, 0, 4, 2));
        frame.add(lessonL, new Rectangle(0, 0, 20, 40));

        // Add action listener to the buttons
        returnButton.addActionListener(this);

        // Set up the frame
        frame.setSize(1280, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
        
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == returnButton) {
            automations q = new automations();
            q.setFrame();
            frame.dispose();
        }
    }
    
    public static void main(String[] args) {
        lesson q = new lesson();
        q.setFrame();
    }
}

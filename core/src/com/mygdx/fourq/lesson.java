package com.mygdx.fourq;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class lesson implements ActionListener {
    JFrame frame;
    JLabel lessonL;
    JButton returnButton;
    Clip buttonSound;

    public lesson() {
        frame = new JFrame("Lesson GUI");
        lessonL = new JLabel(new ImageIcon("C:\\Users\\TEMP.DESKTOP-1NFA8JO\\IdeaProjects\\4th-Quarter-Project\\assets\\lesson.png"));
        returnButton = new JButton("Go Back!");

        try {
            File soundFile = new File("sfx/press1.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            buttonSound = AudioSystem.getClip();
            buttonSound.open(audioIn);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void setFrame() {
        frame.setLayout(new GraphPaperLayout(new Dimension(20, 40)));
        frame.add(returnButton, new Rectangle(0, 0, 4, 2));
        frame.add(lessonL, new Rectangle(0, 0, 20, 40));
        returnButton.addActionListener(this);
        frame.setSize(1280, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (buttonSound != null) {
            buttonSound.setFramePosition(0);
            buttonSound.start();
        }
        if (e.getSource() == returnButton) {
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

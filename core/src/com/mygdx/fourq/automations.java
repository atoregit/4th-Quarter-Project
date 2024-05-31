package com.mygdx.fourq;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class automations implements ActionListener{
    JFrame frame;
    JLabel statementL, lessonL, figureL, promptL, angleAL, sideBL, angleCL;
    JTextField angleATF, sideBTF, angleCTF;
    JButton calculateButton, MenuButton, lessonButton, returnButton;
    JTextArea resultTextArea;
    
    public automations() {
        frame = new JFrame("Automation GUI");
        figureL = new JLabel(new ImageIcon("assets/figure.png")); // Replace with the actual path
        statementL = new JLabel("Solving an Oblique Triangle Given Two Angles and an Included Side (ASA Case)");
        promptL = new JLabel("Enter angle A, side B, and angle C:");
        angleAL = new JLabel("Angle A (alpha) in degrees:");
        sideBL = new JLabel("Side B:");
        angleCL = new JLabel("Angle C (gamma) in degrees:");
        angleATF = new JTextField();
        sideBTF = new JTextField();
        angleCTF = new JTextField();
        calculateButton = new JButton("Calculate");
        MenuButton = new JButton("Menu");
        lessonButton = new JButton("Learn More!");
        resultTextArea = new JTextArea();
    }
        public void setFrame() {
        // Set up the layout
        frame.setLayout(new GraphPaperLayout(new Dimension(20, 40)));

        // Add components to the layout
        frame.add(statementL, new Rectangle(1, 3, 18, 6));
        statementL.setHorizontalAlignment(SwingConstants.CENTER);
        statementL.setFont(new Font("Arial", Font.BOLD, 24));

        frame.add(promptL, new Rectangle(2, 8, 16, 1));
        
        frame.add(angleAL, new Rectangle(1, 10, 4, 1));
        frame.add(angleATF, new Rectangle(4, 10, 6, 1));
        
        frame.add(sideBL, new Rectangle(1, 12, 4, 1));
        frame.add(sideBTF, new Rectangle(4, 12, 6, 1));
        
        frame.add(angleCL, new Rectangle(1, 14, 4, 1));
        frame.add(angleCTF, new Rectangle(4, 14, 6, 1));

        frame.add(MenuButton, new Rectangle(15, 1, 4, 2));
        frame.add(calculateButton, new Rectangle(2, 16, 7, 2));
        frame.add(lessonButton, new Rectangle(1, 1, 4, 2));

        frame.add(figureL, new Rectangle(2, 20, 8, 18));
        frame.add(resultTextArea, new Rectangle(11, 10, 8, 28));

        // Add action listener to the buttons
        calculateButton.addActionListener(this);
        MenuButton.addActionListener(this);
        lessonButton.addActionListener(this);    

        // Set up the frame
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        resultTextArea.setEditable(false);
    }
        
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == calculateButton){
            calculateAndDisplay();
        } else if(e.getSource() == MenuButton){
            frame.dispose();
        } else if(e.getSource() == lessonButton){
            lesson q = new lesson();
            q.setFrame();
            frame.dispose();
        } 
    }

    private void calculateAndDisplay() {
        try {
            double angleA = Double.parseDouble(angleATF.getText());
            double sideB = Double.parseDouble(sideBTF.getText());
            double angleC = Double.parseDouble(angleCTF.getText());

            // Calculate the third angle
            double angleB = 180 - (angleA + angleC);

            // Calculate the sides using the Law of Sines
            double sideA = sideB * Math.sin(Math.toRadians(angleA)) / Math.sin(Math.toRadians(angleB));
            double sideC = sideB * Math.sin(Math.toRadians(angleC)) / Math.sin(Math.toRadians(angleB));

            DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
            df.applyPattern("#,##0.00");

            // Format the results
            String sideAFormatted = df.format(sideA);
            String sideCFormatted = df.format(sideC);
            String angleBFormatted = df.format(angleB);

            // Display the complete solution
            resultTextArea.setText("Solution steps:\n");

            resultTextArea.append("1. Given:\n");
            resultTextArea.append("   - Angle A (alpha) = " + angleA + " degrees\n");
            resultTextArea.append("   - Side B = " + sideB + "\n");
            resultTextArea.append("   - Angle C (gamma) = " + angleC + " degrees\n\n");

            resultTextArea.append("2. Calculate the third angle (Angle B (beta):\n");
            resultTextArea.append("   - Angle B (beta) = 180 - (Angle A (alpha) + Angle C (gamma))\n");
            resultTextArea.append("   - Angle B (beta) = 180 - (" + angleA + " + " + angleC + ")\n");
            resultTextArea.append("   - Angle B (beta) = " + angleBFormatted + " degrees\n\n");

            resultTextArea.append("3. Use the Law of Sines to calculate the unknown sides:\n");

            resultTextArea.append("   a. Calculate Side A (alpha):\n");
            resultTextArea.append("      - Using the formula: a / sin(A) = b / sin(B)\n");
            resultTextArea.append("      - Rearrange to find Side A: a = b * sin(A) / sin(B)\n");
            resultTextArea.append("      - Substitute the values: a = " + sideB + " * sin(" + angleA + ") / sin(" + angleBFormatted + ")\n");
            resultTextArea.append("      - Compute the value: a = " + sideAFormatted + "\n\n");

            resultTextArea.append("   b. Calculate Side C:\n");
            resultTextArea.append("      - Using the formula: c / sin(C) = b / sin(B)\n");
            resultTextArea.append("      - Rearrange to find Side C: c = b * sin(C) / sin(B)\n");
            resultTextArea.append("      - Substitute the values: c = " + sideB + " * sin(" + angleC + ") / sin(" + angleBFormatted + ")\n");
            resultTextArea.append("      - Compute the value: c = " + sideCFormatted + "\n\n");

            resultTextArea.append("4. Final computed values:\n");
            resultTextArea.append("   - Side A: " + sideAFormatted + "\n");
            resultTextArea.append("   - Side C: " + sideCFormatted + "\n");
            resultTextArea.append("   - Angle B (beta): " + angleBFormatted + " degrees\n");

        } catch (NumberFormatException ex) {
            resultTextArea.setText("Invalid input. Please provide valid numbers for the angles and side.");
        }
    }

    public static void main(String[] args) {
        automations q = new automations();
        q.setFrame();
    }
}

package com.solvd.automation.lab.fall.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuickMessageGui {

    private JFrame frame;

    public void go(String response) {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JLabel responseLabel = new JLabel(response);
        responseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new OkButtonListener());
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(responseLabel,BorderLayout.CENTER);
        panel.add(okButton, BorderLayout.SOUTH);

        frame.add(panel,BorderLayout.CENTER);
        frame.setSize(350, 100);
        frame.setVisible(true);
    }

    public class OkButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
        }
    }
}

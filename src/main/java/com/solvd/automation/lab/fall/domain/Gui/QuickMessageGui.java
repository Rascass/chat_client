package com.solvd.automation.lab.fall.domain.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuickMessageGui {

    private JFrame frame;

    public void go(String response) {
        frame = new JFrame();
        JPanel panel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel,BoxLayout.Y_AXIS);

        JLabel responseLabel = new JLabel(response);

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new OkButtonListener());

        panel.add(responseLabel,BorderLayout.CENTER);
        panel.add(okButton, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setSize(250, 100);
        frame.setVisible(true);
    }

    public class OkButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
        }
    }
}

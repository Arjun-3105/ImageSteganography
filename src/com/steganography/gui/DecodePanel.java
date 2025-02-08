package com.steganography.gui;

import com.steganography.core.SteganographyUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class DecodePanel extends JFrame {
    private JLabel imageLabel;
    private File selectedFile;

    public DecodePanel() {
        setTitle("Decode Message from Image");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton chooseImageButton = new JButton("Choose Image");
        imageLabel = new JLabel("No Image Selected", SwingConstants.CENTER);
        JButton decodeButton = new JButton("Decode");

        chooseImageButton.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                imageLabel.setText(selectedFile.getName());
            }
        });

        decodeButton.addActionListener((ActionEvent e) -> {
            if (selectedFile != null) {
                String message = SteganographyUtil.decodeMessage(selectedFile);
                JOptionPane.showMessageDialog(this, message.isEmpty() ? "No message found!" : "Decoded Message: " + message);
            } else {
                JOptionPane.showMessageDialog(this, "Please select an image.");
            }
        });

        panel.add(chooseImageButton);
        panel.add(imageLabel);
        panel.add(decodeButton);

        add(panel);
        setVisible(true);
    }
}

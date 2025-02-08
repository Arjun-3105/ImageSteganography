package com.steganography.gui;

import com.steganography.core.SteganographyUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class EncodePanel extends JFrame {
    private JTextField messageField;
    private JLabel imageLabel;
    private File selectedFile;

    public EncodePanel() {
        setTitle("Encode Message into Image");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton chooseImageButton = new JButton("Choose Image");
        imageLabel = new JLabel("No Image Selected", SwingConstants.CENTER);
        messageField = new JTextField();
        JButton encodeButton = new JButton("Encode");

        chooseImageButton.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                imageLabel.setText(selectedFile.getName());
            }
        });

        encodeButton.addActionListener((ActionEvent e) -> {
            if (selectedFile != null && !messageField.getText().isEmpty()) {
                boolean success = SteganographyUtil.encodeMessage(selectedFile, messageField.getText());
                JOptionPane.showMessageDialog(this, success ? "Message Encoded Successfully!" : "Encoding Failed");
            } else {
                JOptionPane.showMessageDialog(this, "Please select an image and enter a message.");
            }
        });

        panel.add(chooseImageButton);
        panel.add(imageLabel);
        panel.add(messageField);
        panel.add(encodeButton);

        add(panel);
        setVisible(true);
    }
}

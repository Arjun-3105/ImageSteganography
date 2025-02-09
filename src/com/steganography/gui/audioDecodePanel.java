package com.steganography.gui;

import com.steganography.core.AudioSteganographyUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class audioDecodePanel extends JFrame {
    
    private JLabel audioLabel;
    private File selectedFile;

    public audioDecodePanel(){
        setTitle("Decode Message from Audio");
        setSize(500,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,1,10,10));

        JButton chooseaudiButton = new JButton("Choose Audio");
        audioLabel = new JLabel("No Audio Selected",SwingConstants.CENTER);
        JButton decodeButton = new JButton("Decode");

        chooseaudiButton.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();

            if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                selectedFile = fileChooser.getSelectedFile();
                audioLabel.setText(selectedFile.getName());
            }
        });

        decodeButton.addActionListener((ActionEvent e) -> {
            if(selectedFile != null){
                String message = AudioSteganographyUtil.decodeMessage(selectedFile);

                JOptionPane.showMessageDialog(this,message.isEmpty() ? "No message found " : "Decoded Message: " + message);
            }else{
                JOptionPane.showMessageDialog(this,"Please select an audio.");
            }
        });

        panel.add(chooseaudiButton);
        panel.add(audioLabel);
        panel.add(decodeButton);

        add(panel);
        setVisible(true);

    }
}

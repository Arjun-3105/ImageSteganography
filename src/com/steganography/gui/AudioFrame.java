package com.steganography.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Encoder;

public class AudioFrame extends JFrame{
    public AudioFrame(){
        setTitle("Audio Steganography");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(2,1,10,10));

        JButton encodeButton = new JButton("Encode Message: ");
        JButton decodeButton = new JButton("Decode Message: ");

        encodeButton.addActionListener(e -> new audioEncodePanel());
        decodeButton.addActionListener(e -> new audioDecodePanel());

        panel.add(encodeButton);
        panel.add(decodeButton);

        add(panel);
        setVisible(true);

    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run(){
                new AudioFrame();
            }
        });
    }
}

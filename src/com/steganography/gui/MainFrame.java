package com.steganography.gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Encoder;

public class MainFrame extends JFrame{
    public MainFrame(){
        setTitle("Image Steganograph");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center window in center

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1,10,10)); // row,column,row-gap,column-gap

        JButton encodeButton = new JButton("Encode Message: ");
        JButton decodeButton = new JButton("Decode Message: ");


        encodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                new EncodePanel();
            }
        });

        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                new DecodePanel();
            }
        });

        panel.add(encodeButton);
        panel.add(decodeButton);

        add(panel);

        setVisible(true);
    }


    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();  // Create a new MainFrame instance using Event Dispatch Thread
            }
        });
        
    }
}
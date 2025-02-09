package com.steganography.core;

import javax.sound.sampled.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AudioSteganographyUtil {

    // Encode the message into the audio file
    public static boolean encodeMessage(File audioFile, String message) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            byte[] audioBytes = audioStream.readAllBytes();

            byte[] messageBytes = message.getBytes();
            int messageLength = messageBytes.length;

            // Check if the message can fit in the audio file
            if (messageLength * 8 > audioBytes.length) {
                return false; // Message is too large for this audio file.
            }

            int byteIndex = 0;
            for (byte b : messageBytes) {
                for (int i = 0; i < 8; i++) {
                    // Modify the least significant bit (LSB) of the audio byte
                    int bit = (b >> (7 - i)) & 1;
                    audioBytes[byteIndex] = (byte) ((audioBytes[byteIndex] & 0xFE) | bit);
                    byteIndex++;
                }
            }

            // Add a terminator byte (0xFF) to mark the end of the message
            for (int i = 0; i < 8; i++) {
                int bit = (0xFF >> (7 - i)) & 1;
                audioBytes[byteIndex] = (byte) ((audioBytes[byteIndex] & 0xFE) | bit);
                byteIndex++;
            }

            // Save the modified audio to a new file
            String newFileName = generateUniqueFileName(audioFile);
            File output = new File(newFileName);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(audioBytes);
            AudioInputStream newAudioStream = new AudioInputStream(byteArrayInputStream, format, audioBytes.length);
            AudioSystem.write(newAudioStream, AudioFileFormat.Type.WAVE, output);

            System.out.println("Encoded audio saved as: " + newFileName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Decode the hidden message from the audio file
    public static String decodeMessage(File audioFile) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            byte[] audioBytes = audioStream.readAllBytes();
            StringBuilder message = new StringBuilder();

            int byteIndex = 0;
            while (byteIndex < audioBytes.length) {
                byte b = 0;
                for (int i = 0; i < 8; i++) {
                    // Extract the least significant bit (LSB) of the audio byte
                    int bit = audioBytes[byteIndex] & 1;
                    b = (byte) ((b << 1) | bit);
                    byteIndex++;
                }

                // Check for the terminator byte (0xFF) which marks the end of the message
                if (b == 0xFF) {
                    return message.toString(); // Message has ended
                }

                message.append((char) b);
            }

            return message.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // Generate a unique file name based on the current timestamp to avoid overwriting
    private static String generateUniqueFileName(File audioFile) {
        String fileName = audioFile.getName();
        String baseName = fileName.substring(0, fileName.lastIndexOf('.')); // Get the base name without extension
        String extension = fileName.substring(fileName.lastIndexOf('.')); // Get the file extension

        // Get current timestamp to append to the file name
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return baseName + "_encoded_" + timestamp + extension;
    }
}

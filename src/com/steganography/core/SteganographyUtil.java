package com.steganography.core;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SteganographyUtil {

    // Encode the message into the image
    public static boolean encodeMessage(File imageFile, String message) {
        try {
            BufferedImage image = ImageIO.read(imageFile);
            byte[] messageBytes = message.getBytes();
            int messageLength = messageBytes.length;
            
            // Check if the message can fit in the image
            if (messageLength > image.getWidth() * image.getHeight()) {
                return false; // Message is too large for this image.
            }

            int pixelIndex = 0;
            for (byte b : messageBytes) {
                int x = pixelIndex % image.getWidth();
                int y = pixelIndex / image.getWidth();
                int pixel = image.getRGB(x, y) & 0xFFFFFF00; // Clear the last byte
                int newPixel = pixel | (b & 0xFF); // Set the last byte with the message byte
                image.setRGB(x, y, newPixel);
                pixelIndex++;
            }

            // Add a terminator byte (0xFF) to mark the end of the message
            int x = pixelIndex % image.getWidth();
            int y = pixelIndex / image.getWidth();
            int pixel = image.getRGB(x, y) & 0xFFFFFF00; // Clear the last byte
            int newPixel = pixel | (0xFF); // Terminator byte
            image.setRGB(x, y, newPixel);

            // Generate a new file name to avoid overwriting the original image
            String newFileName = generateUniqueFileName(imageFile);
            File output = new File(newFileName);
            ImageIO.write(image, "png", output);
            System.out.println("Encoded image saved as: " + newFileName);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Decode the hidden message from the image
    public static String decodeMessage(File imageFile) {
        try {
            BufferedImage image = ImageIO.read(imageFile);
            StringBuilder message = new StringBuilder();

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int pixel = image.getRGB(x, y);
                    int lastByte = pixel & 0xFF; // Extract the last byte

                    // Check for the terminator byte (0xFF) which marks the end of the message
                    if (lastByte == 0xFF) {
                        return message.toString(); // Message has ended
                    }

                    message.append((char) lastByte);
                }
            }
            return message.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    // Generate a unique file name based on the current timestamp to avoid overwriting
    private static String generateUniqueFileName(File imageFile) {
        String fileName = imageFile.getName();
        String baseName = fileName.substring(0, fileName.lastIndexOf('.')); // Get the base name without extension
        String extension = fileName.substring(fileName.lastIndexOf('.')); // Get the file extension

        // Get current timestamp to append to the file name
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return baseName + "_encoded_" + timestamp + extension;
    }
}

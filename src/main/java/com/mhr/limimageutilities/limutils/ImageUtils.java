/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mhr.limimageutilities.limutils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

/**
 *
 * @author marcosherreroamores
 */
public class ImageUtils {
    
    public final byte[] IDENTIFICATION_BYTES;
    
    public ImageUtils() {
        this.IDENTIFICATION_BYTES = new byte[]{17, 5, 33, 22, 15, 31, 19, 8};
    }
    
    public RgbColor[][] interpretLIMImage(File fileReference) {
        try (InputStream fis = new FileInputStream(fileReference);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            byte[] identificationBytes = new byte[8];
            int readedBytes = ois.read(identificationBytes);
            if (readedBytes == 8 && Arrays.equals(IDENTIFICATION_BYTES, identificationBytes)) {
                System.out.println("The file is valid");
                return readImageContent(ois);
            } else {
                System.out.println("The file is NOT correct");
            }
        } catch (StreamCorruptedException ex) {
            System.out.println("Error message: " + ex.getMessage());
            ex.printStackTrace(System.out);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private RgbColor[][] readImageContent(ObjectInputStream ois) {
        try {
            int width = ois.readInt();
            int height = ois.readInt();
            RgbColor[][] pixelMatrix = new RgbColor[width][height];
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    pixelMatrix[i][j] = new RgbColor();
                    pixelMatrix[i][j].setRed(ois.readInt());
                    pixelMatrix[i][j].setBlue(ois.readInt());
                    pixelMatrix[i][j].setGreen(ois.readInt());
                }
            }
            return pixelMatrix;
        } catch (IOException ex) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void createRedFile(File fileReference, int width, int height) {
        try (OutputStream fis = new FileOutputStream(fileReference);
                ObjectOutputStream ois = new ObjectOutputStream(fis);) {
            ois.write(IDENTIFICATION_BYTES);
            ois.writeInt(width);
            ois.writeInt(height);
            RgbColor[][] pixelMatrix = RgbColor.createMatrix(width, height);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    ois.writeInt(pixelMatrix[i][j].getRed());
                    ois.writeInt(pixelMatrix[i][j].getGreen());
                    ois.writeInt(pixelMatrix[i][j].getBlue());
                }
            }
            System.out.println("Red file created succesfully!");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public RgbColor[][] getImagePixels(File fileReference) {
        RgbColor[][] imagePixels = null;
        try (InputStream fis = new FileInputStream(fileReference);
                ImageInputStream imageIS = ImageIO.createImageInputStream(fis);) {
            BufferedImage imageBuffer = ImageIO.read(imageIS);
            int height = imageBuffer.getHeight();
            int width = imageBuffer.getWidth();
            System.out.println(height + ", " + width);
            imagePixels = new RgbColor[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int intPixel = imageBuffer.getRGB(j, i);
                    Color rgbColor = new Color(intPixel);
                    System.out.println(rgbColor + " " + i + " " + j);
                    imagePixels[i][j] = new RgbColor(rgbColor.getRed(), rgbColor.getGreen(), rgbColor.getBlue());
                }
            }
            System.out.println("Hemos llegado hasta aqui");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return imagePixels;
    }
    
    public void createLIMFileFromPixels(RgbColor[][] pixels, int width, int height, File saveFileReference) {
        try (OutputStream fos = new FileOutputStream(saveFileReference);
                ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            RgbColor[][] wellColocatedPixels = reverseRGBColorMatrix(pixels, width, height);
            oos.write(IDENTIFICATION_BYTES);
            oos.writeInt(height);
            oos.writeInt(width);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    oos.writeInt(wellColocatedPixels[i][j].getRed());
                    oos.writeInt(wellColocatedPixels[i][j].getGreen());
                    oos.writeInt(wellColocatedPixels[i][j].getBlue());
                }
            }
            System.out.println("File converted succesfully");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public RgbColor[][] reverseRGBColorMatrix(RgbColor[][] pixelMatrix, int width, int height) {
        RgbColor[][] newPixels = new RgbColor[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                newPixels[i][j] = pixelMatrix[j][i];
            }
        }
        return newPixels;
    }
    
    public void createJPGImageFromPixels(File fileReference, RgbColor[][] pixels, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgbColor = (pixels[i][j].getRed() << 16) | (pixels[i][j].getGreen() << 8) | pixels[i][j].getBlue();
                bi.setRGB(i, j, rgbColor);
            }
        }
        try {
            ImageIO.write(bi, "JPG", fileReference);
        } catch (IOException ex) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

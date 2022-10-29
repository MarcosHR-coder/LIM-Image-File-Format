/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mhr.limimageutilities.windowutils;

import com.mhr.limimageutilities.Window;
import com.mhr.limimageutilities.limutils.ImageUtils;
import com.mhr.limimageutilities.limutils.RgbColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.*;

/**
 *
 * @author marcosherreroamores
 */
public class WindowMenuBar extends JMenuBar implements ActionListener {
    
    Window windowReference;
    ImageUtils imageUtils;
    
    JMenu fileMenu;
    JMenu interpretMenu;
    JMenu createMenu;
    JMenu convertMenu;
    JMenu helpMenu;
    
    JMenuItem closeItem;
    JMenuItem imagePanelItem;
    JMenuItem interpretItem;
    JMenuItem convertItem;
    JMenuItem convertReverseItem;
    JMenuItem createItem;
    JMenuItem aboutItem;
    JMenuItem helpItem;
    JMenuItem reverseImageItem;
    JMenuItem saveImage;
    
    public WindowMenuBar(Window windowReference) {
        
        this.windowReference = windowReference;
        this.imageUtils = new ImageUtils();
        
        this.fileMenu = new JMenu("File");
        this.interpretMenu = new JMenu("Interpret");
        this.convertMenu = new JMenu("Convert");
        this.createMenu = new JMenu("Create");
        this.helpMenu = new JMenu("Help");
        
        this.loadFileMenu();
        this.loadInterpretMenu();
        this.loadConvertMenu();
        this.loadCreateMenu();
        this.loadHelpMenu();
        
    }
    
    private void loadFileMenu() {
        closeItem = new JMenuItem("Close");
        closeItem.addActionListener(this);
        this.fileMenu.add(closeItem);
        imagePanelItem = new JMenuItem("Quit opened image");
        imagePanelItem.addActionListener(this);
        this.fileMenu.add(imagePanelItem);
        /*reverseImageItem = new JMenuItem("Reverse image");
        reverseImageItem.addActionListener(this);
        this.fileMenu.add(reverseImageItem); */
        this.add(fileMenu);
    }

    private void loadInterpretMenu() {
        interpretItem = new JMenuItem("Interpret file");
        interpretItem.addActionListener(this);
        this.interpretMenu.add(interpretItem);
        this.add(interpretMenu);
    }

    private void loadConvertMenu() {
        convertItem = new JMenuItem("Convert images to LIM");
        convertReverseItem = new JMenuItem("Convert LIM to JPG");
        convertItem.addActionListener(this);
        convertReverseItem.addActionListener(this);
        this.convertMenu.add(convertItem);
        this.convertMenu.add(convertReverseItem);
        this.add(convertMenu);
    }

    private void loadCreateMenu() {
        createItem = new JMenuItem("Create red image");
        createItem.addActionListener(this);
        this.createMenu.add(createItem);
        this.add(createMenu);
    }
    
    private void loadHelpMenu() {
        helpItem = new JMenuItem("Open help screen");
        aboutItem = new JMenuItem("About application");
        helpItem.addActionListener(this);
        aboutItem.addActionListener(this);
        this.helpMenu.add(helpItem);
        this.helpMenu.add(aboutItem);
        this.add(helpMenu);
    }
    
    public File selectFileWithChooser() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            return null;
        }
    }
    
    public File saveFileWithChooser() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            return null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeItem) {
            windowReference.dispatchEvent(new WindowEvent(windowReference, WindowEvent.WINDOW_CLOSING));
        } else if (e.getSource() == interpretItem) {
            File chosenFile = this.selectFileWithChooser();
            if (chosenFile == null) return;
            RgbColor[][] pixelMatrix = imageUtils.interpretLIMImage(chosenFile);
            if (pixelMatrix == null) {
                System.out.println("There was a problem reading the LIM file");
                return;
            }
            windowReference.paintInterpretedImage(pixelMatrix, pixelMatrix.length, pixelMatrix[0].length);
        } else if (e.getSource() == createItem) {
            File chosenFile = this.saveFileWithChooser();
            if (chosenFile == null) return;
            System.out.println(chosenFile.getAbsolutePath());
            String width = JOptionPane.showInputDialog("Introduce the width:");
            String height = JOptionPane.showInputDialog("Introduce the height:");
            imageUtils.createRedFile(chosenFile, Integer.parseInt(width), Integer.parseInt(height));
        } else if (e.getSource() == imagePanelItem) {
            this.windowReference.removeImagePanelInstances();
        } else if (e.getSource() == convertItem) {
            File chosenFile = this.selectFileWithChooser();
            if (chosenFile == null) return;
            RgbColor[][] imagePixels = imageUtils.getImagePixels(chosenFile);
            if (imagePixels == null) {
                System.out.println("There was a problem with the file, is not a valid image");
                return;
            }
            File secondChosenFile = this.saveFileWithChooser();
            if (secondChosenFile == null) return;
            imageUtils.createLIMFileFromPixels(imagePixels, imagePixels.length, imagePixels[0].length, secondChosenFile);
        } else if (e.getSource() == aboutItem) {
            new AboutWindow();
        /* } else if (e.getSource() == reverseImageItem) {
            this.windowReference.reverseCurrentImage(); */
        } else if (e.getSource() == convertReverseItem) {
            File fileToRead = this.selectFileWithChooser();
            if (fileToRead == null) return;
            RgbColor[][] pixels = imageUtils.interpretLIMImage(fileToRead);
            File fileToCreate = this.saveFileWithChooser();
            if (fileToCreate == null) return;
            imageUtils.createJPGImageFromPixels(fileToCreate, pixels, pixels.length, pixels[0].length);
        }
    }
    
}

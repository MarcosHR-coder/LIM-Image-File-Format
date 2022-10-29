/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mhr.limimageutilities;

import com.mhr.limimageutilities.limutils.RgbColor;
import com.mhr.limimageutilities.windowutils.WindowMenuBar;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;

/**
 *
 * @author marcosherreroamores
 */
public class Window extends JFrame {
    
    JMenuBar menuBar;
    InterpretationPanel imagePanel;
    
    public Window() {
        this.imagePanel = null;
        this.setSize(800, 720);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("LIM Image File Format Utilities");
        this.setLayout(new BorderLayout());
        this.loadComponents();
        this.setVisible(true);
    }
    
    private void loadComponents() {
        this.menuBar = new WindowMenuBar(this);
        this.add(BorderLayout.NORTH, menuBar);
    }
    
    public void paintInterpretedImage(RgbColor[][] imagePixels, int width, int height) {
        if (imagePanel != null) {
            this.removeImagePanelInstances();
        }
        this.imagePanel = new InterpretationPanel(imagePixels, width, height);
        this.imagePanel.getUI();
        this.add(BorderLayout.CENTER, imagePanel);
        this.pack();
        this.setSize(width + 20, height + 20);
    }
    
    public void removeImagePanelInstances() {
        if (imagePanel != null) {
            this.remove(imagePanel);
            this.imagePanel = null;
            this.setSize(800, 720);
        }
    }
    
    public void reverseCurrentImage(/*RgbColor[][] pixels, int height, int width*/) {
        // Implement the functionality
    }
}

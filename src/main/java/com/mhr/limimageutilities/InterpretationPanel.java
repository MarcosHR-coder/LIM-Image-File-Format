/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mhr.limimageutilities;

import com.mhr.limimageutilities.limutils.RgbColor;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;

/**
 *
 * @author marcosherreroamores
 */
public class InterpretationPanel extends JPanel {
    
    private int width;
    private int height;
    
    private RgbColor[][] pixels;
    
    public InterpretationPanel(RgbColor[][] pixels, int width, int height) {
        super();
        this.pixels = pixels;
        this.width = width;
        this.height = height;
        this.setSize(width, height);
    }
    
    @Override
    public void paint(Graphics g) {
        System.out.println("Bien");
        super.paint(g);
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                g.setColor(new Color(pixels[i][j].getRed(), pixels[i][j].getGreen(), pixels[i][j].getBlue()));
                g.drawLine(i, j, i, j);
            }
        }
    }
    
}

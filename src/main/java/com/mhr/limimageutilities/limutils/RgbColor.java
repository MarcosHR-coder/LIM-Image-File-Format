/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mhr.limimageutilities.limutils;

/**
 *
 * @author marcosherreroamores
 */
public class RgbColor {
    
    private int red;
    private int blue;
    private int green;

    public RgbColor() {
        
    }
    
    public RgbColor(int red, int blue, int green) {
        this.red = red;
        this.blue = blue;
        this.green = green;
    }
    
    public RgbColor(String red, String blue, String green) {
        this.red = Integer.parseInt(red);
        this.blue = Integer.parseInt(blue);
        this.green = Integer.parseInt(green);
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }
    
    public static RgbColor[][] createMatrix(int width, int height) {
        RgbColor[][] createdMatrix = new RgbColor[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                createdMatrix[i][j] = new RgbColor(255, 0, 0);
            }
        }
        return createdMatrix;
    }

    @Override
    public String toString() {
        return "RgbColor{" + "red=" + red + ", blue=" + blue + ", green=" + green + '}';
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mhr.limimageutilities.windowutils;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author marcosherreroamores
 */
public class AboutWindow extends JFrame implements ActionListener {
    
    public JPanel mainPanel;
    
    public JLabel title;
    public JLabel text;
    public JLabel footer;
    public JButton contact;
    
    public AboutWindow() {
        this.setSize(650, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.loadComponents();
        this.setTitle("LIM Image Format About");
        this.setVisible(true);
    }
    
    private void loadComponents() {
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        this.title = new JLabel("Large Image Maintenance (LIM) - About");
        title.setFont(new Font("Serif", Font.BOLD, 20));
        mainPanel.add(BorderLayout.NORTH, title);
        this.text = new JLabel("LIM Image Format was created in Spain by Marcos Herrero Ruiz");
        text.setFont(new Font("Serif", Font.PLAIN, 14));
        mainPanel.add(BorderLayout.CENTER, text);
        this.footer = new JLabel("LIM Image Format Utils. Version 1.0 for " + System.getProperty("os.name") + " (Nov, 2022)");
        footer.setFont(new Font("Serif", Font.BOLD, 16));
        mainPanel.add(BorderLayout.SOUTH, footer);
        this.contact = new JButton("Contact me");
        contact.addActionListener(this);
        mainPanel.add(BorderLayout.LINE_END, contact);
        this.add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == contact) {
            JOptionPane.showMessageDialog(new JFrame(), "marcoshr.coder@gmail.com");
        }
    }
    
}

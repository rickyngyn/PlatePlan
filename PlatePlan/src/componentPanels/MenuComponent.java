package componentPanels;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import dto.MenuItem;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;

public class MenuComponent extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Create the panel.
     */
    public MenuComponent(MenuItem menuItem) {
        Dimension dimension = new Dimension(400, 100);
        this.setPreferredSize(dimension);
        this.setMinimumSize(dimension);
        this.setMaximumSize(dimension);
        setBackground(new Color(255, 255, 255));
        setLayout(null);
        
    
        
        JLabel lblTitle = new JLabel(menuItem.getName());
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        lblTitle.setBounds(15, 11, 263, 27);
        lblTitle.setBackground(new Color(255, 245, 238)); // Match the background with the JPanel
        add(lblTitle);
        
        
        JLabel lblPrice = new JLabel("$" + menuItem.getPrice());
        lblPrice.setFont(new Font("MS Reference Sans Serif", Font.ITALIC, 14));
        lblPrice.setBounds(318, 17, 72, 14);
        add(lblPrice);
        
        // JTextArea for description
        JTextArea txtDescription = new JTextArea(menuItem.getDescription());
        txtDescription.setFont(new Font("Arial", Font.ITALIC, 11));
        txtDescription.setBounds(15, 49, 375, 40);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setLineWrap(true);
        txtDescription.setEditable(false);
        txtDescription.setFocusable(false);
        txtDescription.setOpaque(false);
        txtDescription.setBackground(new Color(255, 245, 238)); // Match the background with the JPanel
        txtDescription.setBorder(null); // No border to mimic a JLabel
        
        add(txtDescription);
        
        // JTextArea for description
        JTextArea txtDescription2 = new JTextArea("");
        txtDescription2.setFont(new Font("Arial", Font.ITALIC, 11));
        txtDescription2.setBounds(10, 11, 380, 78);
        txtDescription2.setWrapStyleWord(true);
        txtDescription2.setLineWrap(true);
        txtDescription2.setEditable(false);
        txtDescription2.setFocusable(false);
        txtDescription2.setOpaque(true);
        txtDescription2.setBackground(new Color(255, 245, 238)); // Match the background with the JPanel
        txtDescription2.setBorder(null); // No border to mimic a JLabel
        add(txtDescription2);
        

    }
}

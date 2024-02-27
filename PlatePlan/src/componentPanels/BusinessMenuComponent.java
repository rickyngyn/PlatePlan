package componentPanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dto.MenuItem;

public class BusinessMenuComponent extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField txtTitle;
    private JTextField txtPriceField;

    /**
     * Create the panel.
     */
    public BusinessMenuComponent(MenuItem menuItem) {
        Dimension dimension = new Dimension(400, 100);
        this.setPreferredSize(new Dimension(600, 100));
        this.setMinimumSize(new Dimension(600, 100));
        this.setMaximumSize(new Dimension(600, 100));
        setBackground(new Color(255, 245, 238));
        setLayout(null);
        
        // JTextArea for description
        JTextArea txtDescription = new JTextArea(menuItem.getDescription());
        txtDescription.setEditable(true); // This line makes the JTextArea editable
        txtDescription.setFont(new Font("Arial", Font.ITALIC, 11));
        txtDescription.setBounds(15, 49, 474, 40);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setLineWrap(true);
        txtDescription.setOpaque(true);
        txtDescription.setBackground(new Color(255, 245, 238)); // Match the background with the JPanel
        txtDescription.setBorder(null); // No border to mimic a JLabel
        
        add(txtDescription);
        

        
        txtTitle = new JTextField();
        txtTitle.setFont(new Font("Arial", Font.BOLD, 16));
        txtTitle.setBorder(null);
        txtTitle.setCaretColor(new Color(0, 0, 0));
        txtTitle.setOpaque(false);
        txtTitle.setBackground(new Color(255, 255, 255));
        txtTitle.setText(menuItem.getName());
        txtTitle.setBounds(15, 16, 474, 31);
        add(txtTitle);
        txtTitle.setColumns(10);
        
        txtPriceField = new JTextField();
        txtPriceField.setSelectedTextColor(Color.WHITE);
        txtPriceField.setFont(new Font("Arial", Font.ITALIC, 16));
        txtPriceField.setText(""+menuItem.getPrice());
        txtPriceField.setBounds(516, 16, 74, 31);
        add(txtPriceField);
        txtPriceField.setColumns(10);
        
        JLabel lblNewLabel = new JLabel("$");
        lblNewLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        lblNewLabel.setBounds(499, 24, 14, 14);
        add(lblNewLabel);
        


    }
}

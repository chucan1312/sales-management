package ui.menu;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.concurrent.Flow;

import model.*;

public class AddProductMenu extends JFrame implements ActionListener {
    public static final int WIDTH = 1000;
	public static final int HEIGHT = 700;

    private WorkSpace workSpace;
    private JTextField nameField; 
    private JTextField idField;
    private JTextField unitPriceField;
    private JTextField sellingPriceField;
    private JTextField quantityField;

    public AddProductMenu(WorkSpace workSpace) {
        super("Add new product");
        this.workSpace = workSpace;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        nameField = new JTextField(50);
        idField = new JTextField(50);
        unitPriceField = new JTextField(50);
        sellingPriceField = new JTextField(50);
        quantityField = new JTextField(50);

        addTextField(nameField, "Product's Name:", layout, gbc, 1, 1, 1, 1);
        addTextField(idField, "Product's ID:", layout, gbc, 1, 2, 1, 1);
        addTextField(unitPriceField, "Product's unit price:", layout, gbc, 1, 3, 1, 1);
        addTextField(sellingPriceField, "Product's selling price:", layout, gbc, 1, 4, 1, 1);
        addTextField(quantityField, "Product's quantity:", layout, gbc, 1, 5, 1, 1);
        
        // Adds a spacer
        gbc.gridx = 1;
        gbc.gridy += 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        for (int i = 0; i <= 3; i++) {
            add(new JLabel("  "), gbc);
            gbc.gridy += 1;
        }

        // Add all buttons to a single panel with the flow layout
        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton("Submit", buttonPanel);
        addButton("Return to Main Menu", buttonPanel);
        gbc.gridy += 1;
        gbc.insets = new Insets(0, -120, 0, 0);
        add(buttonPanel, gbc);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Submit":
                addProduct();
                break;
            case "Return to Main Menu":
                dispose();
                new MainMenu(workSpace);
                break;
        }
    }
    
    // MODIFIES: this
    // EFFECTS: add a product to workspace and exit the add product menu only if all fields are filled
    private void addProduct() {
        String name = nameField.getText().trim();
        String id = idField.getText().trim();
        String unitPrice = unitPriceField.getText().trim();
        String sellingPrice = sellingPriceField.getText().trim();
        String quantity = quantityField.getText().trim();

        if (!name.isEmpty() && !id.isEmpty() && !unitPrice.isEmpty() && !sellingPrice.isEmpty() && !quantity.isEmpty()) {
            Product product = new Product(name, id, Double.valueOf(unitPrice));
            product.setSellingPrice(Double.valueOf(sellingPrice));
            product.restock(Integer.valueOf(quantity));
            workSpace.getInventory().addProduct(product);
            JOptionPane.showMessageDialog(this, "Product added successfully!");
            dispose();
            new MainMenu(workSpace);
        } else {
            JOptionPane.showMessageDialog(this, "Please enter all required fields.");
        }
    }

    // EFFECTS: add a new label and a text field to the content pane
    private void addTextField(JTextField textField, String label, GridBagLayout layout, GridBagConstraints gbc, int gridx, int gridy, int gridwidth, int gridheight) {
        addLabel(label, layout, gbc, gridx, gridy, gridwidth, gridheight);
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;

        layout.setConstraints(textField, gbc);
        add(textField);
        textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
    }
    
    // EFFECTS: add a label to the content pane at gridx - 1, gridy with gridwith and gridheight
    private void addLabel(String string, GridBagLayout layout, GridBagConstraints gbc, int gridx, int gridy, int gridwidth, int gridheight) {
       Label label = new Label(string);
       gbc.gridx = gridx - 1; 
       gbc.gridy = gridy;
       gbc.gridwidth = gridwidth;
       gbc.gridheight = gridheight;

       layout.setConstraints(label, gbc);
       label.setFont(new Font("Tahoma", Font.BOLD, 15));
       add(label);
    }
        
    // EFFECTS: instantiates a new button and add it to the content pane
    private void addButton(String command, JPanel buttonPanel) {
        JButton btn = new JButton(command);
        btn.setActionCommand(command);
        btn.addActionListener(this);
        btn.setFont(new Font("Tahoma", Font.BOLD, 15));
        buttonPanel.add(btn);
    }
}

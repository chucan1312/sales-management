package ui.menu;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class FilteredInventoryMenu extends JFrame implements ActionListener {
    private List<Product> products;
    private WorkSpace workSpace;

    public FilteredInventoryMenu(List<Product> products, WorkSpace workSpace) {
        super("Search result");
        this.products = products;
        this.workSpace = workSpace;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(MainMenu.WIDTH, MainMenu.HEIGHT));
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        
        addTable(products, InventoryMenu.columnNames, gbc);
        addButton("Return to Inventory", gbc);
        addSpacer(gbc, 3);
        setBackground(MainMenu.backgroundColor);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Return to Inventory":
                dispose();
                new InventoryMenu(workSpace);
                break;
        }
    }

    // EFFECTS: add n empty labels as a spacer
    private void addSpacer(GridBagConstraints gbc, int n) {
        gbc.gridx = 0;
        gbc.gridy += 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        for (int i = 0; i <= n; i++) {
            add(new JLabel("  "), gbc);
            gbc.gridy += 1;
        }
    }

    // EFFECTS: instantiates a new button and add it to the content pane
    private void addButton(String command, GridBagConstraints gbc) {
        JButton btn = new JButton(command);
        btn.setActionCommand(command);
        btn.addActionListener(this);
        btn.setFont(new Font("Tahoma", Font.BOLD, 15));
        btn.setBackground(MainMenu.buttonColor);
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridx = 0;
        gbc.gridy += 1;
        gbc.fill = GridBagConstraints.CENTER;
        add(btn, gbc);
    }

    // EFFECTS: add the table to content pane
    private void addTable(List<Product> products, Object[] columnNames, GridBagConstraints gbc) {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        parseInventory(products, model);
        
        // customize header
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color (146,168,209));
        header.setFont(new Font("Tahoma", Font.BOLD, 15));;
        table.setBackground(MainMenu.backgroundColor);
        table.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy += 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(scrollPane, gbc);
    }
    
    // EFFECTS: parses the data from Inventory into the correct form for JTable
    private void parseInventory(List<Product> products, DefaultTableModel model) {
        for(Product p : products) {
            Object[] row = {p.getName(), p.getId(), p.getUnitPrice(), p.getSellingPrice(), p.getQuantity()};
            model.addRow(row);
        }
    }
}

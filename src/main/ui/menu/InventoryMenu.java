package ui.menu;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import java.awt.event.*;
import java.util.List;

import model.*;

public class InventoryMenu extends JFrame implements ActionListener {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private WorkSpace workSpace;

    // EFFECTS: creates a new Inventory Menu with workspace
    public InventoryMenu(WorkSpace workSpace) {
        super("Inventory");
        this.workSpace = workSpace;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        List<Product> products = workSpace.getInventory().getProducts();

        String[] columnNames = {"Product's name", "Product's ID", "Product's unit price", "Product's selling price", "Product's quantity"};
        addSearchBar(gbc);
        addTable(products, columnNames, gbc);
        addButton("Return to Main Menu", gbc);
        addSpacer(gbc, 3);
        setBackground(new Color(171,177,207));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Return to Main Menu":
                dispose();
                new MainMenu(workSpace);
                break;
        }
    }

    // EFFECTS: instantiates a new button and add it to the content pane
    private void addButton(String command, GridBagConstraints gbc) {
        JButton btn = new JButton(command);
        btn.setActionCommand(command);
        btn.addActionListener(this);
        btn.setFont(new Font("Tahoma", Font.BOLD, 15));
        btn.setBackground(new Color(222,194,203));
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridx = 0;
        gbc.gridy += 1;
        gbc.fill = GridBagConstraints.CENTER;
        add(btn, gbc);
    }

    // EFFECTS: instantiates a new button and add it to a panel
    private void addButton(String command, Panel buttonPanel) {
        // stub
    }

    // EFFECTS: add the table to content pane
    private void addTable(List<Product> products, Object[] columnNames, GridBagConstraints gbc) {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        parseInventory(workSpace.getInventory(), model);
        
        // customize header
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color (146,168,209));
        header.setFont(new Font("Tahoma", Font.BOLD, 15));;
        table.setBackground(new Color(171,177,207));
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
    private void parseInventory(Inventory i, DefaultTableModel model) {
        for(Product p : i.getProducts()) {
            Object[] row = {p.getName(), p.getId(), p.getUnitPrice(), p.getSellingPrice(), p.getQuantity()};
            model.addRow(row);
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

    // EFFECTS: add a text field that acts as a search bar at the top of the pane
    private void addSearchBar(GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy +=1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        TextField searchBar = new TextField(100);
        searchBar.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(searchBar, gbc);
    }
}

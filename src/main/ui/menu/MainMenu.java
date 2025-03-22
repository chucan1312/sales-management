package ui.menu;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

import org.w3c.dom.events.MouseEvent;

import model.*;

public class MainMenu extends JFrame implements ActionListener {
    public static final int WIDTH = 1000;
	public static final int HEIGHT = 700;

    private WorkSpace workspace;
    
    public MainMenu() {
        super("Sales Management App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new FlowLayout());
        addButton("Add product");
        addButton("View all products in inventory");
        addButton("Save data");
        addButton("Load data");
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // EFFECTS: instantiates a new button and add it to the content pane
    private void addButton(String command) {
        JButton btn = new JButton(command);
        btn.setActionCommand(command);
        btn.addActionListener(this);
        add(btn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Add Product":
                openAddProductMenu(workspace);
                break;
            case "View all products in inventory":
                openInventoryMenu(workspace);
                break;
            case "Save data":
                save();
                break;
            case "Load data":
                load();
                break;
        }
    }

    // EFFECTS: instantiates a new AddProductMenu() with current workspace
    private void openAddProductMenu(WorkSpace workspace) {
        // stub
    }

    // EFFECTS: instantiates a new InventoryMenu() with current worksapce
    private void openInventoryMenu(WorkSpace workspace) {
        // stub
    }

    // EFFECTS: saves the workspace to file
    private void save() {

    }

    // EFFECTS: loads the workspace from file
    private void load() {
        
    }
}

package ui.menu;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.*;
import persistence.*;

public class MainMenu extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/workspace";
    public static final int WIDTH = 1000;
	public static final int HEIGHT = 700;

    private WorkSpace workSpace;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    
    public MainMenu(WorkSpace workSpace) {
        super("Sales Management App");
        this.workSpace = workSpace;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new FlowLayout());
        addButton("Add product");
        // addButton("View all products in inventory");
        addButton("Save data");
        addButton("Load data");
        pack();
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
            case "Add product":
                openAddProductMenu(workSpace);
                break;
            // case "View all products in inventory":
            //     openInventoryMenu(workSpace);
            //     break;
            case "Save data":
                save();
                break;
            case "Load data":
                load();
                break;
        }
    }

    // EFFECTS: passes workspace into a new AddProductMenu() instantiation 
    private void openAddProductMenu(WorkSpace workSpace) {
        dispose();
        new AddProductMenu(workSpace, WIDTH, HEIGHT);
    }

    // // EFFECTS: passes workspace into a new InventoryMenu() instantiation 
    // private void openInventoryMenu(WorkSpace workspace) {
    //     // stub
    // }

    // EFFECTS: saves the workspace to file
    private void save() {
        try {
            jsonWriter.open();
            jsonWriter.write(workSpace);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Saved workspace to: " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: loads the workspace from file
    private void load() {
        try {
            workSpace = jsonReader.read();
            JOptionPane.showMessageDialog(this,"Loaded workspace from " + JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,"Unable to read from file: " + JSON_STORE);
        }
    }
}

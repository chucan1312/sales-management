package ui.menu;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.*;
import persistence.*;

public class MainMenu extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/workspace";
    public static final int WIDTH = 1000;
	public static final int HEIGHT = 700;
    public static final Color backgroundColor = new Color(171,177,207);
    public static final Color buttonColor = new Color(222,194,203);

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
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        addTitle("Welcome to the Sales Management App!", gbc, layout);
        JPanel panel = new JPanel(new FlowLayout());
        addButton("Add product", panel);
        addButton("View inventory", panel);
        addButton("Save data", panel);
        addButton("Load data", panel);

        gbc.gridx = 0;
        gbc.gridy += 1;
        gbc.gridwidth = 2;
        panel.setBackground(backgroundColor);
        add(panel, gbc);
        gbc.gridx = 0;
        gbc.gridy += 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, -100, -320, 0);
        addImage("./data/left.png", gbc, layout);
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, -320, -270);
        addImage("./data/right.png", gbc, layout);
        getContentPane().setBackground(new Color(171,177,207));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // EFFECTS: adds image to content pane
    private void addImage(String path, GridBagConstraints gbc, GridBagLayout layout) {
        ImageIcon imageIcon = new ImageIcon(path);
        JLabel image = new JLabel(imageIcon);
        add(image, gbc);
    }

    // EFFECTS: instantiates a new button and add it to the content pane
    private void addButton(String command, JPanel panel) {
        JButton btn = new JButton(command);
        btn.setActionCommand(command);
        btn.addActionListener(this);
        btn.setFont(new Font("Tahoma", Font.BOLD, 15));
        btn.setBackground(buttonColor);
        panel.add(btn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Add product":
                openAddProductMenu(workSpace);
                break;
            case "View inventory":
                openInventoryMenu(workSpace);
                break;
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
        new AddProductMenu(workSpace);
    }

    // EFFECTS: passes workspace into a new InventoryMenu() instantiation 
    private void openInventoryMenu(WorkSpace workspace) {
        dispose();
        new InventoryMenu(workSpace);
    }

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

    private void addTitle(String s, GridBagConstraints gbc, GridBagLayout layout) {
        Label title = new Label(s);
        gbc.gridx = 0;
        gbc.gridy += 1; 
        gbc.gridwidth = 2;
        gbc.gridheight = 1;

        layout.setConstraints(title, gbc);
        title.setFont(new Font("Tahoma", Font.BOLD, 32));
        add(title);
    }
}

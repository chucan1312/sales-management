package ui;

import model.Inventory;
import model.PurchaseRecord;
import model.WorkSpace;
import ui.menu.*;

public class Main {

    public static void main(String[] args) {
        new MainMenu(new WorkSpace(new Inventory(), new PurchaseRecord()));
    }
}

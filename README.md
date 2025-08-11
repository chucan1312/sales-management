# 📂 CPSC 210 Personal Project: Sales Management Application

A Java application that allows users to keep track of their store's sales and inventory. The program includes features to create, edit, and view lists of products and purchases, and all data is stored between sessions using a custom JSON persistence system built entirely with core Java (no external JSON libraries).

## ✨ Features

- **Inventory Management** – Add new products with details (name, ID, cost), update existing items, and search by ID or keywords.
- **Sales Recording** – Record purchases with date, sold items, and payment method; view purchase history by date range or filter by payment method.
- **Business Insights** – Calculate total profit for a chosen time frame; planned features include demand and return statistics.
- **Persistent Storage** – Save and load inventory and purchase records using a custom JSON persistence system built without external libraries.

## 🛠️ Technologies Used

- Java 8+  
- `java.io` & `java.nio.file` for file I/O  
- String operations for JSON encoding/decoding  
- JUnit (for testing, if applicable)

## 🚀 Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/chucan1312/cpsc-210-term-project.git
    cd cpsc-210-term-project
    ```
2. Run the application:
- Open the project in your IDE (IntelliJ, Eclipse, or VS Code with Java extensions).
- Locate the `Main.java` file in `src/main/ui/`.
- Right-click `Main.java` and choose **Run** (or use the Run button).

3. Data files will be saved automatically in the `data/` directory.

---

## 📬 Author
This is a personal project created by Chuc An Trinh for a course at UBC
GitHub: https://github.com/chucan1312/cpsc-210-term-project


📬 Contact
Developed by Your Name.
Feel free to open issues or suggestions for improvement.

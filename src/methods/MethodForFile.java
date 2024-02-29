package methods;

import model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;

import static java.lang.System.*;

public class MethodForFile {
    static final Scanner scanner = new Scanner(in);
    private Product newProduct = null;
    private int rowsPerPage = 8;
    public void loadData(String fileName) {
        try {
            // Define the animation characters
            String[] animationChars = { ".", "..", "...", "...." };
            String[] animationDots = { "|", "/", "-", "\\" };
            int timesToRepeat = 10;
            System.out.println("#".repeat(25));

            long startTime = System.currentTimeMillis(); // Start time measurement

            // Print the loading animation
            for (int i = 0; i < timesToRepeat; i++) {
                System.out.print("\rData is Loading " + animationDots[i % animationDots.length] + animationChars[i % animationChars.length]);
                try {
                    // Add a delay to control the speed of the animation
                    Thread.sleep(125);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }

            long endTime = System.currentTimeMillis(); // End time measurement
            long durationAnimation = endTime - startTime; // Calculate animation duration

            // Read data from file
            startTime = System.currentTimeMillis(); // Reset start time measurement
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                while ((reader.readLine()) != null) {
                    // Read the file line by line without doing anything
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
            endTime = System.currentTimeMillis(); // End time measurement after reading data
            long durationReading = endTime - startTime; // Calculate reading duration

            // Calculate total duration
            long totalDuration = durationAnimation + durationReading;

            // Convert milliseconds to seconds
            double durationSeconds = durationReading / 1000.0;

            // Clear the loading animation
            System.out.print("\rData is Loading ");
            System.out.println();
            System.out.println("#".repeat(25));
            System.out.println("Time taken for animation: " + durationAnimation + " milliseconds");
            System.out.println("Time taken to read data from file: " + durationReading + " milliseconds");
            System.out.println("Total time taken: " + totalDuration + " milliseconds");
            System.out.println("Total time taken in seconds: " + durationSeconds + " seconds");
            System.out.println("Completed! (1s)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //random
    public  void randomWriteProduct(List<Product> productList) {
        out.print("Enter random amount: ");
        int amount = scanner.nextInt();
        out.print("Are you sure you want to random " + amount + " Product? [Y/n]: ");
        char option = scanner.next().charAt(0); // Read the option as a string and get the first character

        if (option == 'Y' || option == 'y') {
            long startTime = currentTimeMillis(); // Record start time
            // Generate products
            Product[] products = new Product[amount];
            for (int i = 0; i < amount; i++) {
                products[i] = new Product();
                products[i].setCode("CSTAD" + i);
                products[i].setName("Product::" + i);
                products[i].setPrice(0.0);
                products[i].setQuantity(0);
                products[i].setDate(LocalDate.now());
                productList.add(products[i]);
            }
            // Write products to file using a separate thread
            Thread writingThread = new Thread(() -> {
                writeProductsToFile(productList);
            });
            writingThread.start();

            long endTime = currentTimeMillis(); // Record end time
            long duration = endTime - startTime; // Calculate duration
            double durationInSeconds = duration / 1000.0; // Convert milliseconds to seconds

            out.println("############################################");
            out.println("# Products have been randomly generated and written to file.");
            out.println("Write " + amount + " products speed: " + durationInSeconds + "s");
        } else {
            out.println("Operation cancelled.");
        }
    }

    //end random
    //

    public static void writeProductsToFile(List<Product> productList) {
        try (PrintWriter writer = new PrintWriter(new BufferedOutputStream(new FileOutputStream("transaction.txt", true)))) {
            for (Product product : productList) {
                writer.println(product.getCode() + "," + product.getName() + "," + product.getPrice() + "," + product.getQuantity() + "," + product.getDate());
            }
            writer.flush(); // Flush remaining data
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    public void createProduct(List<Product> productList){
//        Product product = new Product();
//
//        out.print("Enter product code: ");
//        product.setCode(scanner.nextLine());
//        out.print("Enter product name: ");
//        product.setName(scanner.nextLine());
//        out.print("Enter product price:");
//        product.setPrice(Double.parseDouble(scanner.nextLine()));
//        out.print("Enter product quantity:");
//        product.setQuantity(Integer.parseInt(scanner.nextLine()));
//        product.setDate(LocalDate.now());
//        productList.add(product);
//
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("product.txt",true))){
//            writer.write(product.getCode()+",");
//            writer.write(product.getName()+",");
//            writer.write(product.getPrice()+",");
//            writer.write(product.getQuantity()+",");
//            writer.write(product.getDate()+",");
//            writer.newLine();
//            writer.flush();
//        }catch (IOException e){
//            out.println(e.getMessage());
//        }
//
//    }
//public void createProduct(List<Product> productList) {
//    Product product = new Product();
//
//    boolean isDuplicate = true;
//    while (isDuplicate) {
//        out.print("Enter product code: ");
//        String code = scanner.nextLine();
//
//        // Check if the entered product code already exists
//        isDuplicate = productList.stream().anyMatch(p -> p.getCode().equals(code));
//        if (isDuplicate) {
//            out.println("Product code already exists. Please enter a unique product code.");
//        } else {
//            product.setCode(code);
//        }
//    }
//
//    out.print("Enter product name: ");
//    product.setName(scanner.nextLine());
//
//    // Validate product price
//    double price = 0.0;
//    boolean validPrice = false;
//    while (!validPrice) {
//        try {
//            out.print("Enter product price:");
//            price = Double.parseDouble(scanner.nextLine());
//            if (price <= 0.0) {
//                out.println("Price must be a positive number.");
//            } else {
//                validPrice = true;
//            }
//        } catch (NumberFormatException e) {
//            out.println("Invalid price format. Please enter a valid number.");
//        }
//    }
//    product.setPrice(price);
//
//    // Validate product quantity
//    int quantity = 0;
//    boolean validQuantity = false;
//    while (!validQuantity) {
//        try {
//            out.print("Enter product quantity:");
//            quantity = Integer.parseInt(scanner.nextLine());
//            if (quantity <= 0) {
//                out.println("Quantity must be a positive integer.");
//            } else {
//                validQuantity = true;
//            }
//        } catch (NumberFormatException e) {
//            out.println("Invalid quantity format. Please enter a valid integer.");
//        }
//    }
//    product.setQuantity(quantity);
//
//    product.setDate(LocalDate.now());
//    productList.add(product);
//
//    // Construct the product information string
//    String productInfo = product.getCode() + "," +
//            product.getName() + "," +
//            product.getPrice() + "," +
//            product.getQuantity() + "," +
//            product.getDate() + ",";
//
//    try (BufferedWriter writer = new BufferedWriter(new FileWriter("product.txt", true))) {
//        writer.write(productInfo);
//        writer.newLine();
//        writer.flush();
//    } catch (IOException e) {
//        out.println(e.getMessage());
//    }
//}

//public void createProduct(List<Product> productList) {
//    Product product = new Product();
//
//    // Read existing product codes from the file
//    Set<String> existingCodes = new HashSet<>();
//    try (BufferedReader reader = new BufferedReader(new FileReader("product.txt"))) {
//        String line;
//        while ((line = reader.readLine()) != null) {
//            String[] parts = line.split(",");
//            String code = parts[0].trim();
//            existingCodes.add(code);
//        }
//    } catch (IOException e) {
//        System.out.println("Error reading file: " + e.getMessage());
//        return;
//    }
//
//    boolean isDuplicate = true;
//    while (isDuplicate) {
//        System.out.print("Enter product code: ");
//        String code = scanner.nextLine();
//
//        // Check if the entered product code already exists in the file
//        if (existingCodes.contains(code)) {
//            System.out.println("Product code already exists in the file. Please enter a unique product code.");
//        } else {
//            // Check if the entered product code already exists in the list
//            isDuplicate = productList.stream().anyMatch(p -> p.getCode().equals(code));
//            if (isDuplicate) {
//                System.out.println("Product code already exists in memory. Please enter a unique product code.");
//            } else {
//                product.setCode(code);
//                isDuplicate = false;
//            }
//        }
//    }
//
//    // Proceed with entering other product details
//    System.out.print("Enter product name: ");
//    String name = scanner.nextLine();
//    product.setName(name);
//
//    double price = 0.0;
//    boolean validPrice = false;
//    while (!validPrice) {
//        try {
//            System.out.print("Enter product price: ");
//            price = Double.parseDouble(scanner.nextLine());
//            if (price <= 0.0) {
//                System.out.println("Price must be a positive number.");
//            } else {
//                validPrice = true;
//            }
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid price format. Please enter a valid number.");
//        }
//    }
//    product.setPrice(price);
//
//    int quantity = 0;
//    boolean validQuantity = false;
//    while (!validQuantity) {
//        try {
//            System.out.print("Enter product quantity: ");
//            quantity = Integer.parseInt(scanner.nextLine());
//            if (quantity <= 0) {
//                System.out.println("Quantity must be a positive integer.");
//            } else {
//                validQuantity = true;
//            }
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid quantity format. Please enter a valid integer.");
//        }
//    }
//    product.setQuantity(quantity);
//
//    product.setDate(LocalDate.now());
//    productList.add(product);
//
//    // Write the new product to the file
//    try (BufferedWriter writer = new BufferedWriter(new FileWriter("product.txt", true))) {
//        writer.write(product.getCode() + ",");
//        writer.write(product.getName() + ",");
//        writer.write(product.getPrice() + ",");
//        writer.write(product.getQuantity() + ",");
//        writer.write(product.getDate() + ",");
//        writer.newLine();
//        writer.flush();
//    } catch (IOException e) {
//        System.out.println("Error writing to file: " + e.getMessage());
//    }
//    if (!productList.isEmpty()) {
//        System.out.println("Details of the first index:");
//        System.out.println(productList.get(0));
//    }
//}

//    public void createProduct(List<Product> productList) {
//        Product product = new Product();
//
//        // Read existing product codes from the file
//        Set<String> existingCodes = new HashSet<>();
//        try (BufferedReader reader = new BufferedReader(new FileReader("product.txt"))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(",");
//                String code = parts[0].trim();
//                existingCodes.add(code);
//            }
//        } catch (IOException e) {
//            System.out.println("Error reading file: " + e.getMessage());
//            return;
//        }
//
//        boolean isDuplicate = true;
//        while (isDuplicate) {
//            System.out.print("Enter product code: ");
//            String code = scanner.nextLine();
//
//            // Check if the entered product code already exists in the file
//            if (existingCodes.contains(code)) {
//                System.out.println("Product code already exists in the file. Please enter a unique product code.");
//            } else {
//                // Check if the entered product code already exists in the list
//                isDuplicate = productList.stream().anyMatch(p -> p.getCode().equals(code));
//                if (isDuplicate) {
//                    System.out.println("Product code already exists in memory. Please enter a unique product code.");
//                } else {
//                    product.setCode(code);
//                    isDuplicate = false;
//                }
//            }
//        }
//
//        // Proceed with entering other product details
//        System.out.print("Enter product name: ");
//        String name = scanner.nextLine();
//        product.setName(name);
//
//        double price = 0.0;
//        boolean validPrice = false;
//        while (!validPrice) {
//            try {
//                System.out.print("Enter product price: ");
//                price = Double.parseDouble(scanner.nextLine());
//                if (price <= 0.0) {
//                    System.out.println("Price must be a positive number.");
//                } else {
//                    validPrice = true;
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid price format. Please enter a valid number.");
//            }
//        }
//        product.setPrice(price);
//
//        int quantity = 0;
//        boolean validQuantity = false;
//        while (!validQuantity) {
//            try {
//                System.out.print("Enter product quantity: ");
//                quantity = Integer.parseInt(scanner.nextLine());
//                if (quantity <= 0) {
//                    System.out.println("Quantity must be a positive integer.");
//                } else {
//                    validQuantity = true;
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid quantity format. Please enter a valid integer.");
//            }
//        }
//        product.setQuantity(quantity);
//
//        product.setDate(LocalDate.now());
//        productList.add(product);
//
//        // Write the new product to the file
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("product.txt", true))) {
//            writer.write(product.getCode() + ",");
//            writer.write(product.getName() + ",");
//            writer.write(product.getPrice() + ",");
//            writer.write(product.getQuantity() + ",");
//            writer.write(product.getDate() + ",");
//            writer.newLine();
//            writer.flush();
//        } catch (IOException e) {
//            System.out.println("Error writing to file: " + e.getMessage());
//        }
//        setNewProduct(product);
//
//    }
public void createProduct(List<Product> productList) {
    Product product = new Product();
    Scanner scanner = new Scanner(System.in);
    while (true) {
        System.out.print("Enter product code: ");
        String code = scanner.nextLine();

        // Check if the entered product code already exists in the list
        boolean isDuplicate = productList.stream().anyMatch(p -> p.getCode().equals(code));
        if (isDuplicate) {
            System.out.println("Product code already exists in memory. Please enter a unique product code.");
            continue; // Ask for input again
        }

        // Proceed with entering other product details
        product.setCode(code);
        break; // Exit the loop if the code is unique
    }

    System.out.print("Enter product name: ");
    String name = scanner.nextLine();
    product.setName(name);

    double price = 0.0;
    boolean validPrice = false;
    while (!validPrice) {
        try {
            System.out.print("Enter product price: ");
            price = Double.parseDouble(scanner.nextLine());
            if (price <= 0.0) {
                System.out.println("Price must be a positive number.");
            } else {
                validPrice = true;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid price format. Please enter a valid number.");
        }
    }
    product.setPrice(price);

    int quantity = 0;
    boolean validQuantity = false;
    while (!validQuantity) {
        try {
            System.out.print("Enter product quantity: ");
            quantity = Integer.parseInt(scanner.nextLine());
            if (quantity <= 0) {
                System.out.println("Quantity must be a positive integer.");
            } else {
                validQuantity = true;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity format. Please enter a valid integer.");
        }
    }
    product.setQuantity(quantity);

    product.setDate(LocalDate.now());

    // Add the new product to the beginning of the list
    productList.add(0, product);
    // Write the new product to the file
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("transaction.txt", true))) {
        writer.write(product.getCode() + ",");
        writer.write(product.getName() + ",");
        writer.write(product.getPrice() + ",");
        writer.write(product.getQuantity() + ",");
        writer.write(product.getDate() + ",");
        writer.newLine();
    } catch (IOException e) {
        System.out.println("Error writing to file: " + e.getMessage());
    }

    // Create a copy of the productList
    List<Product> products = new ArrayList<>(productList);
}

//public void createProduct(List<Product> productList) {
//    Product product = new Product();
//
//    // Your existing createProduct method implementation
//    System.out.print("Enter product code: ");
//    String code = scanner.nextLine();
//
//    // Check if the entered product code already exists in the list
//    boolean isDuplicate = productList.stream().anyMatch(p -> p.getCode().equals(code));
//    if (isDuplicate) {
//        System.out.println("Product code already exists in memory. Please enter a unique product code.");
//        return; // Exit the method without adding the product
//    }
//
//    // Proceed with entering other product details
//    product.setCode(code);
//
//    System.out.print("Enter product name: ");
//    String name = scanner.nextLine();
//    product.setName(name);
//
//    double price = 0.0;
//    boolean validPrice = false;
//    while (!validPrice) {
//        try {
//            System.out.print("Enter product price: ");
//            price = Double.parseDouble(scanner.nextLine());
//            if (price <= 0.0) {
//                System.out.println("Price must be a positive number.");
//            } else {
//                validPrice = true;
//            }
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid price format. Please enter a valid number.");
//        }
//    }
//    product.setPrice(price);
//
//    int quantity = 0;
//    boolean validQuantity = false;
//    while (!validQuantity) {
//        try {
//            System.out.print("Enter product quantity: ");
//            quantity = Integer.parseInt(scanner.nextLine());
//            if (quantity <= 0) {
//                System.out.println("Quantity must be a positive integer.");
//            } else {
//                validQuantity = true;
//            }
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid quantity format. Please enter a valid integer.");
//        }
//    }
//    product.setQuantity(quantity);
//
//    product.setDate(LocalDate.now());
//
//    // Add the new product to the beginning of the list
//    productList.add(0, product);
//
//    // Write the new product to the file
//    try (BufferedWriter writer = new BufferedWriter(new FileWriter("product.txt", true))) {
//        writer.write(product.getCode() + ",");
//        writer.write(product.getName() + ",");
//        writer.write(product.getPrice() + ",");
//        writer.write(product.getQuantity() + ",");
//        writer.write(product.getDate() + ",");
//        writer.newLine();
//        writer.flush();
//    } catch (IOException e) {
//        System.out.println("Error writing to file: " + e.getMessage());
//    }
//}
    public List<Product> readProductsFromFile(String fileName) {
        List<Product> productList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String code = parts[0].trim();
                    String name = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());
                    int quantity = Integer.parseInt(parts[3].trim());
                    LocalDate date = LocalDate.parse(parts[4].trim()); // Assuming date is stored in ISO_LOCAL_DATE format

                    Product product = new Product(code, name, price, quantity, date);
                    // Add new products at the beginning of the list
                    productList.add(0, product);
                } else {
                    System.out.println("Invalid data in file: " + line);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return productList;
    }

//    public void editProduct(List<Product> productList) {
//        Table table = new Table(1, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.SURROUND);
//        Product updatedProduct = new Product();
//        out.print("Enter product code: ");
//        String code = scanner.nextLine();
//        for (Product product : productList) {
//            if (product.getCode().equals(code)) {
//                table.addCell("Code : " + product.getCode());
//                table.addCell("Name : " + product.getName());
//                table.addCell("Price : " + product.getPrice());
//                table.addCell("Quantity : " + product.getQuantity());
//                table.addCell("Date : " + product.getDate());
//                out.println(table.render());
//
//                int option;
//                do {
//                    out.println("Select an option:");
//                    out.println("1. Edit all");
//                    out.println("2. Edit name");
//                    out.println("3. Edit price");
//                    out.println("4. Edit quantity");
//                    out.println("0. Exit");
//                    out.print("Enter option: ");
//                    while (!scanner.hasNextInt()) {
//                        System.out.println("Invalid option. Please enter a number.");
//                        scanner.next();
//                    }
//                    option = scanner.nextInt();
//                    scanner.nextLine(); // Consume newline character
//
//                    switch (option) {
//                        case 1:
//                            // Edit all fields
//                            updatedProduct = editAllFields(product);
//                            break;
//                        case 2:
//                            // Edit name
//                            product.setName(editName());
//                            updatedProduct = product;
//                            break;
//                        case 3:
//                            // Edit price
//                            product.setPrice(editPrice());
//                            updatedProduct = product;
//                            break;
//                        case 4:
//                            // Edit quantity
//                            product.setQuantity(editQuantity());
//                            updatedProduct = product;
//                            break;
//                        case 0:
//                            break;
//                        default:
//                            out.println("Invalid option. Please try again.");
//                    }
//                } while (option != 0);
//
//                // Update the product in the list obtained from readProductsFromFile
//                int index = productList.indexOf(product);
//                if (index != -1) {
//                    productList.set(index, updatedProduct);
//                } else {
//                    out.println("Product not found in the list.");
//                }
//                break; // Exit the loop once the product is found and updated
//            }
//        }
//    }

// Helper methods to edit individual fields

    public void editProduct(List<Product> productList) {
        Table table = new Table(1, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.SURROUND);
        out.print("Enter product code: ");
        String code = scanner.nextLine();
        boolean productFound = false;

        for (Product product : productList) {
            if (product.getCode().equals(code)) {
                productFound = true;
                table.addCell("Code : " + product.getCode());
                table.addCell("Name : " + product.getName());
                table.addCell("Price : " + product.getPrice());
                table.addCell("Quantity : " + product.getQuantity());
                table.addCell("Date : " + product.getDate());
                out.println(table.render());

                while (true) {
                    Table table1 = new Table(7, BorderStyle.UNICODE_DOUBLE_BOX_WIDE,ShownBorders.SURROUND);

                    out.println("> Select an option :");
                    table1.addCell("  1). Edit All    |");
                    table1.addCell("  2). Edit Name    |");
                    table1.addCell("  3). Edit Price    |");
                    table1.addCell("  4). Edit Quantity    |");
                    table1.addCell("  5). Exit      ");
                    out.println(table1.render());
                    out.print("Enter option: ");
                    int option = -1;
                    try {
                        option = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid option. Please enter a number.");
                        continue;
                    }

                    switch (option) {
                        case 1:
                            // Edit all fields
                            editAllFields(product);
                            break;
                        case 2:
                            // Edit name
                            product.setName(editName());
                            break;
                        case 3:
                            // Edit price
                            product.setPrice(editPrice());
                            break;
                        case 4:
                            // Edit quantity
                            product.setQuantity(editQuantity());
                            break;
                        case 5:
                            // Exit
                            break;
                        default:
                            out.println("Invalid option. Please try again.");
                            continue;
                    }
                    if (option == 5) {
                        break;
                    }
                    // Update the product in the file
                    updateProductInFile(product);

                    // Re-render the table after update
                    table = new Table(1, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.SURROUND);
                    table.addCell("Code : " + product.getCode());
                    table.addCell("Name : " + product.getName());
                    table.addCell("Price : " + product.getPrice());
                    table.addCell("Quantity : " + product.getQuantity());
                    table.addCell("Date : " + product.getDate());
                    out.println(table.render());
                }
                break; // Exit the loop once the product is found and updated
            }
        }

        if (!productFound) {
            out.println("Product not found in the list.");
        }
    }


    private void updateProductInFile(Product product) {
        List<String> fileContent;
        try {
            // Read all lines from the file
            fileContent = Files.readAllLines(Paths.get("product.txt"));

            // Iterate through the lines and find the line to update
            for (int i = 0; i < fileContent.size(); i++) {
                String line = fileContent.get(i);
                String[] parts = line.split(",");
                if (parts[0].equals(product.getCode())) {
                    // Update the line with the new product details
                    fileContent.set(i, product.getCode() + "," + product.getName() + "," + product.getPrice() + "," + product.getQuantity() + "," + product.getDate());
                    break;
                }
            }

            // Write the updated content back to the file
            Files.write(Paths.get("product.txt"), fileContent);
        } catch (IOException e) {
            System.out.println("Error updating product in file: " + e.getMessage());
        }
    }

    private Product editAllFields(Product product) {
        product.setName(editName());
        product.setPrice(editPrice());
        product.setQuantity(editQuantity());
        product.setDate(LocalDate.now());
        return product;
    }

    private String editName() {
        out.print("Enter product name: ");
        return scanner.nextLine();
    }

    private double editPrice() {
        double price;
        while (true) {
            try {
                out.print("Enter product price: ");
                price = Double.parseDouble(scanner.nextLine());
                if (price <= 0) {
                    System.out.println("Price must be a positive number.");
                } else {
                    return price;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid price format. Please enter a valid number.");
            }
        }
    }

    private int editQuantity() {
        int quantity;
        while (true) {
            try {
                out.print("Enter product quantity: ");
                quantity = Integer.parseInt(scanner.nextLine());
                if (quantity <= 0) {
                    System.out.println("Quantity must be a positive integer.");
                } else {
                    return quantity;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity format. Please enter a valid integer.");
            }
        }
    }

//    public void editProduct(List<Product> productList){
//        Table table = new Table(1,BorderStyle.UNICODE_DOUBLE_BOX_WIDE,ShownBorders.SURROUND);
//        Product updatedProduct = new Product();
//        out.print("Enter product code: ");
//        String code = scanner.nextLine();
//        for(Product product : productList){
//            if (product.getCode().equals(code)){
//
//                    table.addCell("Code : "+product.getCode());
//                    table.addCell("Name : " +product.getName());
//                    table.addCell("Price : "+product.getPrice());
//                    table.addCell("Quantity : "+product.getQuantity());
//                    table.addCell("Date : "+product.getDate());
//                    out.println(table.render());
//
//                out.print("Enter product name: ");
//                product.setName(scanner.nextLine());
//                out.print("Enter product price:");
//                product.setPrice(scanner.nextDouble());
//                out.print("Enter product quantity:");
//                product.setQuantity(scanner.nextInt());
//                product.setDate(LocalDate.now());
//                productList.set(productList.indexOf(product), updatedProduct);
//            }
//        }
//
//    }

//    public List<Product> readProductsFromFile(String fileName) {
//        List<Product> productList = new ArrayList<>();
//        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(",");
//                if (parts.length == 5) {
//                    Product product = new Product();
//                    product.setCode(parts[0].trim());
//                    product.setName(parts[1].trim());
//                    product.setPrice(Double.parseDouble(parts[2].trim()));
//                    product.setQuantity(Integer.parseInt(parts[3].trim()));
//                    product.setDate(LocalDate.parse(parts[4].trim())); // Assuming date is stored in ISO_LOCAL_DATE format
//                    productList.add(product);
//                } else {
//                    System.out.println("Invalid data in file: " + line);
//                }
//            }
//        } catch (IOException | NumberFormatException e) {
//            System.out.println("Error reading file: " + e.getMessage());
//        }
//
//        // Add the newly added product to the beginning of the list
//        if (newProduct != null) {
//            productList.add(0, newProduct);
//        }
//
//        return productList;
//    }

//public void editProduct(List<Product> productList) {
//    Table table = new Table(1, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.SURROUND);
//    Product updatedProduct = new Product();
//    out.print("Enter product code: ");
//    String code = scanner.nextLine();
//    for (Product product : productList) {
//        if (product.getCode().equals(code)) {
//            table.addCell("Code : " + product.getCode());
//            table.addCell("Name : " + product.getName());
//            table.addCell("Price : " + product.getPrice());
//            table.addCell("Quantity : " + product.getQuantity());
//            table.addCell("Date : " + product.getDate());
//            out.println(table.render());
//
//            int option;
//            do {
//                out.println("Select an option:");
//                out.println("1. Edit all");
//                out.println("2. Edit name");
//                out.println("3. Edit price");
//                out.println("4. Edit quantity");
//                out.println("0. Exit");
//                out.print("Enter option: ");
//                while (!scanner.hasNextInt()) {
//                    System.out.println("Invalid option. Please enter a number.");
//                    scanner.next();
//                }
//                option = scanner.nextInt();
//                scanner.nextLine(); // Consume newline character
//
//                switch (option) {
//                    case 1:
//                        out.print("Enter product name: ");
//                        product.setName(scanner.nextLine());
//                        double price;
//                        boolean validPrice = false;
//                        while (!validPrice) {
//                            try {
//                                out.print("Enter product price: ");
//                                price = scanner.nextDouble();
//                                if (price <= 0) {
//                                    System.out.println("Price must be a positive number.");
//                                } else {
//                                    product.setPrice(price);
//                                    validPrice = true;
//                                }
//                            } catch (InputMismatchException e) {
//                                System.out.println("Invalid price format. Please enter a valid number.");
//                                scanner.nextLine(); // Consume newline character
//                            }
//                        }
//                        int quantity;
//                        boolean validQuantity = false;
//                        while (!validQuantity) {
//                            try {
//                                out.print("Enter product quantity: ");
//                                quantity = scanner.nextInt();
//                                if (quantity <= 0) {
//                                    System.out.println("Quantity must be a positive integer.");
//                                } else {
//                                    product.setQuantity(quantity);
//                                    validQuantity = true;
//                                }
//                            } catch (InputMismatchException e) {
//                                System.out.println("Invalid quantity format. Please enter a valid integer.");
//                                scanner.nextLine(); // Consume newline character
//                            }
//                        }
//                        product.setDate(LocalDate.now());
//                        updatedProduct = product;
//                        break;
//                    case 2:
//                        out.print("Enter product name: ");
//                        product.setName(scanner.nextLine());
//                        updatedProduct = product;
//                        break;
//                    case 3:
//                        double newPrice;
//                        boolean validNewPrice = false;
//                        while (!validNewPrice) {
//                            try {
//                                out.print("Enter product price: ");
//                                newPrice = scanner.nextDouble();
//                                if (newPrice <= 0) {
//                                    System.out.println("Price must be a positive number.");
//                                } else {
//                                    product.setPrice(newPrice);
//                                    validNewPrice = true;
//                                }
//                            } catch (InputMismatchException e) {
//                                System.out.println("Invalid price format. Please enter a valid number.");
//                                scanner.nextLine(); // Consume newline character
//                            }
//                        }
//                        updatedProduct = product;
//                        break;
//                    case 4:
//                        int newQuantity;
//                        boolean validNewQuantity = false;
//                        while (!validNewQuantity) {
//                            try {
//                                out.print("Enter product quantity: ");
//                                newQuantity = scanner.nextInt();
//                                if (newQuantity <= 0) {
//                                    System.out.println("Quantity must be a positive integer.");
//                                } else {
//                                    product.setQuantity(newQuantity);
//                                    validNewQuantity = true;
//                                }
//                            } catch (InputMismatchException e) {
//                                System.out.println("Invalid quantity format. Please enter a valid integer.");
//                                scanner.nextLine(); // Consume newline character
//                            }
//                        }
//                        updatedProduct = product;
//                        break;
//                    case 0:
//                        break;
//                    default:
//                        out.println("Invalid option. Please try again.");
//                }
//            } while (option != 0);
//
//            // Update the product in the list
//            productList.set(productList.indexOf(product), updatedProduct);
//            break; // Exit the loop once the product is found and updated
//        }
//    }
//}
//    public void setNewProduct(Product newProduct) {
//        this.newProduct = newProduct;
//    }
    public void setRowsPerPage(Scanner scanner) {
        int newRowsPerPage;
        boolean isValidInput = false;
        do {
            System.out.print("Enter the number of rows per page (must be a positive integer): ");
            if (scanner.hasNextInt()) {
                newRowsPerPage = scanner.nextInt();
                if (newRowsPerPage > 0) {
                    this.rowsPerPage = newRowsPerPage;
                    isValidInput = true;
                } else {
                    System.out.println("Invalid input. Please enter a positive integer.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Consume the invalid input
            }
        } while (!isValidInput);
        scanner.nextLine(); // Consume the remaining newline character
    }
//    public List<Product> readProductsFromFile(String fileName) {
//        List<Product> productList = new ArrayList<>();
//        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(",");
//                if (parts.length == 5) {
//                    Product product = new Product();
//                    product.setCode(parts[0].trim());
//                    product.setName(parts[1].trim());
//                    product.setPrice(Double.parseDouble(parts[2].trim()));
//                    product.setQuantity(Integer.parseInt(parts[3].trim()));
//                    product.setDate(LocalDate.parse(parts[4].trim())); // Assuming date is stored in ISO_LOCAL_DATE format
//                    productList.add(product);
//                } else {
//                    out.println("Invalid data in file: " + line);
//                }
//            }
//        } catch (IOException | NumberFormatException e) {
//            out.println("Error reading file: " + e.getMessage());
//        }
//
//        return productList;
//    }

    public void viewAllProduct(List<Product> productList) {
//        int rowsPerPage = 8;
        int totalPages = (int) Math.ceil((double) productList.size() / rowsPerPage);
        int currentPage = 1;
        int totalRecords = productList.size();
        do {
            int startIndex = (currentPage - 1) * rowsPerPage;
            int endIndex = Math.min(startIndex + rowsPerPage, productList.size());

            Table table = new Table(5, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.ALL);
            table.addCell("Product Code");
            table.addCell("Product Name");
            table.addCell("Product Price");
            table.addCell("Product Quantity");
            table.addCell("Product Date");

            for (int i = startIndex; i < endIndex; i++) {
                Product product = productList.get(i);
                table.addCell("Code : " + product.getCode());
                table.addCell("Name : " + product.getName());
                table.addCell("Price : " + product.getPrice());
                table.addCell("Quantity : " + product.getQuantity());
                table.addCell("Date : " + product.getDate());
            }

            out.println(table.render());
            out.println("~".repeat(87));
            out.printf("Page: %d of %d   %-48s Total records: %d%n", currentPage, totalPages, "", totalRecords);

            currentPage = pageNavigation(currentPage, totalPages);
        } while (currentPage != -1);
    }

    //here code for display
//    public void viewAllProduct(List<Product> productList) {
////        int rowsPerPage = 8;
//        int totalPages = (int) Math.ceil((double) productList.size() / rowsPerPage);
//        int currentPage = 1;
//        int totalRecords = productList.size();
//        do {
//            int startIndex = (currentPage - 1) * rowsPerPage;
//            int endIndex = Math.min(startIndex + rowsPerPage, productList.size());
//
//            Table table = new Table(5, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.ALL);
//            table.addCell("Product Code");
//            table.addCell("Product Name");
//            table.addCell("Product Price");
//            table.addCell("Product Quantity");
//            table.addCell("Product Date");
//
//            for (int i = startIndex; i < endIndex; i++) {
//                Product product = productList.get(i);
//                table.addCell("Code : " + product.getCode());
//                table.addCell("Name : " + product.getName());
//                table.addCell("Price : " + product.getPrice());
//                table.addCell("Quantity : " + product.getQuantity());
//                table.addCell("Date : " + product.getDate());
//            }
//
//            out.println(table.render());
//            out.println("~".repeat(87));
//            out.printf("Page: %d of %d   %-48s Total records: %d%n", currentPage, totalPages, "", totalRecords);
//
//            currentPage = pageNavigation(currentPage, totalPages);
//        } while (currentPage != -1);
//    }
    //end code
    private int pageNavigation(int currentPage, int totalPages) {
        out.printf("Page Navigation   %-25s (F)irst   (P)revious (G)oto (N)ext  (L)ast%n", "", "", "", "", "");
        out.println("~".repeat(87));
        out.print("(B)ack or Navigation page : ");

        Scanner scanner = new Scanner(System.in);
        char option = scanner.next().charAt(0);
        switch (option) {
            case 'F', 'f':
                return 1;
            case 'P', 'p':
                return Math.max(1, currentPage - 1);
            case 'G', 'g':
                out.print("Enter page number to go to: ");
                int pageNumber = scanner.nextInt();
                return Math.min(Math.max(1, pageNumber), totalPages);
            case 'N', 'n':
                return Math.min(totalPages, currentPage + 1);
            case 'L', 'l':
                return totalPages;
            case 'B', 'b':
                return -1; // Signal to exit loop
            default:
                out.println("Invalid option!");
                return currentPage;
        }
    }

    public void readOnlyProduct(List<Product> productList){
        Table table = new Table(1, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.SURROUND);
        out.print("Enter product code: ");
        String code = scanner.nextLine();
        for(Product product : productList){
            if (product.getCode().equals(code)){
                table.addCell("Code : "+product.getCode());
                table.addCell("Name : " +product.getName());
                table.addCell("Price : "+product.getPrice());
                table.addCell("Quantity : "+product.getQuantity());
                table.addCell("Date : "+product.getDate());
                out.println(table.render());
            }
        }
    }
    private static final String FILE_NAME = "product.txt";

    public void deleteProduct(List<Product> productList) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter product code: ");
        String code = scanner.nextLine();

        Iterator<Product> iterator = productList.iterator();
        boolean found = false;
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getCode().equals(code)) {
                iterator.remove();
                found = true;
                break; // Once the product is found and removed, exit the loop
            }
        }

        if (!found) {
            System.out.println("Product not found with code: " + code);
            return; // Exit the method if product is not found
        }

        // Write the updated productList back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Product product : productList) {
                writer.write(product.getCode() + ",");
                writer.write(product.getName() + ",");
                writer.write(product.getPrice() + ",");
                writer.write(product.getQuantity() + ",");
                writer.write(product.getDate() + "\n");
            }
            System.out.println("Record deleted successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
//    public void deleteProduct(List<Product> productList) {
//        out.print("Enter product code: ");
//        String code = scanner.nextLine();
//
//        Iterator<Product> iterator = productList.iterator();
//        while (iterator.hasNext()) {
//            Product product = iterator.next();
//            if (product.getCode().equals(code)) {
//                iterator.remove();
//            }
//        }
//
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("product.txt"))) {
//            for (Product product : productList) {
//                writer.write(product.getCode() + ",");
//                writer.write(product.getName() + ",");
//                writer.write(product.getPrice() + ",");
//                writer.write(product.getQuantity() + ",");
//                writer.write(product.getDate() + "\n");
//            }
//        } catch (IOException e) {
//            out.println("Error writing to file: " + e.getMessage());
//        }
//
//        out.println("Record deleted successfully.");
//    }
//    public void searchProduct(List<Product> productList){
//        Table table = new Table(1, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.SURROUND);
//        out.print("Enter product name: ");
//        String name = scanner.nextLine();
//        for(Product product : productList){
//            if (product.getName().contains(name)){
//                viewAllProduct(productList);
//            }
//        }
//
//    }

//public void searchProduct(List<Product> productList){
//    out.print("Enter product code: ");
//    String code = scanner.nextLine();
//    boolean found = false;
//    for(Product product : productList){
//        if (product.getCode().equals(code)){
//            out.println("Product Found:");
//            out.println("Code : " + product.getCode());
//            out.println("Name : " + product.getName());
//            out.println("Price : " + product.getPrice());
//            out.println("Quantity : " + product.getQuantity());
//            out.println("Date : " + product.getDate());
//            found = true;
//            break; // Stop searching once found
//        }
//    }
//    if (!found) {
//        out.println("No product found with the given code.");
//    }
//}
public void searchProduct(List<Product> productList) {
    Scanner scanner = new Scanner(System.in);
    boolean searchByName = false;

    do {
        out.println("Search by:");
        out.println("1. Product Code");
        out.println("2. Product Name");
        out.println("0. Exit");
        out.print("Enter option: ");

        int option;
        try {
            option = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            out.println("Invalid option. Please enter a number.");
            continue;
        }

        switch (option) {
            case 1:
                searchByCode(productList);
                break;
            case 2:
                searchByName(productList);
                break;
            case 0:
                return; // Exit the method
            default:
                out.println("Invalid option. Please try again.");
        }
    } while (true);
}
    private void searchByCode(List<Product> productList) {
        out.print("Enter product code: ");
        String code = scanner.nextLine();
        boolean found = false;

        List<Product> searchResults = new ArrayList<>();

        for (Product product : productList) {
            if (product.getCode().startsWith(code)) {
                found = true;
                searchResults.add(product);
            }
        }

        if (!found) {
            out.println("No products found with the given code prefix.");
        } else {
            out.println("Search Results:");
            viewAllProduct(searchResults);
        }
    }
    private void searchByName(List<Product> productList) {
        out.print("Enter product name: ");
        String name = scanner.nextLine();
        boolean found = false;

        List<Product> searchResults = new ArrayList<>();

        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(name.toLowerCase())) {
                found = true;
                searchResults.add(product);
            }
        }

        if (!found) {
            out.println("No products found with the given name.");
        } else {
            out.println("Search Results:");
            viewAllProduct(searchResults);
        }
    }


//public void searchProduct(List<Product> productList) {
//    out.print("Enter product code: ");
//    String code = scanner.nextLine();
//    boolean found = false;
//
//    List<Product> searchResults = new ArrayList<>();
//
//    for (Product product : productList) {
//        if (product.getCode().startsWith(code)) {
//            found = true;
//            searchResults.add(product);
//        }
//    }
//
//    if (!found) {
//        out.println("No products found with the given code prefix.");
//    } else {
//        out.println("Search Results:");
//        viewAllProduct(searchResults);
//    }
//}




//    public void searchProduct(List<Product> productList) {
//        out.print("Enter product code: ");
//        String code = scanner.nextLine();
//        boolean found = false;
//        List<String[]> rows = new ArrayList<>();
//
//        for (Product product : productList) {
//            if (product.getCode().startsWith(code)) {
//                if (!found) {
//                    out.println("Matching Products:");
//                    rows.add(new String[]{"Code", "Name", "Price", "Quantity", "Date"});
//                    found = true;
//                }
//                rows.add(new String[]{product.getCode(), product.getName(), String.valueOf(product.getPrice()), String.valueOf(product.getQuantity()), String.valueOf(product.getDate())});
//            }
//        }
//
//        if (!found) {
//            out.println("No products found with the given code prefix.");
//        } else {
//            printTable(rows);
//        }
//    }
//
//    private void printTable(List<String[]> rows) {
//        int[] maxLengths = new int[rows.get(0).length];
//
//        for (String[] row : rows) {
//            for (int i = 0; i < row.length; i++) {
//                maxLengths[i] = Math.max(maxLengths[i], row[i].length());
//            }
//        }
//
//        for (String[] row : rows) {
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < row.length; i++) {
//                sb.append(String.format("%-" + (maxLengths[i] + 2) + "s", row[i])); // Padding for alignment
//            }
//            out.println(sb.toString());
//        }
//    }


//public void searchProduct(List<Product> productList){
//    Table table = new Table(1, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.SURROUND);
//    out.print("Enter product code: ");
//    String code = scanner.nextLine();
//    boolean found = false;
//    for(Product product : productList){
//        if (product.getCode().equals(code)){
//            table.addCell("Code : "+product.getCode());
//            table.addCell("Name : " +product.getName());
//            table.addCell("Price : "+product.getPrice());
//            table.addCell("Quantity : "+product.getQuantity());
//            table.addCell("Date : "+product.getDate());
////            out.println(table.render());
//            found = true;
//            break; // Stop searching once found
//        }
//    }
//    if (!found) {
//        out.println("No product found with the given code.");
//    } else {
//        out.println(table.render());
//    }
//}


    public void backUpData(String sourceFilePath, String backupFilePath) {
        try {
            Path sourcePath = Path.of(sourceFilePath);
            Path backupPath = Path.of(backupFilePath);
            if (Files.exists(sourcePath)) {
                // Create the backup directory if it doesn't exist
                Files.createDirectories(backupPath.getParent());

                // Open streams with buffering
                try (BufferedInputStream inputStream = new BufferedInputStream(Files.newInputStream(sourcePath));
                     BufferedOutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(backupPath))) {

                    // Copy data in chunks
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    outputStream.flush(); // Flush the buffer after writing all data
                }

                System.out.println("Backup created successfully.");
            } else {
                System.out.println("Source file does not exist.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static File[] getBackupFiles(String backupDirectory) {
        File backupDir = new File(backupDirectory);
        return backupDir.listFiles();
    }

    public void listBackupFiles(String backupDirectory) {
        File backupDir = new File(backupDirectory);
        File[] backupFiles = backupDir.listFiles();

        if (backupFiles != null && backupFiles.length > 0) {
            Table table = new Table(1, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.SURROUND);
            table.addCell("Backup Files:");

            for (int i = 0; i < backupFiles.length; i++) {
                table.addCell((i + 1) + ". " + backupFiles[i].getName());
            }

            System.out.println(table.render());
        } else {
            System.out.println("No backup files found.");
        }
    }
    public void restoreData(String sourceFilePath, String backupDirectory, int fileNumber) {
        try {
            File[] backupFiles = getBackupFiles(backupDirectory);

            if (fileNumber >= 1 && fileNumber <= backupFiles.length) {
                File selectedFile = backupFiles[fileNumber - 1];
                String backupFileName = selectedFile.getName();
                String restoredFilePath = sourceFilePath.replaceFirst("[.][^.]+$", "_restored" + backupFileName.substring(backupFileName.lastIndexOf(".")));

                Files.copy(selectedFile.toPath(), Path.of(restoredFilePath), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Data restored successfully to: " + restoredFilePath);
            } else {
                System.out.println("Invalid file number.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

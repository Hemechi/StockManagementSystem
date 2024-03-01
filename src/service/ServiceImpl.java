package service;

import methods.MethodForFile;
import methods.MethodForFileImpl;
import model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import util.Animation;
import util.AnimationImpl;
import util.Pagination;
import util.PaginationImpl;

import java.io.*;
import java.security.ProtectionDomain;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

public class ServiceImpl implements Service {
    static Pagination pagination = new PaginationImpl();
    static MethodForFile method = new MethodForFileImpl();
    static Animation animation = new AnimationImpl();
    static final Scanner scanner = new Scanner(in);

    int rowsPerPage = 8;
    @Override
    public void createProduct(List<Product> productList ) {
        Product product = new Product();

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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("product.txt", true))) {
            writer.write(product.getCode() + ",");
            writer.write(product.getName() + ",");
            writer.write(product.getPrice() + ",");
            writer.write(product.getQuantity() + ",");
            writer.write(product.getDate() + ",");
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    @Override
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
                    method.updateProductInFile(product);

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
    @Override
    public void readProductsFromFile(List<Product> productList) {
        long startTime = System.currentTimeMillis();

        // Show loading animation
        Thread animationThread = new Thread(() -> {
            animation.loadData();
        });
        animationThread.start();

        // Start reading file
        Thread fileReadingThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new FileReader("product.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",", 5); // Limit split to 5 parts
                    if (parts.length == 5) {
                        String code = parts[0];
                        String name = parts[1];
                        double price = Double.parseDouble(parts[2]);
                        int quantity = Integer.parseInt(parts[3]);
                        LocalDate date = LocalDate.parse(parts[4].trim()); // Assuming date is stored in ISO_LOCAL_DATE format

                        Product product = new Product(code, name, price, quantity, date);
                        synchronized (productList) {
                            productList.add(product);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Error parsing data: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        });
        fileReadingThread.start();

        // Wait for file reading thread to finish
        try {
            fileReadingThread.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted: " + e.getMessage());
            Thread.currentThread().interrupt(); // Restore interrupted status
        }

        // Wait for animation thread to finish
        try {
            animationThread.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted: " + e.getMessage());
            Thread.currentThread().interrupt(); // Restore interrupted status
        }

        // Record end time
        long endTime = System.currentTimeMillis();
        double totalTimeSeconds = (endTime - startTime) / 1000.0;
        System.out.println("Completed: " + totalTimeSeconds + " seconds");
    }
    @Override
    public void viewAllProduct(List<Product> productList) {
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

            System.out.println(table.render());
            System.out.println("~".repeat(87));
            System.out.printf("Page: %d of %d   %-48s Total records: %d%n", currentPage, totalPages, "", totalRecords);

            currentPage = pagination.pageNavigation(currentPage, totalPages);
        } while (currentPage != -1);
    }
    @Override
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
    @Override
    public void deleteProduct(List<Product> productList) {
        String code;
        boolean validInput = false;

        do {
            System.out.print("Enter product code: ");
            code = scanner.nextLine().toUpperCase();

            // Check if the input matches any product code
            for (Product product : productList) {
                if (product.getCode().equals(code)) {
                    validInput = true;
                    break;
                }
            }

            if (!validInput) {
                System.out.println("Invalid product code. Please try again.");
            }

        } while (!validInput);

        Iterator<Product> iterator = productList.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getCode().equals(code)) {
                iterator.remove();
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("product.txt"))) {
            for (Product product : productList) {
                writer.write(product.getCode() + ",");
                writer.write(product.getName() + ",");
                writer.write(product.getPrice() + ",");
                writer.write(product.getQuantity() + ",");
                writer.write(product.getDate() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

        System.out.println("Record deleted successfully.");
    }
    @Override
    public void searchProduct(List<Product> productList) {
        Scanner scanner = new Scanner(System.in);
        boolean searchByName = false;

        do {
            Table table2 = new Table(7, BorderStyle.UNICODE_DOUBLE_BOX_WIDE,ShownBorders.SURROUND);

            out.println("> Select an option :");
            table2.addCell("  1). Search by Product Code    |");
            table2.addCell("  2). Search by Product Name    |");
            table2.addCell("  3). Exit    ");
            out.println(table2.render());
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
                case 3:
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

    @Override
    public void randomProduct(List<Product> transactions , List<Product> productList  ,String filename) {
        out.print("Enter random amount: ");
        int amount = scanner.nextInt();
        out.print("Are you sure you want to random " + amount + " Product? [Y/n]: ");
        char option = scanner.next().charAt(0);
        if (option == 'Y' || option == 'y') {
            long startTime = System.currentTimeMillis();
            animation.loadData();
            Product[] products = new Product[amount];
            for (int i = 0; i < amount; i++) {
                products[i] = new Product();
                products[i].setCode("CSTAD" + i);
                products[i].setName("Product::" + i);
                products[i].setPrice(0.0);
                products[i].setQuantity(0);
                products[i].setDate(LocalDate.now());
                synchronized (productList) {
                    productList.add(products[i]);
                }
            }
            // Write products to file using a separate thread
            Thread writingThread = new Thread(() -> {
                writeProductsToFile(productList,filename);
            });
            writingThread.start();



            try {
                writingThread.join();
            } catch (InterruptedException e) {
                out.println("Thread interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            double durationInSeconds = duration / 1000.0;
            out.println("#Random Completed.");
            out.println("#".repeat(25));
            out.println("Write " + amount + "  ||  Speed: " + durationInSeconds + "s");
            out.println("#".repeat(25));
        } else {
            out.println("Operation cancelled.");
        }
    }
    @Override
    public void writeProductsToFile(List<Product> productList , String filename) {
        try (PrintWriter writer = new PrintWriter(new BufferedOutputStream(new FileOutputStream(filename)))) {
            for (Product product : productList) {
                writer.println(product.getCode() + "," + product.getName() + "," + product.getPrice() + "," + product.getQuantity() + "," + product.getDate());
            }
            writer.flush(); // Flush remaining data
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
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
        scanner.nextLine();
    }
    @Override
    public void commitData(List<Product> transactions, List<Product> productList, String filename) {
        synchronized (transactions) {
            for (Product product : productList) {
                transactions.add(product);
            }
            writeProductsToFile(transactions, filename);
            clearData("transaction.txt");
            out.println("Commit Completed");
        }
        transactions.clear();
    }
    @Override
    public void clearData(String fileName){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            writer.write("");
        } catch (IOException e) {
            out.println(e.getMessage());
        }
    }
}

package service;

import methods.MethodForFile;
import methods.MethodForFileImpl;
import model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import util.Pagination;
import util.PaginationImpl;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.lang.System.in;
import static java.lang.System.out;

public class ServiceImpl implements Service {
    static Pagination pagination = new PaginationImpl();
    static MethodForFile method = new MethodForFileImpl();
    static final Scanner scanner = new Scanner(in);
    @Override
    public void createProduct(List<Product> productList) {
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
    public List<Product> readProductsFromFile(String fileName) {
        List<Product> productList = new ArrayList<>();
        long startTime = System.currentTimeMillis(); // Record start time

        Thread fileReadingThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
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
                    } else {
                        System.out.println("Invalid data in file: " + line);
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

        try {
            fileReadingThread.join(); // Wait for the file reading thread to finish
//            animationThread.join(); // Wait for the animation thread to finish
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted: " + e.getMessage());
            Thread.currentThread().interrupt(); // Restore interrupted status
        }

        long endTime = System.currentTimeMillis(); // Record end time
        double totalTimeSeconds = (endTime - startTime) / 1000.0; // Calculate total time taken in seconds
        System.out.println("Total time taken to read products: " + totalTimeSeconds + " seconds");

        // Perform a read operation to ensure data is flushed
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.readLine() != null) { /* Read the file to the end */ }
        } catch (IOException e) {
            System.out.println("Error closing file: " + e.getMessage());
        }

        return productList;
    }
    @Override
    public void viewAllProduct(List<Product> productList) {
        int rowsPerPage = 8;
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
        out.print("Enter product code: ");
        String code = scanner.nextLine();

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
            out.println("Error writing to file: " + e.getMessage());
        }

        out.println("Record deleted successfully.");
    }
    @Override
    public void searchProduct(List<Product> productList){
        Table table = new Table(1, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.SURROUND);
        out.print("Enter product name: ");
        String name = scanner.nextLine();
        for(Product product : productList){
            if (product.getName().contains(name)){
                viewAllProduct(productList);
            }
        }

    }
    @Override
    public void randomProduct(List<Product> productList) {
        out.print("Enter random amount: ");
        int amount = scanner.nextInt();
        out.print("Are you sure you want to random " + amount + " Product? [Y/n]: ");
        char option = scanner.next().charAt(0); // Read the option as a string and get the first character

        if (option == 'Y' || option == 'y') {
            long startTime = System.currentTimeMillis(); // Record start time
            // Generate products
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
            // Create a writing thread
            Thread writingThread = new Thread(() -> writeProductsToFile(productList));
            writingThread.start();

            // Wait for both animation and writing threads to finish
            try {
//                animationThread.join();
                writingThread.join();
            } catch (InterruptedException e) {
                out.println("Thread interrupted: " + e.getMessage());
                Thread.currentThread().interrupt(); // Restore interrupted status
            }

            long endTime = System.currentTimeMillis(); // Record end time
            long duration = endTime - startTime; // Calculate duration
            double durationInSeconds = duration / 1000.0; // Convert milliseconds to seconds

            out.println("############################################");
            out.println("# Products have been randomly generated and written to file.");
            out.println("Write " + amount + " products speed: " + durationInSeconds + "s");
        } else {
            out.println("Operation cancelled.");
        }
    }


    @Override
    public void writeProductsToFile(List<Product> productList) {
        try (PrintWriter writer = new PrintWriter(new BufferedOutputStream(new FileOutputStream("product.txt", true)))) {
            for (Product product : productList) {
                writer.println(product.getCode() + "," + product.getName() + "," + product.getPrice() + "," + product.getQuantity() + "," + product.getDate());
            }
            writer.flush(); // Flush remaining data
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadingAnimation() {
        try {
            // Define the animation characters
            String[] animationChars = { ".", "..", "...", "...." };
            String[] animationDots = { "|", "/", "-", "\\" };
            int timesToRepeat = 10;
            System.out.println("#".repeat(25));

            // Print the loading animation
            for (int i = 0; i < timesToRepeat; i++) {
                System.out.print("\rData is Loading " + animationDots[i % animationDots.length] + animationChars[i % animationChars.length]);
                try {
                    // Add a delay to control the speed of the animation
                    Thread.sleep(120);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
            // Clear the loading animation
            System.out.print("\rData is Loading ");
            System.out.println();
            System.out.println("#".repeat(25));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

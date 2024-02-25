package methods;

import model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import static java.lang.System.*;

public class MethodForFile {
    static final Scanner scanner = new Scanner(in);
    public void createProduct(List<Product> productList){
        Product product = new Product();
        out.print("Enter product code: ");
        product.setCode(scanner.nextLine());
        out.print("Enter product name: ");
        product.setName(scanner.nextLine());
        out.print("Enter product price:");
        product.setPrice(Double.parseDouble(scanner.nextLine()));
        out.print("Enter product quantity:");
        product.setQuantity(Integer.parseInt(scanner.nextLine()));
        product.setDate(LocalDate.now());
        productList.add(product);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("product.txt",true))){
            writer.write(product.getCode()+",");
            writer.write(product.getName()+",");
            writer.write(product.getPrice()+",");
            writer.write(product.getQuantity()+",");
            writer.write(product.getDate()+",");
            writer.newLine();
        }catch (IOException e){
            out.println(e.getMessage());
        }

    }
    public void editProduct(List<Product> productList){
        Table table = new Table(1,BorderStyle.UNICODE_DOUBLE_BOX_WIDE,ShownBorders.SURROUND);
        Product updatedProduct = new Product();
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

                out.print("Enter product name: ");
                product.setName(scanner.nextLine());
                out.print("Enter product price:");
                product.setPrice(scanner.nextDouble());
                out.print("Enter product quantity:");
                product.setQuantity(scanner.nextInt());
                product.setDate(LocalDate.now());
                productList.set(productList.indexOf(product), updatedProduct);
            }
        }

    }
    public List<Product> readProductsFromFile(String fileName) {
        List<Product> productList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Product product = new Product();
                    product.setCode(parts[0].trim());
                    product.setName(parts[1].trim());
                    product.setPrice(Double.parseDouble(parts[2].trim()));
                    product.setQuantity(Integer.parseInt(parts[3].trim()));
                    product.setDate(LocalDate.parse(parts[4].trim())); // Assuming date is stored in ISO_LOCAL_DATE format
                    productList.add(product);
                } else {
                    out.println("Invalid data in file: " + line);
                }
            }
        } catch (IOException | NumberFormatException e) {
            out.println("Error reading file: " + e.getMessage());
        }

        return productList;
    }
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

            currentPage = pageNavigation(currentPage, totalPages);
        } while (currentPage != -1);
    }
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
}

package methods;

import model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static java.lang.System.*;

public class MethodForFile {
    static final Scanner scanner = new Scanner(System.in);
    public void createProduct(List<Product> productList){
        Product product = new Product();
        out.print("Enter product code: ");
        product.setCode(scanner.nextLine());
        out.print("Enter product name: ");
        product.setName(scanner.nextLine());
        out.print("Enter product price:");
        product.setPrice(scanner.nextDouble());
        out.print("Enter product quantity:");
        product.setQuantity(scanner.nextInt());
        product.setDate(LocalDate.now());
        productList.add(product);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("product.txt",true))){
            writer.write(product.getCode()+",");
            writer.write(product.getName()+",");
            writer.write(product.getPrice()+",");
            writer.write(product.getQuantity()+",");
            writer.write(product.getDate()+"\n");
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
                    System.out.println("Invalid data in file: " + line);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return productList;
    }
    public void viewAllProduct(List<Product> productList){
        Table table = new Table(1, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.SURROUND);
        for(Product product : productList){
            table.addCell("Code : "+product.getCode());
            table.addCell("Name : " +product.getName());
            table.addCell("Price : "+product.getPrice());
            table.addCell("Quantity : "+product.getQuantity());
            table.addCell("Date : "+product.getDate());
        }
        out.println(table.render());
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
    public void deleteProduct(List<Product> productList){
        out.print("Enter product code: ");
        String code = scanner.nextLine();
        for(Product product : productList){
            if (product.getCode().equals(code)){
                productList.remove(product);
            }
        }
    }
    public void searchProduct(List<Product> productList){
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
    public static void backUpData(String sourceFilePath, String backupFilePath) {
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
}

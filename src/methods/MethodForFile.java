package methods;

import model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Target;
import java.time.LocalDate;
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
}
import methods.MethodForFile;
import model.Product;
import service.ServiceImpl;
import util.Animation;
import util.UtilTextTable;
import util.JTable;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainTest {
    static String backupDirectory = "backup/";
//    static MethodForFile method = new MethodForFile();
    static  ServiceImpl service = new MethodForFile();
    static List<Product> productList = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Welcome to Stock Management System");
        UtilTextTable util = new UtilTextTable();
        Animation animation = new Animation();
        util.display();
        animation.loadData();
        boolean isTrue = true;
        do {
            JTable jtable = new JTable();
            jtable.displayTable();
            // add Option Input
            Scanner scanner = new Scanner(System.in);
            System.out.print("> Select menu no -> ");
            String option = scanner.nextLine();
            switch (option) {
                case "l", "L" -> {
                    // display code
                    productList = service.readProductsFromFile("product.txt");
                    service.viewAllProduct(productList);
                } case "m","M"-> {
                    //random code
                    service.randomProduct(productList);
                }
                case "w", "W" -> {
                    // write code
                    service.createProduct(productList);
                }
                case "r", "R" -> {
                    // read code
                    service.readOnlyProduct(productList);
                }
                case "e", "E" -> {
                    // edit code
                   service.editProduct(productList);
                }
                case "d", "D" -> {
                    // delete code
                   service.deleteProduct(productList);
                }
                case "s", "S" -> {
                    // search code
                    service.searchProduct(productList);
                }
                case "o", "O" -> {
                    // set row code
                }
                case "c", "C" -> {
                    // commit code
                }
                case "k", "K" -> {
                    service.backUpData();
                }
                case "t", "T" -> {
                    // restore code
                   service.listBackupFiles(backupDirectory);
                }
                case "h", "H" -> {
                    System.out.println();
                    System.out.println("# Help Instruction");
                    JTable tableHelp = new JTable();
                    tableHelp.displayHelpTable();
                }
                case "x", "X" -> {
                    System.out.println();
                    JTable tableExit = new JTable();
                    tableExit.displayExitTable();
                    System.exit(0);
                }
                default -> {
                    // default code
                    System.out.println("Invalid input");
                }
            }
        } while (isTrue);
    }
}
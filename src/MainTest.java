import methods.MethodForFile;
import model.Product;
import util.Animation;
import util.UtilTextTable;
import util.JTable;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static methods.MethodForFile.readProductsFromFile;
import static util.Animation.loadData;

public class MainTest {
    static String backupDirectory = "backup/";
    static MethodForFile method = new MethodForFile();
    static List<Product> productList = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Welcome to Stock Management System");
        UtilTextTable util = new UtilTextTable();
        util.display();
        readProductsFromFile();
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
                    productList = method.readProductsFromFile("product.txt");
                    method.viewAllProduct(productList);
                } case "m","M"-> {
                    //random code
                    method.randomProduct(productList);
                }
                case "w", "W" -> {
                    // write code
                    method.createProduct(productList);
                }
                case "r", "R" -> {
                    // read code
                    method.readOnlyProduct(productList);
                }
                case "e", "E" -> {
                    // edit code
                    method.editProduct(productList);
                }
                case "d", "D" -> {
                    // delete code
                    method.deleteProduct(productList);
                }
                case "s", "S" -> {
                    // search code
                    method.searchProduct(productList);
                }
                case "o", "O" -> {
                    // set row code
                }
                case "c", "C" -> {
                    // commit code
                }
                case "k", "K" -> {
                    // back up code
                    String sourceFilePaths = "product.txt";
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    String timestamp = dateFormat.format(new Date());
                    String backupFileName = "backupfile_" + timestamp + ".csv";
                    String backupFilePath = backupDirectory + backupFileName;

                    System.out.print("Are you sure to Backup [Y/N]: ");
                    String ch = scanner.nextLine();

                    if (ch.equalsIgnoreCase("y")) {
                        method.backUpData(sourceFilePaths, backupFilePath);
                    }
                }
                case "t", "T" -> {
                    // restore code
                    method.listBackupFiles(backupDirectory);
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
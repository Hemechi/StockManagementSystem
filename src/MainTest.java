import methods.MethodForFile;
import methods.MethodForFileImpl;
import model.Product;
import service.Service;
import service.ServiceImpl;
import util.Animation;
import util.AnimationImpl;
import view.Menu;
import view.MenuImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MainTest {
    static String backupDirectory = "backup/";
    static Service service = new ServiceImpl();
    static MethodForFile method = new MethodForFileImpl();
    static Animation animation = new AnimationImpl();
    static Menu menuDisplay = new MenuImpl();
    static List<Product> productList = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Welcome to Stock Management System");
        menuDisplay.displayStyle();
//        service.readProductsFromFile(productList);
        boolean isTrue = true;
        do {
             menuDisplay.displayMenu();
            // add Option Input
            Scanner scanner = new Scanner(System.in);
            System.out.print("> Select menu no -> ");
            String option = scanner.nextLine();
            switch (option) {
                case "l", "L" -> {
                    // display code
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
                    method.backUpData();
                }
                case "t", "T" -> {
                    // restore code
                   method.listBackupFiles(backupDirectory);
                }
                case "h", "H" -> {
                    System.out.println();
                    System.out.println("# Help Instruction");
                    menuDisplay.displayHelpTable();
                }
                case "x", "X" -> {
                    System.out.println();
                    menuDisplay.displayExitTable();
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
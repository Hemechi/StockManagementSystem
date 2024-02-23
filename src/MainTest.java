import util.UtilTextTable;
import util.JTable;

import java.util.Scanner;

public class MainTest {
    public static void main(String[] args) {
        System.out.println("Welcome to Stock Management System");
        UtilTextTable util = new UtilTextTable();
        util.display();
        System.out.println("#".repeat(25));
        System.out.println("Data is loading");
        System.out.println("#".repeat(25));
        boolean isTrue =true;
        do{
            JTable jtable = new JTable();
            jtable.displayTable();
            // add Option Input
            Scanner scanner = new Scanner(System.in);
            System.out.print("> Select menu no -> ");
            String option = scanner.nextLine();
            switch(option){
                case "l","L"-> {
                    //display code
                } case "m","M"-> {
                    //random code
                }
                case "w","W"->{
                    //write code
                }
                case "r","R"->{
                    //read code
                }
                case "e","E"->{
                    //edit code
                }
                case "d","D"->{
                    //delete code
                }
                case "s","S"->{
                    //search code
                }
                case "o","O"->{
                    //set row code
                }
                case "c","C"->{
                    //commit code
                }
                case "k","K"->{
                    //back up code
                }case "t","T"->{
                    //restore code
                }case "h","H"->{
                    System.out.println();
                    System.out.println("# Help Instruction");
                    JTable tableHelp = new JTable();
                    tableHelp.displayHelpTable();
                }
                case "x","X"->{
                    System.out.println();
                    JTable tableExit = new JTable();
                    tableExit.displayExitTable();
                    System.exit(0);
                }
                default -> {
                    //default code
                    System.out.println("Invaid input");
                }
            }
        }while(isTrue);
    }
}
package util;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

public class JTable {
    public void displayTable(){
        System.out.println();
        System.out.println("#".repeat(25));
        System.out.println("# Application Menu");
        Table table = new Table(7, BorderStyle.UNICODE_DOUBLE_BOX_WIDE,ShownBorders.SURROUND);
        table.setColumnWidth(0,15,25);
        table.setColumnWidth(1, 15,25);
        table.setColumnWidth(2,15,25);
        table.setColumnWidth(3,15,25);
        table.setColumnWidth(4,15,25);
        table.setColumnWidth(5,15,25);
        table.setColumnWidth(6,15,25);
        table.setColumnWidth(0,15,25);
        table.setColumnWidth(1,15,25);
        table.setColumnWidth(2, 15,25);
        table.setColumnWidth(3,15,25);
        table.setColumnWidth(4,15,25);
        table.setColumnWidth(5,15,25);
        table.setColumnWidth(6,15,25);
        table.addCell("Disp(l)ay");
        table.addCell("| Rando(m)");
        table.addCell("| W)rite");
        table.addCell("| R)ead");
        table.addCell("| (E)dit");
        table.addCell("| D)elete");
        table.addCell("| S)earch");
        table.addCell("Set r(o)w");
        table.addCell("| (C)ommit");
        table.addCell("| Bac(k) up");
        table.addCell("| Res(t)ore");
        table.addCell("| (H)elp");
        table.addCell("| E(x)it");
        System.out.println(table.render());

    }
    public void displayExitTable(){
        Table tableExit = new Table(9, BorderStyle.DESIGN_CAFE);
        tableExit.addCell("               GOOD BYE!         ");
        System.out.println(tableExit.render());
    }
    public void displayHelpTable(){
    }

}


package methods;

import model.Product;

import java.util.List;

public interface MethodForFile {
    void writeProductsToFile(List<Product> productList, String filename);
    void updateProductInFile(Product product);
    void restoreData(String sourceFilePath, String backupDirectory, int fileNumber);
    void listBackupFiles(String backupDirectory);
    void backUpData();
    void Commit(List<Product> productList);
}

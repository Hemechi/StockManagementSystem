package service;

import model.Product;

import java.util.List;

public interface ServiceImpl {
    void viewAllProduct(List<Product> productList);
    void createProduct(List<Product> productList);
     List<Product> readProductsFromFile(String fileName);
     void randomProduct(List<Product> productList);
     void readOnlyProduct(List<Product> productList);
     void editProduct(List<Product> productList);
     void deleteProduct(List<Product> productList);
     void searchProduct(List<Product> productList);
     void backUpData();
     void listBackupFiles(String backupDirectory);
}

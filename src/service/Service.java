package service;

import model.Product;

import java.util.List;
import java.util.Scanner;

public interface Service {
    void createProduct(List<Product> productList);
    void readOnlyProduct(List<Product> productList);
    void randomProduct(List<Product> productList, String filename);
    void editProduct(List<Product> productList);
    void deleteProduct(List<Product> productList);
    void searchProduct(List<Product> productList);
    void readProductsFromFile(List<Product> productList);
    void writeProductsToFile(List<Product> productList,String filename);
    void viewAllProduct(List<Product> productList);
    void setRowsPerPage(Scanner scanner);
    void commitData(List<Product> Transactions , List<Product> productList  ,String filename);

}

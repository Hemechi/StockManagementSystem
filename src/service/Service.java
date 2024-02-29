package service;

import model.Product;

import java.util.List;
import java.util.Scanner;

public interface Service {
    void createProduct(List<Product> productList);
    void readOnlyProduct(List<Product> productList);
    void randomProduct(List<Product> productList);
    void editProduct(List<Product> productList);
    void deleteProduct(List<Product> productList);
    void searchProduct(List<Product> productList);
    List<Product> readProductsFromFile(List<Product> productList, String fileName);
    void writeProductsToFile(List<Product> productList);
    void viewAllProduct(List<Product> productList);
    void setRowsPerPage(Scanner scanner);
}

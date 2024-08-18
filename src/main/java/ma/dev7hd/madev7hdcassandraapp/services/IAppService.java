package ma.dev7hd.madev7hdcassandraapp.services;

import ma.dev7hd.madev7hdcassandraapp.entities.Category;
import ma.dev7hd.madev7hdcassandraapp.entities.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface IAppService {
    ResponseEntity<?> newProduct(Product product);

    List<Product> getAllProducts();

    ResponseEntity<?> getProductByName(String name);

    ResponseEntity<Product> getProductById(UUID id);

    ResponseEntity<?> getProductsByCategoryName(String categoryName);

    ResponseEntity<String> deleteProductById(UUID id);

    ResponseEntity<Product> updateProduct(Product product);

    ResponseEntity<String> deleteCategoryById(UUID categoryId);

    ResponseEntity<String> deleteCategoryByName(String categoryName);

    ResponseEntity<String> deleteProductsByCategoryId(UUID categoryId);

    ResponseEntity<String> deleteProductsByCategoryName(String categoryName);

    ResponseEntity<List<Product>> getProductsByPriceRange(double minPrice, double maxPrice);

    ResponseEntity<Category> getCategoryByName(String name);

    ResponseEntity<?> addCategory(String categoryName);

    ResponseEntity<?> updateCategory(UUID id, String categoryName);

    List<Category> getAllCategories();
}

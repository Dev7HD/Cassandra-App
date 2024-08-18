package ma.dev7hd.madev7hdcassandraapp.services;

import lombok.AllArgsConstructor;
import ma.dev7hd.madev7hdcassandraapp.entities.Category;
import ma.dev7hd.madev7hdcassandraapp.entities.Product;
import ma.dev7hd.madev7hdcassandraapp.metier.IMetier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppService implements IAppService {
    private IMetier metier;

    @Override
    public ResponseEntity<?> newProduct(Product product) {
        return metier.addProduct(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return metier.getAllProducts();
    }

    @Override
    public ResponseEntity<?> getProductByName(String name) {
        return metier.getProductsByName(name);
    }

    @Override
    public ResponseEntity<Product> getProductById(UUID id) {
        return metier.getProductById(id);
    }

    @Override
    public ResponseEntity<?> getProductsByCategoryName(String categoryName) {
        return metier.getProductsByCategoryName(categoryName);
    }

    @Override
    public ResponseEntity<String> deleteProductById(UUID id) {
        return metier.deleteProductById(id);
    }

    @Override
    public ResponseEntity<Product> updateProduct(Product product) {
        return metier.updateProduct(product);
    }

    @Override
    public ResponseEntity<String> deleteCategoryById(UUID categoryId) {
        return metier.deleteCategoryById(categoryId);
    }

    @Override
    public ResponseEntity<String> deleteCategoryByName(String categoryName) {
        return metier.deleteCategoryByName(categoryName);
    }

    @Override
    public ResponseEntity<String> deleteProductsByCategoryId(UUID categoryId) {
        return metier.deleteProductsByCategoryId(categoryId);
    }

    @Override
    public ResponseEntity<String> deleteProductsByCategoryName(String categoryName) {
        return metier.deleteProductsByCategoryName(categoryName);
    }

    @Override
    public ResponseEntity<List<Product>> getProductsByPriceRange(double minPrice, double maxPrice) {
        return metier.getProductsByPriceRange(minPrice, maxPrice);
    }

    @Override
    public ResponseEntity<Category> getCategoryByName(String name) {
        return metier.getCategoryByName(name);
    }

    @Override
    public ResponseEntity<?> addCategory(String categoryName) {
        return metier.addCategory(categoryName);
    }

    @Override
    public ResponseEntity<?> updateCategory(UUID id, String categoryName) {
        return metier.updateCategory(id, categoryName);
    }

    @Override
    public List<Category> getAllCategories(){
        return metier.getAllCategories();
    }
}

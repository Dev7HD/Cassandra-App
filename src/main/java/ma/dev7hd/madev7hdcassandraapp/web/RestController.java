package ma.dev7hd.madev7hdcassandraapp.web;

import lombok.AllArgsConstructor;
import ma.dev7hd.madev7hdcassandraapp.entities.Category;
import ma.dev7hd.madev7hdcassandraapp.entities.Product;
import ma.dev7hd.madev7hdcassandraapp.services.IAppService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin("*")
@AllArgsConstructor
public class RestController {
    private IAppService appService;

    @PostMapping("/products/new")
    public ResponseEntity<?> newProduct(@RequestBody Product product){
        return appService.newProduct(product);
    }

    @GetMapping("/products/all")
    public List<Product> getAllProducts(){
        return appService.getAllProducts();
    }

    @GetMapping("/products/category/{categoryName}")
    public ResponseEntity<?> getAllProductsByCategoryName(@PathVariable String categoryName){
        return appService.getProductsByCategoryName(categoryName);
    }

    @GetMapping("/product/name/{name}")
    public ResponseEntity<?> getProductByName(@PathVariable String name){
        return appService.getProductByName(name);
    }

    @GetMapping("/product/id/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable UUID id) {
        return appService.getProductById(id);
    }

    @DeleteMapping("/product/id/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable UUID id) {
        return appService.deleteProductById(id);
    }

    @PutMapping("/product/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        return appService.updateProduct(product);
    }

    @DeleteMapping("/category/id/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable(name = "id") UUID categoryId){
        return appService.deleteCategoryById(categoryId);
    }

    @DeleteMapping("/category/name/{name}")
    public ResponseEntity<String> deleteCategoryByName(@PathVariable(name = "name") String categoryName){
        return appService.deleteCategoryByName(categoryName);
    }

    @DeleteMapping("/products/category/id/{id}")
    public ResponseEntity<String> deleteProductsByCategoryId(@PathVariable(name = "id") UUID categoryId){
        return appService.deleteProductsByCategoryId(categoryId);
    }

    @GetMapping("/products/price")
    public ResponseEntity<List<Product>> getProductsByPriceRange(double minPrice, double maxPrice){
        return appService.getProductsByPriceRange(minPrice, maxPrice);
    }

    @GetMapping("/category")
    public ResponseEntity<Category> getCategoryByName(String name){
        return appService.getCategoryByName(name);
    }

    @PostMapping("category/new")
    public ResponseEntity<?> addCategory(String categoryName){
        return appService.addCategory(categoryName);
    }

    @PutMapping("/category/update")
    public ResponseEntity<?> updateCategory(UUID id, String newName){
        return appService.updateCategory(id, newName);
    }

    @GetMapping("/category/all")
    public List<Category> getAllCategories(){
        return appService.getAllCategories();
    }

    @DeleteMapping("/products/category/name/{name}")
    public ResponseEntity<String> deleteProductsByCategoryName(@PathVariable(name = "name") String categoryName){
        return appService.deleteProductsByCategoryName(categoryName);
    }
}

package ma.dev7hd.madev7hdcassandraapp.metier;

import lombok.extern.slf4j.Slf4j;
import ma.dev7hd.madev7hdcassandraapp.entities.Category;
import ma.dev7hd.madev7hdcassandraapp.entities.Product;
import ma.dev7hd.madev7hdcassandraapp.repositories.CategoryRepository;
import ma.dev7hd.madev7hdcassandraapp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class MetierImpl implements IMetier {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public MetierImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<?> addProduct(Product product) {
        if (product == null || product.getId() != null || product.getName() == null || product.getName().isEmpty() || product.getDescription() == null || product.getDescription().isEmpty() || product.getPrice() <= 0 || product.getCategoryName() == null || product.getCategoryName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            if (!categoryRepository.existsByNameIgnoreCase(product.getCategoryName().trim().toLowerCase())) {
                return ResponseEntity.badRequest().body("Category does not exists");
            } else {
                product.setId(UUID.randomUUID());
                product.setName(product.getName().trim().toLowerCase());
                product.setDescription(product.getDescription().trim().toLowerCase());
                product.setCategoryName(product.getCategoryName().trim().toLowerCase());
                Product savedProduct = productRepository.insert(product);
                return ResponseEntity.ok(savedProduct);
            }
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public ResponseEntity<?> getProductsByName(String name) {
        Optional<List<Product>> products = productRepository.findAllByName(name);
        if (products.isPresent()) {
            return ResponseEntity.ok(products.get());
        } else {
            return ResponseEntity.badRequest().body("Product not found");
        }
    }

    @Override
    public ResponseEntity<Product> getProductById(UUID id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<?> getProductsByCategoryName(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Category is null");
        }
        categoryName = categoryName.trim().toLowerCase();
        Optional<Category> optionalCategory = categoryRepository.findByName(categoryName);
        if (optionalCategory.isEmpty()) {
            return ResponseEntity.badRequest().body("Category not found");
        } else {
            Category cat = optionalCategory.get();
            Optional<List<Product>> products = productRepository.findAllByCategoryName(cat.getName());
            return products.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }
    }

    @Override
    public ResponseEntity<String> deleteProductById(UUID id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
            return ResponseEntity.ok("Product deleted successfully");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Product> updateProduct(Product product) {
        if (product == null || product.getId() == null || product.getId().toString().isEmpty() || product.getName() == null || product.getName().isEmpty() || product.getDescription() == null || product.getDescription().isEmpty() || product.getPrice() <= 0 || !categoryRepository.existsByNameIgnoreCase(product.getCategoryName().trim().toLowerCase())|| !productRepository.existsById(product.getId())) {
            return ResponseEntity.badRequest().build();
        }

        product.setCategoryName(product.getCategoryName().trim().toLowerCase());
        Product updatedProduct = productRepository.save(product);
        return ResponseEntity.ok(updatedProduct);

    }

    @Override
    public ResponseEntity<String> deleteCategoryById(UUID categoryId) {
        if (categoryId == null) {
            return ResponseEntity.badRequest().body("ID Error");
        } else {
            Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
            if (optionalCategory.isPresent()) {
                String catName = optionalCategory.get().getName();
                Optional<List<Product>> products = productRepository.findAllByCategoryName(catName);
                products.ifPresent(productRepository::deleteAll);
                categoryRepository.deleteById(categoryId);
                return ResponseEntity.ok("Category deleted successfully");
            } else {
                return ResponseEntity.badRequest().body("Category not found");
            }

        }
    }

    @Override
    public ResponseEntity<String> deleteCategoryByName(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Name Error");
        } else {
            Optional<Category> optionalCategory = categoryRepository.findByName(categoryName);
            if (optionalCategory.isPresent()) {
                String catName = optionalCategory.get().getName();
                Optional<List<Product>> products = productRepository.findAllByCategoryName(catName);
                products.ifPresent(productRepository::deleteAll);
                categoryRepository.delete(optionalCategory.get());
                return ResponseEntity.ok("Category deleted successfully");
            } else {
                return ResponseEntity.badRequest().body("Category not found");
            }
        }
    }

    @Override
    public ResponseEntity<String> deleteProductsByCategoryId(UUID categoryId) {
        if (categoryId == null) {
            return ResponseEntity.badRequest().body("ID Error");
        } else {
            Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
            return deleteProductsByCategory(optionalCategory);
        }
    }

    @Override
    public ResponseEntity<String> deleteProductsByCategoryName(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("ID Error");
        } else {
            Optional<Category> optionalCategory = categoryRepository.findByName(categoryName);
            return deleteProductsByCategory(optionalCategory);
        }
    }

    private ResponseEntity<String> deleteProductsByCategory(Optional<Category> optionalCategory) {
        if (optionalCategory.isPresent()) {
            Optional<List<Product>> products = productRepository.findAllByCategoryName(optionalCategory.get().getName());
            products.ifPresent(productRepository::deleteAll);
            return ResponseEntity.ok("Products deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("Category not found");
        }
    }

    @Override
    public ResponseEntity<List<Product>> getProductsByPriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0 || minPrice > maxPrice) {
            return ResponseEntity.badRequest().build();
        } else {
            Optional<List<Product>> optionalProductList = productRepository.findByPriceIsBetween(minPrice-1, maxPrice+1);
            return optionalProductList.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }
    }

    @Override
    public ResponseEntity<Category> getCategoryByName(String name) {
        if (name == null || name.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            Optional<Category> category = categoryRepository.findByName(name);
            return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }
    }

    @Override
    public ResponseEntity<?> addCategory(String categoryName) {
        if (categoryName == null || categoryName.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        String catName = categoryName.trim().toLowerCase();
        boolean existsedByName = categoryRepository.existsByNameIgnoreCase(catName);
        if (existsedByName) {
            return ResponseEntity.badRequest().body("Category already exist");
        } else {
            Category newCat = new Category();
            newCat.setId(UUID.randomUUID());
            newCat.setName(catName);
            Category savedCategory = categoryRepository.insert(newCat);
            return ResponseEntity.ok(savedCategory);
        }
    }

    @Override
    public ResponseEntity<?> updateCategory(UUID id, String newCategoryName) {
        if (newCategoryName == null || newCategoryName.isEmpty() || id == null) {
            return ResponseEntity.badRequest().build();
        }
        String newName = newCategoryName.trim().toLowerCase();
        boolean existByName = categoryRepository.existsByNameIgnoreCase(newName);
        if (existByName) {
            return ResponseEntity.badRequest().body("Category already exist");
        }

        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            Optional<List<Product>> productList = productRepository.findAllByCategoryName(category.getName().trim().toLowerCase());
            category.setName(newName);
            Category updatedCategory = categoryRepository.save(category);
            productList.ifPresent(products -> products.forEach(product -> {
                product.setCategoryName(category.getName());
                productRepository.save(product);
            }));
            return ResponseEntity.ok(updatedCategory);
        } else {
            return ResponseEntity.badRequest().body("Id is incorrect");
        }
    }
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}

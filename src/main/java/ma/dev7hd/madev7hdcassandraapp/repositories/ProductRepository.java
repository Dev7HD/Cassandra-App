package ma.dev7hd.madev7hdcassandraapp.repositories;

import ma.dev7hd.madev7hdcassandraapp.entities.Product;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends CassandraRepository<Product, UUID> {
    Optional<List<Product>> findAllByName(String name);
    Optional<List<Product>> findAllByCategoryName(String categoryName);
    void deleteAllByCategoryName(String categoryName);
    @AllowFiltering
    Optional<List<Product>> findByPriceIsBetween(double minPrice, double maxPrice);
}

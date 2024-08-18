package ma.dev7hd.madev7hdcassandraapp.repositories;

import ma.dev7hd.madev7hdcassandraapp.entities.Category;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends CassandraRepository<Category, UUID> {
    Optional<Category> findByName(String name);
    Optional<Category> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}

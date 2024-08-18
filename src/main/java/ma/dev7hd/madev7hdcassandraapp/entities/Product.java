package ma.dev7hd.madev7hdcassandraapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table("products")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    @PrimaryKey
    private UUID id;
    @Indexed
    private String name;
    private String description;
    @Indexed
    private double price;
    @Indexed
    private String categoryName;
}

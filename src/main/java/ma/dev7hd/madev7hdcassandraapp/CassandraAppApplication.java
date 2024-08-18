package ma.dev7hd.madev7hdcassandraapp;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CassandraAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CassandraAppApplication.class, args);
    }

    /*@Bean
    public CqlSession cqlSession() {
        return CqlSession.builder()
                .withKeyspace("my_keyspace")
                .build();
    }*/

}

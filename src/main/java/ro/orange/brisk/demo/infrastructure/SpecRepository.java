package ro.orange.brisk.demo.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.orange.brisk.core.IPersistentSpec;

public interface SpecRepository extends MongoRepository<IPersistentSpec, String> {
}

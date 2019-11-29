package services.ravi.spring5webfluxrest.respositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import services.ravi.spring5webfluxrest.domain.Vendor;

public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {
}

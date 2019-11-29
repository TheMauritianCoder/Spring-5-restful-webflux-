package services.ravi.spring5webfluxrest.respositories;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import services.ravi.spring5webfluxrest.domain.Category;

public interface CategoryRespository extends ReactiveMongoRepository<Category,String> {

}

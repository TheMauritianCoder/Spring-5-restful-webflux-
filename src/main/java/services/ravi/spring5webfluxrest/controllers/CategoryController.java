package services.ravi.spring5webfluxrest.controllers;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import services.ravi.spring5webfluxrest.domain.Category;
import services.ravi.spring5webfluxrest.respositories.CategoryRespository;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryRespository categoryRespository;

    public CategoryController(CategoryRespository categoryRespository) {
        this.categoryRespository = categoryRespository;
    }

    @GetMapping
    Flux<Category> list(){
        return categoryRespository.findAll();
    }

    @GetMapping("/{id}")
    Mono<Category> getById(@PathVariable String id){
        return categoryRespository.findById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> createCategory(@RequestBody Publisher<Category> categoryStream){
        return categoryRespository.saveAll(categoryStream).then();
    }

    @PutMapping("/{id}")
    Mono<Category> update(@PathVariable  String id, @RequestBody Category category){
        category.setId(id);
        return categoryRespository.save(category);
    }

    @PatchMapping("/{id}")
    Mono<Category> patch(@PathVariable  String id, @RequestBody Category category){

        Category foundCategory = categoryRespository.findById(id).block();

        if(foundCategory.getDescription() != category.getDescription()){
            foundCategory.setDescription(category.getDescription());
            return categoryRespository.save(foundCategory);
        }

        return Mono.just(foundCategory);
    }

}

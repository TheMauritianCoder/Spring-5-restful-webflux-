package services.ravi.spring5webfluxrest.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import services.ravi.spring5webfluxrest.domain.Category;
import services.ravi.spring5webfluxrest.respositories.CategoryRespository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class CategoryControllerTest {

    WebTestClient webTestClient;
    @Mock
    CategoryRespository categoryRespository;
    CategoryController categoryController;

    @Before
    public void setUp() throws Exception {
        categoryRespository = Mockito.mock(CategoryRespository.class);
        categoryController = new CategoryController(categoryRespository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    public void list() {
        BDDMockito.given(categoryRespository.findAll())
                .willReturn(
                        Flux.just(Category.builder().description("Cat1").build(),Category.builder().description("Cat2").build())
                );

        webTestClient.get()
                        .uri("/api/v1/categories")
                        .exchange()
                        .expectBodyList(Category.class)
                        .hasSize(2);
    }

    @Test
    public void getById() {
        BDDMockito.given(categoryRespository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().description("Cat1").build()));

        webTestClient.get()
                .uri("/api/v1/categories/111")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    public void createCategory() {
        BDDMockito.given(categoryRespository.saveAll(any(Publisher.class))).willReturn(Flux.just(Category.builder().build()));

        Mono<Category> catToSaveMono = Mono.just(Category.builder().description("Some Category").build());

        webTestClient.post()
                .uri("/api/v1/categories")
                .body(catToSaveMono,Category.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void putCategrory() {
        BDDMockito.given(categoryRespository.save(any(Category.class))).willReturn(Mono.just(Category.builder().build()));

        Mono<Category> catToSaveMono = Mono.just(Category.builder().description("Some Category").build());

        webTestClient.put()
                .uri("/api/v1/categories/111")
                .body(catToSaveMono,Category.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void patch() {

        BDDMockito.given(categoryRespository.findById(anyString())).willReturn(Mono.just(Category.builder().build()));

        BDDMockito.given(categoryRespository.save(any(Category.class))).willReturn(Mono.just(Category.builder().build()));

        Mono<Category> catToSaveMono = Mono.just(Category.builder().description("Some Category").build());

        webTestClient.patch()
                .uri("/api/v1/categories/111")
                .body(catToSaveMono,Category.class)
                .exchange()
                .expectStatus().isOk();

        BDDMockito.verify(categoryRespository).save(any());
    }
}
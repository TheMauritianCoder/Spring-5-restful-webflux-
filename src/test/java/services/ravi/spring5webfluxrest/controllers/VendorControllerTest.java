package services.ravi.spring5webfluxrest.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import services.ravi.spring5webfluxrest.domain.Category;
import services.ravi.spring5webfluxrest.domain.Vendor;
import services.ravi.spring5webfluxrest.respositories.VendorRepository;

import static org.hamcrest.Matchers.any;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;

public class VendorControllerTest {

    VendorRepository vendorRepository;
    VendorController vendorController;
    WebTestClient webTestClient;

    @Before
    public void setUp() throws Exception {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    public void getAll() {
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().build(), Vendor.builder().build()));

        webTestClient.get().uri("/api/v1/vendors").exchange().expectBodyList(Vendor.class).hasSize(2);

    }

    @Test
    public void getById() {
        BDDMockito.given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(Vendor.builder().build()));

        webTestClient.get().uri("/api/v1/vendors/1").exchange().expectBody(Vendor.class);
    }

    @Test
    public void postVendor(){
        BDDMockito.given(vendorRepository.saveAll(ArgumentMatchers.any(Publisher.class))).willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> vendorToSave = Mono.just(Vendor.builder().firstname("Ravi").lastname("Kowlessur").build());

        webTestClient.post()
                .uri("/api/v1/vendors")
                .body(vendorToSave,Category.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void putVendor() {
        BDDMockito.given(vendorRepository.save(ArgumentMatchers.any(Vendor.class))).willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorToSave = Mono.just(Vendor.builder().firstname("Ravi").lastname("Kowlessur").build());

        webTestClient.put()
                .uri("/api/v1/vendors/111")
                .body(vendorToSave,Category.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void patch() {

        BDDMockito.given(vendorRepository.findById(anyString())).willReturn(Mono.just(Vendor.builder().build()));

        BDDMockito.given(vendorRepository.save(ArgumentMatchers.any(Vendor.class))).willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorToSave = Mono.just(Vendor.builder().firstname("Ravi").lastname("Kowlessur").build());

        webTestClient.patch()
                .uri("/api/v1/vendors/111")
                .body(vendorToSave,Category.class)
                .exchange()
                .expectStatus().isOk();

        BDDMockito.verify(vendorRepository).save(ArgumentMatchers.any());
    }

}
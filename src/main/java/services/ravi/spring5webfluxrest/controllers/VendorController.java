package services.ravi.spring5webfluxrest.controllers;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import services.ravi.spring5webfluxrest.domain.Category;
import services.ravi.spring5webfluxrest.domain.Vendor;
import services.ravi.spring5webfluxrest.respositories.VendorRepository;

@RestController
@RequestMapping("/api/v1/vendors")
public class VendorController {

    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping
    public Flux<Vendor> getAll(){
        return vendorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Vendor> getById(@PathVariable String id){
        return vendorRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> postVendor(@RequestBody Publisher<Vendor> publisher){
        return vendorRepository.saveAll(publisher).then();
    }

    @PutMapping("/{id}")
    Mono<Vendor> putVendor(@PathVariable String id, @RequestBody Vendor vendor){
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }


    @PatchMapping("/{id}")
    Mono<Vendor> patch(@PathVariable  String id, @RequestBody Vendor vendor){

        Vendor foundVendor = vendorRepository.findById(id).block();

        if(foundVendor.getFirstname() != vendor.getFirstname()){
            foundVendor.setFirstname(vendor.getFirstname());
        }

        if(foundVendor.getLastname() != vendor.getLastname()){
            foundVendor.setLastname(vendor.getLastname());
        }

        foundVendor = vendorRepository.save(foundVendor).block();

        return Mono.just(foundVendor);
    }


}

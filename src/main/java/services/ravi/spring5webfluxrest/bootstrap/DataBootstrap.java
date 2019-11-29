package services.ravi.spring5webfluxrest.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import services.ravi.spring5webfluxrest.domain.Category;
import services.ravi.spring5webfluxrest.domain.Vendor;
import services.ravi.spring5webfluxrest.respositories.CategoryRespository;
import services.ravi.spring5webfluxrest.respositories.VendorRepository;

@Component
public class DataBootstrap implements CommandLineRunner {

    private final CategoryRespository categoryRespository;
    private final VendorRepository vendorRepository;

    public DataBootstrap(CategoryRespository categoryRespository, VendorRepository vendorRepository) {
        this.categoryRespository = categoryRespository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(categoryRespository.count().block() == 0){
            System.out.println("Loading Data for Category...");
            categoryRespository.save(Category.builder().description("Fruits").build()).block();
            categoryRespository.save(Category.builder().description("Nuts").build()).block();
            categoryRespository.save(Category.builder().description("Breads").build()).block();
            categoryRespository.save(Category.builder().description("Meats").build()).block();
            categoryRespository.save(Category.builder().description("Eggs").build()).block();
            System.out.println("Categories Loaded..."+categoryRespository.count().block());
        }

        if(vendorRepository.count().block() == 0){
            System.out.println("Loading Data for Vendors...");
            vendorRepository.save(Vendor.builder().firstname("Joe").lastname("Buck").build()).block();
            vendorRepository.save(Vendor.builder().firstname("Michael").lastname("Weston").build()).block();
            vendorRepository.save(Vendor.builder().firstname("Jessie").lastname("Waters").build()).block();
            vendorRepository.save(Vendor.builder().firstname("Bill").lastname("Nershil").build()).block();
            vendorRepository.save(Vendor.builder().firstname("Jimmy").lastname("Buffet").build()).block();
            System.out.println("Vendors Loaded..."+vendorRepository.count().block());
        }


    }
}

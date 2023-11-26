package com.bootcamp.robotikka.robotikkaapi.repo;

//import com.bootcamp.robotikka.robotikkaapi.dto.paginated.converter.ProductConverter;
import com.bootcamp.robotikka.robotikkaapi.dto.paginated.converter.ProductConverter;
import com.bootcamp.robotikka.robotikkaapi.entity.Product;
import com.bootcamp.robotikka.robotikkaapi.entity.ProductImages;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import java.awt.print.Pageable;
import java.util.List;

@EnableJpaRepositories
@Repository
public interface ProductRepo extends JpaRepository<Product,String> {
    @Query(
           value = "SELECT property_id AS propertyId, description, display_name AS displayName, qty, selling_price AS sellingPrice, unit_price AS unitPrice FROM product_table",
           nativeQuery = true
    )
    public List<ProductConverter> findAllProductsWithPaginate(String text, PageRequest pageable);
    @Query(
            value = "SELECT COUNT(*) FROM product_table",
            nativeQuery = true
    )
    public long findAllProductsCount(String text);


//    @Query(
//            value = "SELECT property_id AS propertyId, description, display_name AS displayName, qty, selling_price AS sellingPrice, unit_price AS unitPrice FROM product_table WHERE display_name LIKE %?1%",
//            nativeQuery = true
//    )
//    public List<ProductConverter> findAllProductsWithPaginate(String text, Pageable pageable);
//    @Query(
//            value = "SELECT COUNT(*) FROM product_table WHERE display_name LIKE %?1%",
//            nativeQuery = true
//    )
//    public long findAllProductsCount(String text);
//
//    @Query(
//            value = "SELECT * FROM product_image WHERE product_property_id=?",
//            nativeQuery = true
//    )
//    public List<ProductImages> findAll(String id);
}

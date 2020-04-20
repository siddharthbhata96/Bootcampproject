package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.dto.ProductUpdateDto;
import com.ecommerce.Ecommerce.entities.order.Cart;
import com.ecommerce.Ecommerce.entities.product.*;
import com.ecommerce.Ecommerce.entities.registration.Seller;
import com.ecommerce.Ecommerce.services.ProductDaoService;
import com.ecommerce.Ecommerce.services.UserDaoService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.aspectj.bridge.IMessage;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    @Autowired
    private ProductDaoService data;

    @Autowired
    private UserDaoService userDaoService;

    @PostMapping("/seller/productSave/{category_name}")
    public String saveProducts(@RequestBody List<Product> product,@PathVariable(value="category_name")String category_name)
    {
        Seller seller = userDaoService.getLoggedInSeller();
        Integer seller_user_id = seller.getId();
        String save= data.createProduct(seller_user_id, product, category_name);
        return save;
    }


    //View All product
    public List<Product> findSellerwiseProducts(Integer sellerid){
        return data.findSellerProducts(sellerid);
    }
    @GetMapping("/sellerProduct/viewAll")
    public MappingJacksonValue retrieveSellerProducts() {
        Seller seller = userDaoService.getLoggedInSeller();
        Integer sellerid = seller.getId();

        SimpleBeanPropertyFilter filter1 = SimpleBeanPropertyFilter.filterOutAllExcept("productName","brand",
                "productDescription","isCancellable","isReturnable","variations","category");

        FilterProvider filterProvider1 = new SimpleFilterProvider().addFilter("productfilter",filter1);

        MappingJacksonValue mapping1 = new MappingJacksonValue(findSellerwiseProducts(sellerid));

        mapping1.setFilters(filterProvider1);

        return mapping1;
    }

    //View product ById
    public Product viewProduct(Long product_id) {
        return data.findProduct(product_id);
    }

    @GetMapping("sellerProduct/{product_id}")
    public MappingJacksonValue retrieveProduct(@PathVariable Long product_id) {
        SimpleBeanPropertyFilter filter2 = SimpleBeanPropertyFilter.filterOutAllExcept("productName","brand",
                "productDescription","isCancellable","isReturnable","category");

        FilterProvider filterProvider2 = new SimpleFilterProvider().addFilter("productfilter",filter2);

        MappingJacksonValue mapping2=new MappingJacksonValue(viewProduct(product_id));
        mapping2.setFilters(filterProvider2);

        return mapping2;
    }

    @PutMapping("/seller/deleteProduct/{pid}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long pid) {

        Seller seller = userDaoService.getLoggedInSeller();
        Integer sellerid = seller.getId();

        String message = data.deleteProduct(pid, sellerid);

        return  new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PutMapping("/seller/updateproduct/{pid}")
    public ResponseEntity<Object> updateProductDetails(@RequestBody ProductUpdateDto productUpdateDto, @PathVariable Long pid){
        Seller seller = userDaoService.getLoggedInSeller();
        Integer sellerid = seller.getId();
        String message = data.updateProduct(productUpdateDto, pid, sellerid);

        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    public List<Product> findCategorywiseProducts(String category_name){
        return data.findCategoryProducts(category_name);
    }

    @GetMapping("/products/{category_name}")
    public MappingJacksonValue retrieveProductList(@PathVariable String category_name) {

        SimpleBeanPropertyFilter filter6 = SimpleBeanPropertyFilter.filterOutAllExcept("productName","brand",
                "productDescription","isCancellable","isReturnable");

        FilterProvider filterProvider6 = new SimpleFilterProvider().addFilter("productfilter",filter6);

        MappingJacksonValue mapping6 = new MappingJacksonValue(findCategorywiseProducts(category_name));

        mapping6.setFilters(filterProvider6);

        return mapping6;
    }

}

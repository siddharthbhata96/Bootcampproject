package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.dto.ProductVariationUpdateDto;
import com.ecommerce.Ecommerce.entities.product.ProductVariation;
import com.ecommerce.Ecommerce.services.ProductVariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductVariationController {

    @Autowired
    ProductVariationService data;

    @PostMapping("/productSave/variation/{product_id}")
    public String saveProductVariation(@PathVariable(value="product_id")Long product_id, @RequestBody List<ProductVariation> product_variations)
    {
        String product_variations1=data.createProductVariation(product_id,product_variations);

        return product_variations1;
    }

    @GetMapping("/productVariationDisplay/{Id}")
    public ProductVariation viewVariant(@PathVariable Integer Id) {
        return data.displayProductVariation(Id);
    }

    @PutMapping("/seller/updateproduct/variant/{vid}")
    public ResponseEntity<Object> updateProductVariant(@RequestBody ProductVariationUpdateDto productVariationUpdateDto, @PathVariable Integer vid){
        String message = data.updateProductVariation(productVariationUpdateDto, vid);

        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

}

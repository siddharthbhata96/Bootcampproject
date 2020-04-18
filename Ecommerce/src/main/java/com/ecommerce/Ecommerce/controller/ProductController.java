package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.entities.order.Cart;
import com.ecommerce.Ecommerce.entities.product.*;
import com.ecommerce.Ecommerce.entities.registration.Seller;
import com.ecommerce.Ecommerce.services.ProductDaoService;
import com.ecommerce.Ecommerce.services.UserDaoService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/productSave/variation/{product_id}")
    public void saveProductVariation(@PathVariable(value="product_id")Long product_id, @RequestBody List<ProductVariation> product_variations)
    {
        List<ProductVariation>product_variations1=data.createProductVariation(product_id,product_variations);
    }

   /* @PostMapping("{customer_user_id}/add-to-cart/{productVariation_id}")
    public void addToCart(@PathVariable Integer customer_user_id, @RequestBody Cart cart, @PathVariable Integer productVariation_id){
        Cart cart1= data.addToCart(customer_user_id, cart, productVariation_id);
    }*/
}

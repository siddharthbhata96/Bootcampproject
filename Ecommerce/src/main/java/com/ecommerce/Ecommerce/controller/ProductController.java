package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.entities.Product_Details.*;
import com.ecommerce.Ecommerce.services.ProductDaoService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
public class ProductController {
    @Autowired
    private ProductDaoService data;


    ObjectMapper objectMapper=new ObjectMapper();
    @PostMapping("admin/category")
    public String createCategory(@RequestBody Category category)
    {
        String a=data.categorySave(category);
        return a;
    }
    @PostMapping("/admin/category/{parent_id}")
    public String createCategoryParent(@PathVariable(value="parent_id")Integer parent_id ,@RequestBody Category category)
    {
        String b=data.categoryParentSave(parent_id,category);
        return b;
    }

    @GetMapping("list/category")
    public List<Category>findAllCategory()
    {
        return data.retrieveAllCategory();
    }

    @PostMapping("/{seller_user_id}/productSave/{category_name}")
    public void saveProducts(@PathVariable(value="seller_user_id")Integer seller_user_id, @RequestBody List<Product> product,@PathVariable(value="category_name")String category_name)
    {
        List<Product> product1= data.createProduct(seller_user_id, product, category_name);
    }

    @PostMapping("/productSave/variation/{product_id}")
    public void saveProductVariation(@PathVariable(value="product_id")Integer product_id, @RequestBody List<Product_Variation> product_variations)
    {
        List<Product_Variation>product_variations1=data.createProductVariation(product_id,product_variations);
    }

    /*@PostMapping(name="/productSave/variation/{product_id}",consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> saveProductVariation(@PathVariable(value="product_id")Integer product_id,@RequestParam("file")MultipartFile file,@RequestParam(required=true,value="product_variation")List<Product_Variation> product_variations) throws IOException
    {
        File convertFile=new File("/Users/siddharthbhatia/Desktop/Papers"+file.getOriginalFilename());
        convertFile.createNewFile();
        FileOutputStream fout=new FileOutputStream();
        fout.write(file.getBytes());
        fout.close();
        Product_Variation product_variation1=objectMapper.readValue(file,Product_Variation.class);
        Product_Variation p= (Product_Variation) data.createProductVariation(product_id,product_variations);
        List<Product_Variation>product_variations1=data.createProductVariation(product_id,product_variations);
        return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
    }*/

    @PostMapping("/{customer_user_id}/productReviewSave/{product_id}")
    public void saveProductsReviews(@PathVariable(value="customer_user_id")Integer customer_user_id, @RequestBody List<Product_Review> product_reviews, @PathVariable(value="product_id")Integer product_id)
    {
        List<Product_Review> product_reviews1= data.createProductReviews(customer_user_id, product_reviews, product_id);
    }

    @PostMapping("{customer_user_id}/add-to-cart/{productVariation_id}")
    public void addToCart(@PathVariable Integer customer_user_id, @RequestBody Cart cart,@PathVariable Integer productVariation_id){
        Cart cart1= data.addToCart(customer_user_id, cart, productVariation_id);
    }
}

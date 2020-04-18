package com.ecommerce.Ecommerce.controller;


import com.ecommerce.Ecommerce.entities.product.Category;
import com.ecommerce.Ecommerce.services.CategoryDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    CategoryDaoService data;

    @PostMapping("admin/category")
    public String createCategory(@RequestBody Category category)
    {
        String save=data.categoryCreate(category);
        return save;
    }

    @PostMapping("/category/{parentCategory}")
    public String createCategoryParent(@PathVariable String parentCategory ,@RequestBody List<Category> subCategory)
    {
        String save=data.subcategoryCreate(parentCategory,subCategory);
        return save;
    }

    @GetMapping("category/list")
    public List<Category> findAllCategories(){
        return data.showAllCategory();
    }
}

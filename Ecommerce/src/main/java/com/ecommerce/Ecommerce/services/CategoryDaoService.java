package com.ecommerce.Ecommerce.services;

import com.ecommerce.Ecommerce.entities.product.Category;
import com.ecommerce.Ecommerce.repos.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryDaoService {
    @Autowired
    CategoryRepository categoryRepository;

     public String categoryCreate(Category category)
     {
         categoryRepository.save(category);
         return "New Category added";
     }

     public String subcategoryCreate(String parentName, List<Category> subCategory)
     {
         Optional<Category> parentCategory=categoryRepository.findByName(parentName);
         Category category=parentCategory.get();

         Category finalCategory = category;
         subCategory.forEach(e->e.setParentCategory(finalCategory));

         categoryRepository.saveAll(subCategory);

         return "Sub Category saved";
     }

     public List<Category> showAllCategory()
     {
         return categoryRepository.listCategory();
     }
}

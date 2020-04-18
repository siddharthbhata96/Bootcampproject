package com.ecommerce.Ecommerce.services;

import com.ecommerce.Ecommerce.entities.product.Category;
import com.ecommerce.Ecommerce.entities.product.CategoryMetadataField;
import com.ecommerce.Ecommerce.entities.product.CategoryMetadataFieldValues;
import com.ecommerce.Ecommerce.exception.BadRequestException;
import com.ecommerce.Ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.Ecommerce.model.CategoryMetadataFieldModel;
import com.ecommerce.Ecommerce.model.MetadataFieldValueInsertModel;
import com.ecommerce.Ecommerce.repos.CategoryMetadataFieldRepository;
import com.ecommerce.Ecommerce.repos.CategoryMetadataFieldValuesRepository;
import com.ecommerce.Ecommerce.repos.CategoryRepository;
import com.ecommerce.Ecommerce.utils.StringToMapParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryMetadataFieldService {
    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public String addNewMetadataField(String fieldName){
        CategoryMetadataField categoryMetadataField = categoryMetadataFieldRepository.findByName(fieldName);
        if (categoryMetadataField!=null){
            throw new BadRequestException("Metadata field already exists");
        }
        else{
            CategoryMetadataField categoryMetadataField1= new CategoryMetadataField();
            categoryMetadataField1.setName(fieldName);
            categoryMetadataFieldRepository.save(categoryMetadataField1);
            return "Category metadata field created";
        }
    }

    public String addNewMetadataFieldValues(MetadataFieldValueInsertModel fieldValueDtos, Long categoryId, Long metaFieldId){

        Optional<Category> category= categoryRepository.findById(categoryId);
        Optional<CategoryMetadataField> categoryMetadataField= categoryMetadataFieldRepository.findById(metaFieldId);
        if (!category.isPresent())
            throw new ResourceNotFoundException("Category does not exists");
        else if (!categoryMetadataField.isPresent())
            throw new ResourceNotFoundException("Metadata field does not exists");
        else{
            Category category1= new Category();
            category1= category.get();

            CategoryMetadataField categoryMetadataField1= new CategoryMetadataField();
            categoryMetadataField1= categoryMetadataField.get();

            CategoryMetadataFieldValues categoryFieldValues = new CategoryMetadataFieldValues();

            for(CategoryMetadataFieldModel fieldValuePair : fieldValueDtos.getFieldValues()){

                String values = StringToMapParser.toCommaSeparatedString(fieldValuePair.getValues());

                categoryFieldValues.setValue(values);
                categoryFieldValues.setCategory(category1);
                categoryFieldValues.setCategoryMetadataField(categoryMetadataField1);

                categoryMetadataFieldValuesRepository.save(categoryFieldValues);
            }
            return "Metadata field values added successfully";
        }

    }

}

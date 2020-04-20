package com.ecommerce.Ecommerce.dto;

import com.ecommerce.Ecommerce.dto.CategoryMetadataFieldDto;

import java.util.List;

public class MetadataFieldValueInsertDto {

    private List<CategoryMetadataFieldDto> fieldValues;

    public List<CategoryMetadataFieldDto> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(List<CategoryMetadataFieldDto> fieldValues) {
        this.fieldValues = fieldValues;
    }
}

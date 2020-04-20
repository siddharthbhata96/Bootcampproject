package com.ecommerce.Ecommerce.services;

import com.ecommerce.Ecommerce.dto.ProductVariationUpdateDto;
import com.ecommerce.Ecommerce.entities.product.Product;
import com.ecommerce.Ecommerce.entities.product.ProductVariation;
import com.ecommerce.Ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.Ecommerce.repos.ProductRepository;
import com.ecommerce.Ecommerce.repos.ProductVariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductVariationService {

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    ProductRepository productRepository;

    public String createProductVariation(Long id, List<ProductVariation> product_variations)
    {
        Product product;
        Optional<Product> byId=productRepository.findById(id);
        if(byId.isPresent()) {
            product = byId.get();
            product_variations.forEach((e -> e.setProduct(product)));
            productVariationRepository.saveAll(product_variations);
            return "Products are saved";
        }
        else
        {
            throw  new ResourceNotFoundException("No product with this Id found");
        }
    }

    public ProductVariation displayProductVariation(Integer Id)
    {
        Optional<ProductVariation> variant = productVariationRepository.findById(Id);

        if (variant.isPresent()) {
            ProductVariation productVariation = variant.get();
            return productVariation;
        } else {
            throw new ResourceNotFoundException("Invalid Variant ID");
        }
    }

    @Transactional
    @Modifying
    public String updateProductVariation(ProductVariationUpdateDto productVariationUpdateDto, Integer vid) {
        Optional<ProductVariation> productVariation = productVariationRepository.findById(vid);

        if (productVariation.isPresent()) {
            ProductVariation savedVariation = productVariation.get();

            if (productVariationUpdateDto.getQuantityAvailable() != null)
                savedVariation.setQuantity_available(productVariationUpdateDto.getQuantityAvailable());

            if (productVariationUpdateDto.getPrice() != null)
                savedVariation.setPrice(productVariationUpdateDto.getPrice());

            if (productVariationUpdateDto.getIs_active() != null)
                savedVariation.setIs_active(productVariationUpdateDto.getIs_active());
            return "Product Variant Updated Successfully";
        }
        else {
            throw new ResourceNotFoundException("Invalid Variant ID");
        }
    }


}

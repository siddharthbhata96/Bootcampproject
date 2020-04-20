package com.ecommerce.Ecommerce.services;

import com.ecommerce.Ecommerce.dto.ProductUpdateDto;
import com.ecommerce.Ecommerce.entities.order.Cart;
import com.ecommerce.Ecommerce.entities.product.*;
import com.ecommerce.Ecommerce.entities.registration.Customer;
import com.ecommerce.Ecommerce.entities.registration.EmailSenderService;
import com.ecommerce.Ecommerce.entities.registration.Seller;
import com.ecommerce.Ecommerce.entities.registration.User;
import com.ecommerce.Ecommerce.exception.BadRequestException;
import com.ecommerce.Ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.Ecommerce.exception.UserNotFoundException;
import com.ecommerce.Ecommerce.repos.*;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class ProductDaoService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Category category;

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    ProductReviewRepository productReviewRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserDaoService userDaoService;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    public String createProduct(Integer user_id,List<Product> product,String category_name)
    {
        User sell;
        Optional<User>byId=userRepository.findById(user_id);
        sell=byId.get();

        Seller sell2;
        sell2=(Seller)sell;

        Seller finalSell=sell2;
        product.forEach(e->e.setSeller(finalSell));

        Optional<Category> category1 = categoryRepository.findByName(category_name);
        category=category1.get();

        product.forEach(e->e.setCategory(category));


        productRepository.saveAll(product);
        return  "Product Saved";

    }

    public List<Product> findSellerProducts(Integer sellerid) {
        return productRepository.findSellerAssociatedProducts(sellerid);

    }

    public Product findProduct(Long pid) {
        Optional<Product> product = productRepository.findById(pid);
        if(product.isPresent())
        {
            Product product1 = product.get();
            return product1;
        }
        else
            throw new ResourceNotFoundException("Invalid Product ID");
    }


    public String deleteProduct(Long pid, Integer sellerid) {
        Optional<Product> product = productRepository.findById(pid);

        if (product.isPresent()) {
            Product savedProduct = product.get();
            Integer s_id = savedProduct.getSeller().getId();

            System.out.println(""+s_id);

            if (s_id.equals(sellerid)) {

                savedProduct.setIs_deleted(true);
                productRepository.save(savedProduct);
                return "Product Deleted Successfully";
            }
            else {
                throw new BadRequestException("Product not associated to current seller");
            }
        }
        else {
            throw new ResourceNotFoundException("Invalid Product ID");
        }
    }

    @Transactional
    @Modifying
    public String updateProduct(ProductUpdateDto productUpdateDto, Long pid, Integer sellerid) {
        Optional<Product> product = productRepository.findById(pid);

        if (product.isPresent()) {
            Product savedProduct = product.get();

            Integer s_id = savedProduct.getSeller().getId();

            if(s_id.equals(sellerid)) {
                if (productUpdateDto.getProductName() != null)
                    savedProduct.setName(productUpdateDto.getProductName());

                if (productUpdateDto.getBrand() != null)
                    savedProduct.setBrand(productUpdateDto.getBrand());

                if (productUpdateDto.getProductDescription() != null)
                    savedProduct.setDescription(productUpdateDto.getProductDescription());

                if (productUpdateDto.getCancellable() != null)
                    savedProduct.setIs_cancellable(productUpdateDto.getCancellable());

                if (productUpdateDto.getReturnable() != null)
                    savedProduct.setIs_returnable(productUpdateDto.getReturnable());

                return "Product Updated Successfully";

            }
            else {
                throw new BadRequestException("Product not associated to current seller");
            }

        }
        else {
            throw new ResourceNotFoundException("Invalid Product ID");
        }
    }

    public Product viewParticularProduct(String product_name) {
        Product product = productRepository.findByProductName(product_name);
        return product;
    }

    public List<Product> findCategoryProducts(String category_name) {
        String category=categoryRepository.findByCatName(category_name);
        return productRepository.findAllProducts(category);
    }

    @Transactional
    public String activateProduct(Long pid) {

        Optional<Product> product = productRepository.findById(pid);
        if (product.isPresent()) {
            Product product1 = product.get();
            Seller seller = product1.getSeller();

            String emailid = seller.getEmail();

            if(!product1.isIs_active())
            {
                product1.setIs_active(true);
                productRepository.save(product1);
                SimpleMailMessage mailMessage=new SimpleMailMessage();
                mailMessage.setTo(emailid);
                mailMessage.setSubject("Product Activated!!");
                mailMessage.setFrom("siddharth.bhatia1996@gmail.com");
                mailMessage.setText("Your product has been Activated by our Team" +
                        " Customers can now view it and place orders for same.");
                emailSenderService.sendEmail((mailMessage));
                return "Product Activated";
            }
            else
            {
                return "Product is already Activated";
            }

        } else {
            throw new ResourceNotFoundException("Incorrect Product ID");
        }
    }

    @Transactional
    public String deactivateProduct(Long pid)
    {
        Optional<Product> product = productRepository.findById(pid);
        if (product.isPresent()) {
            Product product1 = product.get();
            Seller seller = product1.getSeller();

            String emailid = seller.getEmail();

            if(product1.isIs_active())
            {
                product1.setIs_active(false);
                productRepository.save(product1);
                SimpleMailMessage mailMessage=new SimpleMailMessage();
                mailMessage.setTo(emailid);
                mailMessage.setSubject("Product Deactivated!!");
                mailMessage.setFrom("siddharth.bhatia1996@gmail.com");
                mailMessage.setText("Your product has been deactivated by our Team" +
                        "Please contact our team for assistance");
                emailSenderService.sendEmail((mailMessage));
                return "Product Deactivated";
            }
            else
            {
                return "Product is already deactivated";
            }

        } else {
            throw new ResourceNotFoundException("Incorrect Product ID");
        }
    }
}

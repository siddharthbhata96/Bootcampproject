package com.ecommerce.Ecommerce.services;

import com.ecommerce.Ecommerce.entities.order.Cart;
import com.ecommerce.Ecommerce.entities.product.ProductVariation;
import com.ecommerce.Ecommerce.entities.registration.Customer;
import com.ecommerce.Ecommerce.entities.registration.User;
import com.ecommerce.Ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.Ecommerce.exception.UserNotFoundException;
import com.ecommerce.Ecommerce.repos.CartRepository;
import com.ecommerce.Ecommerce.repos.ProductVariationRepository;
import com.ecommerce.Ecommerce.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartDaoService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    CartRepository cartRepository;

    public String addToCart(Integer customer_user_id, Cart cart, Integer productVariationId)
    {
        Optional<User>byIdCustomer=userRepository.findById(customer_user_id);
        if(byIdCustomer.isPresent())
        {
            User user;
            user=byIdCustomer.get();

            Customer customer;
            customer=(Customer) user;

            cart.setCustomer(customer);

            Optional<ProductVariation>byIdProduct=productVariationRepository.findById(productVariationId);

            if(byIdProduct.isPresent())
            {
                ProductVariation productVariation;
                productVariation=byIdProduct.get();

                Integer qty=cart.getQuantity();

                if(qty<productVariation.getQuantity_available())
                {
                    cart.setProductVariation(productVariation);
                    cartRepository.save(cart);
                    return  "Order Added to cart";
                }
                else
                {
                    throw new ResourceNotFoundException("Ordered Quantity is greater than available stock");
                }
            }
            else
            {
            throw new ResourceNotFoundException("Invalid product ID");
        }
        }
          else
              {
        throw new UserNotFoundException("Invalid customer ID");
    }

    }
}

package com.ecommerce.Ecommerce.services;

import com.ecommerce.Ecommerce.entities.order.OrderProduct;
import com.ecommerce.Ecommerce.entities.order.Orders;
import com.ecommerce.Ecommerce.entities.order.Cart;
import com.ecommerce.Ecommerce.entities.product.ProductVariation;
import com.ecommerce.Ecommerce.entities.registration.Address;
import com.ecommerce.Ecommerce.entities.registration.Customer;
import com.ecommerce.Ecommerce.entities.registration.User;
import com.ecommerce.Ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.Ecommerce.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class OrderDaoService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductVariationRepository productVariationRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderProductRepository order_productRepository;

    public String addToOrders(Integer customer_user_id, Orders orders, Integer cart_id)
    {
        Optional<Cart> cartId= cartRepository.findById(cart_id);
        if(cartId.isPresent())
        {
            Cart cart;
            cart = cartId.get();

            Customer customer=cart.getCustomer();

            orders.setCustomer(customer);

            Address address;
            String address_label=orders.getCustomer_address_label();
            Optional<Address>address1=addressRepository.findByAdd(address_label, customer_user_id);

            if(address1.isPresent())
            {
                address=address1.get();
                orders.setCustomer_address_address_line(address.getHouse_number());
                orders.setCustomer_address_city(address.getCity());
                orders.setCustomer_address_state(address.getState());
                orders.setCustomer_address_country(address.getCountry());
                orders.setCustomer_address_zipcode(address.getZip_code());
                orders.setDate_created(new Date());
            }
            else
            {
                throw new ResourceNotFoundException("Address not available for this account");
            }
            OrderProduct orderProduct=new OrderProduct();
            orderProduct.setOrders(orders);
            orderProduct.setProduct_variation(cart.getProductVariation());
            orderProduct.setQuantity(cart.getQuantity());

            ProductVariation productVariation=cart.getProductVariation();
            orderProduct.setPrice(productVariation.getPrice());
            Double amount=orderProduct.getPrice()*cart.getQuantity();
            orders.setAmount_paid(amount);

            Integer originalQty=productVariation.getQuantity_available();
            Integer customerQty=cart.getQuantity();

            productVariation.setQuantity_available(originalQty-customerQty);

            productVariationRepository.save(productVariation);
            order_productRepository.save(orderProduct);
            orderRepository.save(orders);
            return "Order will be delivered to selected address";
        }
        else
        {
            throw  new ResourceNotFoundException("Nothing in your cart");
        }
    }
}

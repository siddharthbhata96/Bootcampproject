package com.ecommerce.Ecommerce.services;

import com.ecommerce.Ecommerce.entities.Order_Details.Order_Product;
import com.ecommerce.Ecommerce.entities.Order_Details.Orders;
import com.ecommerce.Ecommerce.entities.Product_Details.Cart;
import com.ecommerce.Ecommerce.entities.Product_Details.Product_Variation;
import com.ecommerce.Ecommerce.entities.Registration_Details.Address;
import com.ecommerce.Ecommerce.entities.Registration_Details.Customer;
import com.ecommerce.Ecommerce.entities.Registration_Details.User;
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
    private Product_VariationRepository productVariationRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private Order_ProductRepository order_productRepository;

    public Orders addToOrder(Integer customer_user_id, Orders orders, Integer cart_id)
    {
        Optional<User> customer = userRepository.findById(customer_user_id);
        User user;
        user=customer.get();

        Customer customer1;
        customer1=(Customer)user;

        orders.setCustomer(customer1);

        Address address;
        String address_label=orders.getCustomer_address_label();
        Optional<Address> address1= addressRepository.findByAdd(address_label, customer_user_id);
        address= address1.get();

        orders.setCustomer_address_address_line(address.getLabel());
        orders.setCustomer_address_city(address.getCity());
        orders.setCustomer_address_country(address.getCountry());
        orders.setCustomer_address_state(address.getState());
        orders.setCustomer_address_zipcode(address.getZip_code());
        orders.setDate_created(new Date());

        orderRepository.save(orders);

        Optional<Cart> cartId= cartRepository.findById(cart_id);
        Cart cart;
        cart= cartId.get();

        Order_Product orderProduct=new Order_Product();
        orderProduct.setOrders(orders);
        orderProduct.setProduct_variation(cart.getProductVariation());
        orderProduct.setQuantity(cart.getQuantity());

        Product_Variation product_variation= cart.getProductVariation();

        orderProduct.setPrice(product_variation.getPrice());

        order_productRepository.save(orderProduct);

        return orders;
    }

}

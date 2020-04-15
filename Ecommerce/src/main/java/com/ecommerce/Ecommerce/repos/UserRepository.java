package com.ecommerce.Ecommerce.repos;

import com.ecommerce.Ecommerce.entities.Product_Details.Category;
import com.ecommerce.Ecommerce.entities.Registration_Details.Customer;
import com.ecommerce.Ecommerce.entities.Registration_Details.Seller;
import com.ecommerce.Ecommerce.entities.Registration_Details.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static java.lang.Boolean.TRUE;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
  Optional <User> findByEmail(String useremail);

/*
  @Query(value = "select city,country,house_number,label,state,zip_code from address,user where user.id=address.user_id and user_id=id",nativeQuery = true)
  List<Object[]>  findListCustomer(@Param("id") Integer id);
*/



  /* @Query(value = "select id,email,first_name,middle_name,last_name,is_active from user,customer where user.id=customer.user_id",nativeQuery = true)
   List<Object[]> listCustomers();


   @Query(value = "select id,email,first_name,middle_name,last_name,is_active,company_contact,company_name from user,seller where user.id=seller.user_id",nativeQuery = true)
   List<Object[]> listSeller();*/

}

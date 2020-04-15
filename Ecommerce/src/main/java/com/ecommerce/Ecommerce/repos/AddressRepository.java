package com.ecommerce.Ecommerce.repos;

import com.ecommerce.Ecommerce.entities.Registration_Details.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends CrudRepository<Address,Integer> {

    @Query(value = "select * from address where user_id=:id and label=:label", nativeQuery = true)
    Optional<Address> findByAdd(@Param("label") String label, @Param("id") Integer id);

   /* @Query(value="select city,country,house_number,label,state,zip_code from address,user where user.id=address.user_id and user_id=id", nativeQuery = true)
    List<Object[]> findCustomer(@Param("id") Integer id);*/
}

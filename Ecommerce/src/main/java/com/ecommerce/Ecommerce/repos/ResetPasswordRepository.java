package com.ecommerce.Ecommerce.repos;

import com.ecommerce.Ecommerce.entities.Registration_Details.ResetPasswordToken;
import com.ecommerce.Ecommerce.entities.Registration_Details.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ResetPasswordRepository extends CrudRepository<ResetPasswordToken,Integer> {
    ResetPasswordToken findByUser(User user);

    ResetPasswordToken findByToken(String resetToken);
}

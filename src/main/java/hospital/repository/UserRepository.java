/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital.repository;

import hospital.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Constant
 */
public interface UserRepository extends MongoRepository<User, String> {

    public User findByUserId(String userId);

    public User findByEmail(String email);

    public User findByRole(String role);

    public Page<User> findAll(Pageable pageRequest);

}

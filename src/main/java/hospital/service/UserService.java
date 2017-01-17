/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital.service;

import hospital.controller.UserAPIResponse;
import hospital.domain.JSONResponse;
import hospital.domain.PasswordEncoder;
import hospital.domain.User;
import hospital.repository.UserRepository;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Constant
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public JSONResponse createUser(String email, String firstname, String lastname, String password, String role) {
        JSONResponse jResponse = new JSONResponse();
        User user = userRepo.findByEmail(email);
        if (user == null) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setFirstname(firstname);
            newUser.setLastname(lastname);
            newUser.setRole(role);
            try {
                PasswordEncoder encoder = new PasswordEncoder();
                newUser.setPasswordDigest(encoder.getSaltedHash(password));
            } catch (Exception ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
            UserAPIResponse res = null;
            userRepo.save(newUser);
            User check = userRepo.findByEmail(email);
            if (check != null) {
                res = new UserAPIResponse(email, firstname, lastname);
            }
            jResponse.setResult(res);
            jResponse.setStatus(true);
            jResponse.setMessage("User created successfully");
            return jResponse;
        } else {
            jResponse.setResult(null);
            jResponse.setStatus(false);
            jResponse.setMessage("This email already exists");

            return jResponse;
        }
    }

}

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

    @Autowired
    private Authenticate auth;

    public JSONResponse createUser(String managerEmail, String managerPassword,
            String email, String firstname, String lastname, String password, String role) {
        JSONResponse jResponse = new JSONResponse();
        User u = auth.authenticate(managerEmail, managerPassword);
        User user = userRepo.findByEmail(email);
        if (user == null && u != null && u.getRole().equalsIgnoreCase("manager")) {
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
            if (u == null || !u.getRole().equalsIgnoreCase("manager")) {
                jResponse.setMessage("You do not have the permissions to create an account");
            } else {
                jResponse.setMessage("This email already exists");
            }
            return jResponse;
        }
    }
    

    public JSONResponse deleteUser(String managerEmail, String managerPassword, String deleteEmail) {
        JSONResponse jRes = new JSONResponse();
        User u = auth.authenticate(managerEmail, managerPassword);
        User user = userRepo.findByEmail(deleteEmail);
        if (user != null && u != null && u.getRole().equalsIgnoreCase("manager")) {
            userRepo.delete(user);
            jRes.setMessage("User deleted");
            jRes.setResult(null);
            jRes.setStatus(true);
            return jRes;
        } else {
            jRes.setMessage("This user does not exist");
            jRes.setResult(null);
            jRes.setStatus(false);
            return jRes;
        }
    }

    public JSONResponse getUserByEmail(String email, String password) {
        JSONResponse jRes = new JSONResponse();
        User u = auth.authenticate(email, password);
        if (!email.isEmpty() && u != null) {
            User user = userRepo.findByEmail(email);
            if (user != null) {
                User newUser = new User();
                newUser.setUserId(user.getUserId());
                newUser.setEmail(email);
                newUser.setFirstname(user.getFirstname());
                newUser.setLastname(user.getLastname());
                jRes.setMessage("User found");
                jRes.setResult(newUser);
                jRes.setStatus(true);
            } else {
                jRes.setMessage("User not found");
                jRes.setResult(null);
                jRes.setStatus(false);
            }
            return jRes;
        } else {
            jRes.setMessage("The email of the user cannot be empty");
            jRes.setResult(null);
            jRes.setStatus(false);
            return jRes;
        }
    }
}

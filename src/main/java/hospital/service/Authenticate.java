/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital.service;

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
public class Authenticate {

    @Autowired
    private UserRepository userRepo;

    public User authenticate(String email, String password) {
        PasswordEncoder enc = new PasswordEncoder();
        User u = userRepo.findByEmail(email);
        if (u != null) {
            try {
                boolean status = enc.check(password, u.getPasswordDigest());
                if (status) {
                    return u;
                }
            } catch (Exception ex) {
                Logger.getLogger(Authenticate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public JSONResponse auth(String email, String password) {
        PasswordEncoder enc = new PasswordEncoder();
        JSONResponse jRes = new JSONResponse();
        User u = userRepo.findByEmail(email);
        if (u != null) {
            try {
                boolean status = enc.check(password, u.getPasswordDigest());
                if (status) {
                    jRes.setMessage("Authenticated");
                    jRes.setStatus(true);
                    jRes.setResult(null);
                    return jRes;
                }
            } catch (Exception ex) {
                Logger.getLogger(Authenticate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        jRes.setMessage("Could not authenticate");
        jRes.setResult(null);
        jRes.setStatus(false);
        return jRes;

    }

    public UserRepository getUserRepo() {
        return userRepo;
    }

    public void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

}

package hospital.controller;

import hospital.domain.JSONResponse;
import hospital.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class APIController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping(value = "/createUser/{email}/{firstName}/{lastName}/"
            + "{password}/{role}", method = RequestMethod.GET)
    @ResponseBody
    public JSONResponse createUser(
            @PathVariable String email,
            @PathVariable String firstName,
            @PathVariable String lastName,
            @PathVariable String password,
            @PathVariable String role) {
        return userService.createUser(email, firstName, lastName, password, role);
    }

}

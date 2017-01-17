package hospital.controller;

import hospital.domain.JSONResponse;
import hospital.service.Authenticate;
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

    @Autowired
    private Authenticate auth;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping(value = "/createUser/{managerEmail}/{managerPassword}/{email}/{firstName}/{lastName}/"
            + "{password}/{role}", method = RequestMethod.GET)
    @ResponseBody
    public JSONResponse createUser(
            @PathVariable String managerEmail,
            @PathVariable String managerPassword,
            @PathVariable String email,
            @PathVariable String firstName,
            @PathVariable String lastName,
            @PathVariable String password,
            @PathVariable String role) {
        return userService.createUser(managerEmail, managerPassword, email, firstName, lastName, password, role);
    }

    @RequestMapping(value = "/deleteUser/{managerEmail}/{managerPassword}/{deleteEmail:.+}", method = RequestMethod.GET)
    @ResponseBody
    public JSONResponse deleteUser(
            @PathVariable String managerEmail,
            @PathVariable String managerPassword,
            @PathVariable String deleteEmail) {
        return userService.deleteUser(managerEmail, managerPassword, deleteEmail);
    }

    @RequestMapping(value = "/getUserByEmail/{email}/{password}", method = RequestMethod.GET)
    @ResponseBody
    public JSONResponse getUserByEmail(
            @PathVariable String email,
            @PathVariable String password) {
        return userService.getUserByEmail(email, password);
    }

    @RequestMapping(value = "/authenticate/{username}/{password}", method = RequestMethod.GET)
    @ResponseBody
    public JSONResponse authenticate(
            @PathVariable String username,
            @PathVariable String password) {
        return auth.auth(username, password);
    }

}

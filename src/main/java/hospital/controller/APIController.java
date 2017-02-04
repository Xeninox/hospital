package hospital.controller;

import hospital.domain.JSONResponse;
import hospital.service.Authenticate;
import hospital.service.PatientService;
import hospital.service.UserService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class APIController {

    @Autowired
    private UserService userService;

    @Autowired
    private Authenticate auth;

    @Autowired
    private PatientService patientServ;

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

    @RequestMapping(value = "/setPatient",
            method = RequestMethod.POST)
    @ResponseBody
    JSONResponse setPatient(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String middleName,
            @RequestParam String lastName,
            @RequestParam String address,
            @RequestParam String dateOfBirth,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        return patientServ.createPatient(email, password, firstName, middleName, lastName, address, dateOfBirth, image);
    }

    @RequestMapping(value = "/updatePatient",
            method = RequestMethod.POST)
    @ResponseBody
    JSONResponse updatepatient(
            @RequestParam String patientid,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String middleName,
            @RequestParam String lastName,
            @RequestParam String address,
            @RequestParam String dateOfBirth,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        return patientServ.updatePatient(patientid, email, password, firstName, middleName, lastName, address, dateOfBirth, image);
    }

    @RequestMapping(value = "/deletePatient/{email}/{password}/{patientId}", method = RequestMethod.GET)
    @ResponseBody
    public JSONResponse deletePatient(
            @PathVariable String email,
            @PathVariable String password,
            @PathVariable String patientId) {
        return patientServ.deletePatient(email, password, patientId);
    }

    @RequestMapping(value = "/getPatientById/{email}/{password}/{patientId}", method = RequestMethod.GET)
    @ResponseBody
    public JSONResponse getPatientById(
            @PathVariable String email,
            @PathVariable String password,
            @PathVariable String patientId) {
        return patientServ.getPatientById(email, password, patientId);
    }

}

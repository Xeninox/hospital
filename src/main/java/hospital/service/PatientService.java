/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital.service;

import hospital.domain.JSONResponse;
import hospital.domain.Patient;
import hospital.domain.User;
import hospital.repository.PatientRepository;
import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Constant
 */
@Service
public class PatientService {

    @Autowired
    private Authenticate auth;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private HttpServletRequest request;

    public JSONResponse createPatient(String managerEmail, String managerPassword,
            String firstname, String middlename, String lastname, String address,
            String dateOfBirth, MultipartFile image) throws IOException {
        JSONResponse jResponse = new JSONResponse();
        User u = auth.authenticate(managerEmail, managerPassword);
        Patient pat = new Patient();
        String logo = saveLogo(image);

        if (u != null && (u.getRole().equalsIgnoreCase("manager")
                || u.getRole().equalsIgnoreCase("receptionist"))) {
            if (!firstname.isEmpty() && !middlename.isEmpty()
                    && !lastname.isEmpty() && !address.isEmpty()
                    && !dateOfBirth.isEmpty() && !image.isEmpty()) {
                pat.setAddress(address);
                pat.setDateOfBirth(dateOfBirth);
                pat.setFirstName(firstname);
                pat.setLastName(lastname);
                pat.setMiddleName(middlename);
                pat.setPatientImage(logo);
                patientRepo.save(pat);
                jResponse.setMessage("Patient created successfully");
                jResponse.setResult(pat);
                jResponse.setStatus(true);
            } else {
                jResponse.setMessage("Patient creation failed");
                jResponse.setResult(null);
                jResponse.setStatus(false);
            }
            return jResponse;
        } else {
            jResponse.setMessage("Patient creation failed");
            jResponse.setResult(null);
            jResponse.setStatus(false);
            return jResponse;
        }
    }

    private String saveLogo(MultipartFile image) throws IOException {
        String fileName = image.getOriginalFilename();
        String path = request.getServletContext().getRealPath("/");
        //making directories for our required path.
        byte[] bytes = image.getBytes();
        File directory = new File(path + "/uploads");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // saving the file
        File file = new File(directory.getAbsolutePath() + System.getProperty("file.separator") + fileName);
        image.transferTo(file);
        return "/uploads/" + file.getName();
    }
    
    public JSONResponse updatePatient(String patientId, String managerEmail, String managerPassword,
            String firstname, String middlename, String lastname, String address,
            String dateOfBirth, MultipartFile image) throws IOException {
        JSONResponse jResponse = new JSONResponse();
        User u = auth.authenticate(managerEmail, managerPassword);
        Patient pat = patientRepo.findByPatientId(patientId);
        String logo = saveLogo(image);

        if (u != null && (u.getRole().equalsIgnoreCase("manager")
                || u.getRole().equalsIgnoreCase("receptionist")) && pat != null) {
            if (!firstname.isEmpty() && !middlename.isEmpty()
                    && !lastname.isEmpty() && !address.isEmpty()
                    && !dateOfBirth.isEmpty() && !image.isEmpty()) {
                pat.setPatientId(patientId);
                pat.setAddress(address);
                pat.setDateOfBirth(dateOfBirth);
                pat.setFirstName(firstname);
                pat.setLastName(lastname);
                pat.setMiddleName(middlename);
                pat.setPatientImage(logo);
                patientRepo.save(pat);
                jResponse.setMessage("Patient updated successfully");
                jResponse.setResult(pat);
                jResponse.setStatus(true);
            } else {
                jResponse.setMessage("Patient update failed");
                jResponse.setResult(null);
                jResponse.setStatus(false);
            }
            return jResponse;
        } else {
            jResponse.setMessage("Patient update failed");
            jResponse.setResult(null);
            jResponse.setStatus(false);
            return jResponse;
        }
    }

    public JSONResponse deletePatient(String email, String password, String patientId) {
        JSONResponse jRes = new JSONResponse();
        User u = auth.authenticate(email, password);
        Patient pat = patientRepo.findByPatientId(patientId);
        if (pat != null && u != null && u.getRole().equalsIgnoreCase("manager")) {
            patientRepo.delete(pat);
            jRes.setMessage("Patient deleted");
            jRes.setResult(null);
            jRes.setStatus(true);
            return jRes;
        } else {
            jRes.setMessage("This patient does not exist");
            jRes.setResult(null);
            jRes.setStatus(false);
            return jRes;
        }
    }
    
     public JSONResponse getPatientById(String email, String password, String patientId) {
        JSONResponse jRes = new JSONResponse();
        User u = auth.authenticate(email, password);
        if (!email.isEmpty() && u != null) {
            Patient pat = patientRepo.findByPatientId(patientId);
            if (pat != null) {
                jRes.setMessage("Patient found");
                jRes.setResult(pat);
                jRes.setStatus(true);
            } else {
                jRes.setMessage("Patient not found");
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

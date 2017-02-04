/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital.repository;

import hospital.domain.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Constant
 */
public interface PatientRepository extends MongoRepository<Patient, String>{
    
    public Patient findByPatientId(String patientId);
    
    public Patient findByLastName(String lastName);
    
}

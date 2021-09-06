package com.example.patient.fr.repositories;

import com.example.patient.fr.entities.PatientEntity;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<PatientEntity, Integer> {

}

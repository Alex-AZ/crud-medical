package com.example.patient.fr.repositories;

import com.example.patient.fr.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    public UserEntity findByEmail(String username);
}

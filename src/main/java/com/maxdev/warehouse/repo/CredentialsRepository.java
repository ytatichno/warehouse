package com.maxdev.warehouse.repo;

import com.maxdev.warehouse.models.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialsRepository extends JpaRepository<Credential,Integer> {
    Credential findCredentialByEmail(String email);
}


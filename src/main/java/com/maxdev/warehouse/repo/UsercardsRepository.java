package com.maxdev.warehouse.repo;

import com.maxdev.warehouse.models.Usercard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsercardsRepository extends JpaRepository<Usercard,Integer> {
}

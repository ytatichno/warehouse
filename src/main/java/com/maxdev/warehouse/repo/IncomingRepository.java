package com.maxdev.warehouse.repo;

import com.maxdev.warehouse.models.Incoming;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IncomingRepository extends JpaRepository<Incoming,Integer> {
}

package com.maxdev.warehouse.repo;

import com.maxdev.warehouse.models.Rack;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RacksRepository extends JpaRepository<Rack,Integer> {
}

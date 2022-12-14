package com.maxdev.warehouse.repo;

import com.maxdev.warehouse.models.Outcoming;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;



public interface OutcomingRepository extends JpaRepository<Outcoming,Integer> {
    List<Outcoming> findAllBySatisfiedFalse();

}

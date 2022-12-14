package com.maxdev.warehouse.repo;

import com.maxdev.warehouse.models.Rack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RacksRepository extends JpaRepository<Rack,Integer> {
    List<Rack> findAllByGoodId(Integer id);
    Rack findByAddr(String addr);
}

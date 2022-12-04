package com.maxdev.warehouse.repo;

import com.maxdev.warehouse.models.Good;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GoodsRepository extends JpaRepository<Good,Integer> {
}

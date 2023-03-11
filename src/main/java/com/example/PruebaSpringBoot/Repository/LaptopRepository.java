package com.example.PruebaSpringBoot.Repository;

import com.example.PruebaSpringBoot.Entity.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Long>{

}

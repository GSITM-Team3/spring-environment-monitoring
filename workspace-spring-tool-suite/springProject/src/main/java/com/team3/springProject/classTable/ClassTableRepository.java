package com.team3.springProject.classTable;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassTableRepository extends JpaRepository<ClassTable, Long>{
   List<ClassTable> findAll();
   Optional<ClassTable> findByName(String name);
}

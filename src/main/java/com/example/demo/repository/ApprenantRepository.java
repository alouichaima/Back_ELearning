package com.example.demo.repository;

import com.example.demo.entity.Apprenant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApprenantRepository extends JpaRepository<Apprenant, Long> {
     Optional<Apprenant> findById(Long id);


}



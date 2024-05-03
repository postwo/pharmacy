package com.example.phproject.pharmacy.repository;


import com.example.phproject.pharmacy.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManagerFactory;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {


}
package com.example.phproject.pharmacy.entity;

import com.example.phproject.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "pharmacy")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pharmacy extends BaseTimeEntity { //이거는 [] 이렇게 감싸져있는 리스트이다  //BaseTimeEntity 시간 auditing

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pharmacyName;
    private String pharmacyAddress;
    private double latitude;
    private double longitude;

    //주소를 변경
    public void changePharmacyAddress(String address) { //파라메터 변경하려는 주소
        this.pharmacyAddress = address;
    }
}
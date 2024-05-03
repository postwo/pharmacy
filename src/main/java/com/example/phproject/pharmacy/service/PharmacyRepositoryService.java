package com.example.phproject.pharmacy.service;

import com.example.phproject.pharmacy.entity.Pharmacy;
import com.example.phproject.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRepositoryService {
    private final PharmacyRepository pharmacyRepository;

    @Transactional
    public void updateAddress(Long id, String address){
       Pharmacy entity = pharmacyRepository.findById(id).orElse(null);

       if (Objects.isNull(entity)){ //validation check  entity가 null이면
           log.error("[PharmacyRepositoryService updateAddress] not found id:{}", id);
           return ;
       }

       entity.changePharmacyAddress(address);
    }


    //for test 트랜잭션 있는거와 없는거 비교 해보기 위해 만듬
    public void updateAddressWithoutTransaction(Long id, String address){
        Pharmacy entity = pharmacyRepository.findById(id).orElse(null);

        if (Objects.isNull(entity)){ //validation check  entity가 null이면
            log.error("[PharmacyRepositoryService updateAddress] not found id:{}", id);
            return ;
        }

        entity.changePharmacyAddress(address);
    }

}

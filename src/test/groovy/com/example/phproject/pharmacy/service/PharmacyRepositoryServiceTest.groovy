package com.example.phproject.pharmacy.service

import com.example.phproject.AbstractIntegrationContainerBaseTest
import com.example.phproject.pharmacy.entity.Pharmacy
import com.example.phproject.pharmacy.repository.PharmacyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

//@Transactional
class PharmacyRepositoryServiceTest extends AbstractIntegrationContainerBaseTest{ //통합테스트

    @Autowired
    private PharmacyRepositoryService pharmacyRepositoryService;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    //테스트를 시작하면 데이터베이스 테이터를 지우기 위해 생성
    def setup(){
        pharmacyRepository.deleteAll()
    }

    def "PharmacyRepository update - dirty checking success"(){
        given:
        String address = "서울 특별시 성북구 종암동"
        String modifiedAddress = "서울 광진구 구의동" // 변경할 주소
        String name = "은혜 약국"

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .build()

        when:
        def entity=  pharmacyRepository.save(pharmacy)
        pharmacyRepositoryService.updateAddress(entity.getId(), modifiedAddress)

       def result = pharmacyRepository.findAll()

        then:
       result.get(0).getPharmacyAddress() == modifiedAddress //주소변경 검사
    }


    //트랜잭션이 없는 메소드
    def "PharmacyRepository update - dirty checking fail"(){
        given:
        String address = "서울 특별시 성북구 종암동"
        String modifiedAddress = "서울 광진구 구의동" // 변경할 주소
        String name = "은혜 약국"

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .build()

        when:
        def entity=  pharmacyRepository.save(pharmacy)
        pharmacyRepositoryService.updateAddressWithoutTransaction(entity.getId(), modifiedAddress)

        def result = pharmacyRepository.findAll()

        then:
        result.get(0).getPharmacyAddress() == address //변경되지 않는주소
    }

}
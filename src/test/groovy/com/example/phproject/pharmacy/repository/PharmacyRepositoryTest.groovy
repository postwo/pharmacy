package com.example.phproject.pharmacy.repository

import com.example.phproject.AbstractIntegrationContainerBaseTest
import com.example.phproject.pharmacy.entity.Pharmacy
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification


class PharmacyRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    PharmacyRepository pharmacyRepository

    def "PharmacyRepository save"() { // 여기도 테스트할때 환경변수 키값 edit/configuration 꼭 api 키값넣어주기

        given:
        String address = "서울 특별시 성북구 종암동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()
        when:
        def entity = pharmacyRepository.save(pharmacy)

        then:
        entity.getPharmacyAddress() == address
        entity.getPharmacyName() == name
        entity.getLatitude() == latitude
        entity.getLongitude() == longitude
    }
}
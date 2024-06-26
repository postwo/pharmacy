package com.example.phproject.pharmacy.repository

import com.example.phproject.AbstractIntegrationContainerBaseTest
import com.example.phproject.pharmacy.entity.Pharmacy
import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalDateTime


// 이거는 테스트컨테이너로 테스트하기 때문에 테스트할때는 도커를 켜놓고 해야한다
// 도커를 안키고 테스트하면 에러가 작렬한다
class PharmacyRepositoryTest extends AbstractIntegrationContainerBaseTest { 
    

    @Autowired
    PharmacyRepository pharmacyRepository

    //전에 했던 테스트에서 테이터가 남아서 다음 테스트에 영향을 줄수 있기 때문에 set up을 사용
    def setup(){ //테스트 메소드 시작전에 실행이 되는 메소드
        pharmacyRepository.deleteAll()
        /* setup 메소드가 하는 쿼리 =전체적으로 한번 조회하고 그이후 데이터를 지운다
        Hibernate: select pharmacy0_.id as id1_0_, pharmacy0_.created_date as created_2_0_, pharmacy0_.modified_date as modified3_0_, pharmacy0_.latitude as latitude4_0_, pharmacy0_.longitude as longitud5_0_, pharmacy0_.pharmacy_address as pharmacy6_0_, pharmacy0_.pharmacy_name as pharmacy7_0_ from pharmacy pharmacy0_
         Hibernate: delete from pharmacy where id=?
        */
        
    }


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


    def "PharmacyRepository saveAll"(){

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
        pharmacyRepository.saveAll(Arrays.asList(pharmacy))
        def result = pharmacyRepository.findAll()

        then:
        result.size() == 1 // 데이터 들어가 있는게 1개 이냐
    }


    def "BaseTimeEntity 등록"(){
        given:
        LocalDateTime now = LocalDateTime.now()
        String address = "서울 특별시 성북구 종암동"
        String name = "은혜 약국"

        def pharmacy  = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .build()

        when:
        pharmacyRepository.save(pharmacy)
        def result = pharmacyRepository.findAll()

        then:
        //get(0) 들어가있는 데이터1개를 의미한다
        //isAfter() now라는 시간보다 최근인지 확인해주는 메소드이다
        result.get(0).getCreatedDate().isAfter(now)
        result.get(0).getModifiedDate().isAfter(now)
    }

}
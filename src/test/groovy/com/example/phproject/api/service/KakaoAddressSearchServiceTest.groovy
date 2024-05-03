package com.example.phproject.api.service

import com.example.phproject.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

//Could not resolve placeholder 'KAKAO_REST_API_KEY' in value "${KAKAO_REST_API_KEY}" 이러한 에러가 뜨면 카카오 api 키 넣어주기
class KakaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest { //통합테스트 스프링 컨테이너를 띄워서 할거

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService;

    def "address 파라미터 값이 null이면, requestAddressSearch 메소드는 null을 리턴한다"(){
        given: //동적으로  def address , 명시적으로 String address
        def address = null

        when:
        def search = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        search == null
    }

    def "주소값이 valid하다면, requestAddressSearch 메소드는 정상적으로 document를 반환한다"(){
        given:
        def address = "서울 성북구 종암로 10길"

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result.documentList.size() > 0
        result.metaDto.totalcount > 0
        result.documentList.get(0).addressName != null
    }

}
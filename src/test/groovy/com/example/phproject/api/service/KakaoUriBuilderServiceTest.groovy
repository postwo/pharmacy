package com.example.phproject.api.service

import spock.lang.Specification

import java.nio.charset.StandardCharsets


class KakaoUriBuilderServiceTest extends Specification {

    private KakaoUriBuilderService kakaoUriBuilderService;

    //setup 이걸 설정 하면 이게 제일먼저 실행되게 하는거다
    def setup() {
        kakaoUriBuilderService = new KakaoUriBuilderService();
    }

    //한글주소를 입력 인코딩 테스트
    def "builderUrlByAddressSearch - 한글 파라미터의 경우 정상적으로 인코딩"(){
        given:
        String address = "서울 성북구"
        def charset = StandardCharsets.UTF_8

        //def uri 동적으로 타입을 표시할수있다
        when:
        def uri = kakaoUriBuilderService.buildUriByAddressSearch(address)
        def decodeResult = URLDecoder.decode(uri.toString(),charset) // 인코드 하거를 디코드로 다시 풀어버리는과정이다

        then:
        decodeResult == "https://dapi.kakao.com/v2/local/search/address.json?query=서울 성북구" //디코딩한결과가 서울 성북구가 똑같이 나오는지 확인

    }
}
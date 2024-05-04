package com.example.phproject.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class KakaoUriBuilderService {

    private static final String KAKAO_LOCAL_SEARCH_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    //카테고리로 호출
    private static final String KAKAO_LOCAL_CATEGORY_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/category.json";

    public URI buildUriByAddressSearch(String address){
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_ADDRESS_URL);
        uriBuilder.queryParam("query",address);

        URI uri  = uriBuilder.build().encode().toUri(); // uri 생성
        log.info("[KakaoUriBuilderService buildUriByAddressSearch] address:{}, uri:{}", address,uri);

        return uri;
    }

//카카오api
    public URI buildUriByCategorySearch(double latitude, double longitude, double radius, String category) {

        double meterRadius = radius * 1000; //m단위 이다

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_CATEGORY_SEARCH_URL);
        uriBuilder.queryParam("category_group_code", category);
        uriBuilder.queryParam("x", longitude);
        uriBuilder.queryParam("y", latitude);
        uriBuilder.queryParam("radius", meterRadius); //반경은 m단위이다
        uriBuilder.queryParam("sort","distance"); //위도 경도 기준으로 가까운곳을 찾는다

        URI uri = uriBuilder.build().encode().toUri();

        log.info("[KakaoAddressSearchService buildUriByCategorySearch] uri: {} ", uri);

        return uri;
    }

}
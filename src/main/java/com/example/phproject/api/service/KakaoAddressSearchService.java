package com.example.phproject.api.service;


import com.example.phproject.api.dto.KakaoApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoAddressSearchService {

    private final RestTemplate restTemplate;
    private final KakaoUriBuilderService kakaoUriBuilderService;

    @Value("${kakao.rest.api.key}") //value어노테이션을 활용해서 yml 파일에서 카카오 환경변수를 가지고 온다
    private String kakaoRestApiKey;

    @Retryable( //어떤 exception에서 재처리를 해줄건지
            value = {RuntimeException.class},
            maxAttempts = 2, //지정한 재시도 횟수
            backoff = @Backoff(delay = 2000) //얼마만큼 딜레이를 줄건지
            //이걸 모두다 실패했을 경우  @Recover 실행을 한다
    )
    public KakaoApiResponseDto requestAddressSearch(String address){

        //null값이 올수도 있기 때문에 밸리데이션처리를 한다
        if (ObjectUtils.isEmpty(address)) return null; //isEmpty(address) 어드레스 주소가 null값이거나 빈값이 들어갔을경우 처리

        URI uri = kakaoUriBuilderService.buildUriByAddressSearch(address);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey); //KakaoAK 대소문자 확실히 체크하기
        HttpEntity httpEntity = new HttpEntity<>(headers);

        //kakao api 호출
        return restTemplate.exchange(uri, HttpMethod.GET,httpEntity, KakaoApiResponseDto.class).getBody();

    }

    @Recover
    public KakaoApiResponseDto recover(RuntimeException e, String address) {
        log.error("All the retries failed. address: {}, error : {}", address, e.getMessage());
        return null;
    }
}

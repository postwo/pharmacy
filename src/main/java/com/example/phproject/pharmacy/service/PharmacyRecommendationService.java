package com.example.phproject.pharmacy.service;

import com.example.phproject.api.dto.DocumentDto;
import com.example.phproject.api.dto.KakaoApiResponseDto;
import com.example.phproject.api.service.KakaoAddressSearchService;
import com.example.phproject.direction.entity.Direction;
import com.example.phproject.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;




    public void recomendPharmacyList(String address){

       KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

       //밸리데이션 체크 : null값이 올수도있고 ,주소 검색이 안될수도 있고
        if (Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())) {
            log.error("[PharmacyRecommendationService.recommendPharmacyList fail] Input address: {}", address);
            return Collections.emptyList();
        }
            DocumentDto documentDto = kakaoApiResponseDto.getDocumentList().get(0);

            List<Direction> directionList = directionService.buildDirectionList(documentDto);
            //List<Direction> directionList = directionService.buildDirectionListByCategoryApi(documentDto);

            return directionService.saveAll(directionList)
                    .stream()
                    .map(this::convertToOutputDto)
                    .collect(Collectors.toList());
        }

        private OutputDto convertToOutputDto(Direction direction) {

            return OutputDto.builder()
                    .pharmacyName(direction.getTargetPharmacyName())
                    .pharmacyAddress(direction.getTargetAddress())
                    .directionUrl(baseUrl + base62Service.encodeDirectionId(direction.getId()))
                    .roadViewUrl(ROAD_VIEW_BASE_URL + direction.getTargetLatitude() + "," + direction.getTargetLongitude())
                    .distance(String.format("%.2f km", direction.getDistance()))
                    .build();
        }
    }
}
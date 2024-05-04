package com.example.phproject.direction.service;

import com.example.phproject.api.dto.DocumentDto;
import com.example.phproject.api.service.KakaoCategorySearchService;
import com.example.phproject.direction.entity.Direction;
import com.example.phproject.direction.repository.DirectionRepository;
import com.example.phproject.pharmacy.dto.PharmacyDto;
import com.example.phproject.pharmacy.repository.PharmacyRepository;
import com.example.phproject.pharmacy.service.PharmacySearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.internal.NaturalIdXrefDelegate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class DirectionService {

   private static final int MAX_SEARCH_COUNT= 3; // 약국 최대 검색 갯수 3개
   private static final double RADIUS_KH= 10.0; //반경 10km


    private final PharmacySearchService pharmacySearchService;
    private final DirectionRepository directionRepository;
    private final KakaoCategorySearchService kakaoCategorySearchService;

    @Transactional //데이터 변경이 있기 때문에 트랜잭션 처리
    public List<Direction> saveAll(List<Direction> directionList){
        if (CollectionUtils.isEmpty(directionList)) return Collections.emptyList();
        return directionRepository.saveAll(directionList);
    }

    public List<Direction> buildDirectionList(DocumentDto documentDto) {//최대3개 까지 반환

        //validation 체크
        if (Objects.isNull(documentDto)) { //documentDto가 null이면
            return Collections.emptyList();
        }

        //약국 데이터 조회(전부)
       //List<PharmacyDto> pharmacyDtos= pharmacySearchService.searchPharmacyDtoList();

        //거리계산 알고리즘 이용하여 ,고객과 약구 사이의 거리를
        //이거는 공공기관이다
        return pharmacySearchService.searchPharmacyDtoList()
                //map(pharmacyDto -> ) 이렇게 하면 약국 dto를 하나씩 순회하면서 진행을 한다
                .stream().map(pharmacyDto ->
                        Direction.builder()
                                .inputAddress(documentDto.getAddressName())
                                .inputLatitude(documentDto.getLatitude())
                                .inputLongitude(documentDto.getLongitude())
                                .targetPharmacyName(pharmacyDto.getPharmacyName())
                                .targetAddress(pharmacyDto.getPharmacyAddress())
                                .targetLatitude(pharmacyDto.getLatitude())
                                .targetLongitude(pharmacyDto.getLongitude())
                                .distance( //고객과 약국사이의 거리를 구한다 각각의 dto에서 위도경도를 가지고 와서 cal메서드에서 넣어서 처리해준다
                                        calculateDistance(documentDto.getLatitude(),documentDto.getLongitude(),
                                                pharmacyDto.getLatitude(),pharmacyDto.getLongitude())
                                )
                                .build())
                .filter(direction -> direction.getDistance() <= RADIUS_KH) //10km가 넘게되는걸 볼필요없기 때문에 이렇게 10km 이하만 볼수있게 선언한다
                .sorted(Comparator.comparing(Direction::getDistance)) //오름차순으로 정렬
                .limit(MAX_SEARCH_COUNT) //최대 3개 까지만 보면 된다
                .collect(Collectors.toList());


    }


    //이거는 kakao api그자체를 쓰는거다
    // pharmacy search by category kakao api
    public List<Direction> buildDirectionListByCategoryApi(DocumentDto inputDocumentDto) {
        if(Objects.isNull(inputDocumentDto)) return Collections.emptyList();

        return kakaoCategorySearchService
                .requestPharmacyCategorySearch(inputDocumentDto.getLatitude(), inputDocumentDto.getLongitude(), RADIUS_KH)
                .getDocumentList()
                .stream().map(resultDocumentDto ->
                        Direction.builder()
                                .inputAddress(inputDocumentDto.getAddressName())
                                .inputLatitude(inputDocumentDto.getLatitude())
                                .inputLongitude(inputDocumentDto.getLongitude())
                                .targetPharmacyName(resultDocumentDto.getPlaceName())
                                .targetAddress(resultDocumentDto.getAddressName())
                                .targetLatitude(resultDocumentDto.getLatitude())
                                .targetLongitude(resultDocumentDto.getLongitude())
                                .distance(resultDocumentDto.getDistance() * 0.001) //m단위로 전달하는걸 다시  km 단위로 변경
                                .build())
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }





    // Haversine formula 예시 보기
    //double lat1, double lon1 고객의 위도 경도 주소이다
    //double lat2, double lon2 약국의 위도 경도 주소이다
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }
}

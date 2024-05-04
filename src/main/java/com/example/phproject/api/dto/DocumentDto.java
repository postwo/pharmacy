package com.example.phproject.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentDto { //[ ] list로 전달됨

    @JsonProperty("place_name") //약국 카테고리를 설정했을경우 약국명을 전달해준다 //공공데이터는 성북구 되어있어서 그지역을 벗어나도 될수있게 api를 적용
    private String placeName;

    @JsonProperty("address_name")
    private String addressName;

    @JsonProperty("y") //kakao api json데이터 맵핑
    private double latitude; //위도 Y 좌표값, 경위도인 경우 latitude(위도)

    @JsonProperty("x")
    private double longitude; //경도 X 좌표값, 경위도인 경우 longitude (경도)

    @JsonProperty("distance") //카카오api에서 위도 경도 기준으로 가까운 약국을 찾아준다
    private double distance;
}

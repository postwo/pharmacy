package com.example.phproject.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDto { //[ ] list로 전달됨

    @JsonProperty("address_name")
    private String addressName;

    @JsonProperty("y") //kakao api json데이터 맵핑
    private double latitude; //위도 Y 좌표값, 경위도인 경우 latitude(위도)

    @JsonProperty("x")
    private double longitude; //경도 X 좌표값, 경위도인 경우 longitude (경도)
}

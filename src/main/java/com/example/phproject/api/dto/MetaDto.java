package com.example.phproject.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MetaDto {

    @JsonProperty("total_count") // 제이슨(카카오 api에서 받은)으로 받은 total_count를 totalcount하고 맵핑시켜주는용도이다
    private Integer totalcount;//몇건을 검색을 했는지
}

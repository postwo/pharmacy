package com.example.phproject.direction.controller

import com.example.phproject.direction.dto.OutputDto
import com.example.phproject.pharmacy.service.PharmacyRecommendationService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class FormControllerTest extends Specification {

    private MockMvc mockMvc;
    private PharmacyRecommendationService pharmacyRecommendationService;
    private List<OutputDto> outputDtoList

    def setup() {
        // FormController MockMvc 객체로 만든다.
        mockMvc = MockMvcBuilders.standaloneSetup(new FormController(pharmacyRecommendationService))
                .build()

        outputDtoList = new ArrayList<>()
        outputDtoList.addAll(
                OutputDto.builder()
                        .pharmacyName("pharmacy1")
                        .build(),
                OutputDto.builder()
                        .pharmacyName("pharmacy2")
                        .build()
        )
    }

    def "GET /"() {

        expect:
        // FormController 의 "/" URI를 get방식으로 호출
        mockMvc.perform(get("/"))
                .andExpect(handler().handlerType(FormController.class))
                .andExpect(handler().methodName("main"))
                .andExpect(status().isOk()) // 예상 값을 검증한다.
                .andExpect(view().name("main"))
                .andDo(log())
    }

    def "POST /search"() {

        given:
        String inputAddress = "서울 성북구 종암동"

        when:
        def resultActions = mockMvc.perform(post("/search")
                .param("address", inputAddress))

        then:
        // 1 * 이거는 한번만 호출한다는거다
        1 * pharmacyRecommendationService.recomendPharmacyList(argument -> {//argument 사용자에게받은주소
            assert argument == inputAddress // mock 객체의 argument 검증
            //사용자에게 받은주소와  String inputAddress = "서울 성북구 종암동" 이게 같은지 비교하는거다
        }) >> outputDtoList // >> outputDtoList = Mockito에서는 이를 "Stubbing"이라고 부르며, 메서드 호출에 대한 반환 값을 설정하는 데 사용

        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("output"))
                .andExpect(model().attributeExists("outputFormList")) // model에 outputFormList라는 key가 존재하는지 확인
                .andExpect(model().attribute("outputFormList", outputDtoList))
                .andDo(print())
    }
}
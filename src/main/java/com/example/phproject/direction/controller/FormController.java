package com.example.phproject.direction.controller;

import com.example.phproject.direction.dto.InputDto;
import com.example.phproject.pharmacy.service.PharmacyRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class FormController {
    private final PharmacyRecommendationService pharmacyRecommendationService; //추천 서비스

    @GetMapping("/")
    public String main() {
        return "main";
    }


    //@ModelAttribute InputDto inputDto 고객이 입력한 주소정보를 inputdto 담아서 가지고온다
    @PostMapping("/search")
    public ModelAndView postDirection(@ModelAttribute InputDto inputDto)  {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("output");
        modelAndView.addObject("outputFormList",
                pharmacyRecommendationService.recomendPharmacyList(inputDto.getAddress()));

        return modelAndView;
    }



}

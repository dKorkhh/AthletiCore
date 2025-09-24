package com.example.athleticore.controller;

import com.example.athleticore.entity.Session;
import com.example.athleticore.service.impl.calendar.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final HolidayService holidayService;

    @GetMapping("/")
    public String getHomePage(Model model) {
        model.addAttribute("sessions", List.of(
                Session.builder().name("some sport").description("dfgsdfgfd").build(),
                Session.builder()
                        .name("Basketball")
                        .description("A fast-paced game where two teams compete to score points by shooting a ball through a hoop.")
                        .build(),
                Session.builder()
                        .name("Tennis")
                        .description("A racket sport played individually or in doubles, hitting a ball over a net.")
                        .build(),
                Session.builder()
                        .name("Yoga")
                        .description("A practice combining physical poses, breathing techniques, and meditation for relaxation.")
                        .build(),
                Session.builder()
                        .name("Swimming")
                        .description("An individual or team sport involving racing in water using various strokes.")
                        .build()
        ));

        List<?> holidays = holidayService.getListOfHolidays();
        model.addAttribute("holidays", holidays);
        return "index";
    }
}

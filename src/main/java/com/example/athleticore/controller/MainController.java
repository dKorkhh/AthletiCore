package com.example.athleticore.controller;

import com.example.athleticore.service.impl.calendar.HolidayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final HolidayService holidayService;

    @Operation(summary = "Get start page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = {"/", ""})
    public String getHomePage(Model model) {
        List<?> holidays = holidayService.getListOfHolidays();
        model.addAttribute("holidays", holidays);
        return "index";
    }
}

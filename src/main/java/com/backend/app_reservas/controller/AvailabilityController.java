package com.backend.app_reservas.controller;

import com.backend.app_reservas.dto.AvailabilityDTO;
import com.backend.app_reservas.service.AvailabilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping
    public ResponseEntity<List<AvailabilityDTO>> getAvailability(@RequestParam String month) { // Formato "YYYY-MM"
        YearMonth yearMonth = YearMonth.parse(month);
        List<AvailabilityDTO> availabilities = availabilityService.getMonthlyAvailability(yearMonth);
        return ResponseEntity.ok(availabilities);
    }
}

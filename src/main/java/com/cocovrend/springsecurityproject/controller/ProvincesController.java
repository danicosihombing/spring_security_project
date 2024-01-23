package com.cocovrend.springsecurityproject.controller;

import com.cocovrend.springsecurityproject.model.respone.Provinces;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/api/provinsi")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProvincesController {
    private static final List<Provinces> PROVINCES = Arrays.asList(
            new Provinces("Nanggroe Aceh Darussalam", "Banda Aceh"),
            new Provinces("Sumatera Utara", "Medan"),
            new Provinces("Sumatera Selatan", "Palembang"),
            new Provinces("Sumatera Barat", "Padang"),
            new Provinces("Riau", "Pekanbaru")
    );

    @GetMapping(path = "/indonesia")
    public List<Provinces> getProvinces(){
        log.info("Success Get Provinces Data");
        return PROVINCES.stream()
                .collect(Collectors.toUnmodifiableList());
    }

}

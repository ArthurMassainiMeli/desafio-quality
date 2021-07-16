package com.meli.br.imoveis.controller;

import com.meli.br.imoveis.entity.District;
import com.meli.br.imoveis.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/district")
public class DistrictController {

    private final DistrictService districtService;

    @Autowired
    public DistrictController(DistrictService districtService) {
        this.districtService = districtService;
    }

    @PostMapping
    public ResponseEntity<District> save(@Valid @RequestBody District district){
        return ResponseEntity.status(HttpStatus.CREATED).body(districtService.save(district));
    }

}

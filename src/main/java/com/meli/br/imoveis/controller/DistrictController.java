package com.meli.br.imoveis.controller;

import com.meli.br.imoveis.entity.District;
import com.meli.br.imoveis.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public District save(@Valid @RequestBody District district){
        return districtService.save(district);
    }

}

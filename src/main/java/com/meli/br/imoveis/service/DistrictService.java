package com.meli.br.imoveis.service;

import com.meli.br.imoveis.entity.District;
import com.meli.br.imoveis.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

@Service
public class DistrictService {

    private final DistrictRepository districtRepository;

    @Autowired
    public DistrictService(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    public District save(District district) {

        if (!districtRepository.existsById(district.getName())) {
            return districtRepository.save(district);
        }
        throw new EntityExistsException("Bairro já cadastrado");
    }
}

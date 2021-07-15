package com.meli.br.imoveis.service;

import com.meli.br.imoveis.entity.District;
import com.meli.br.imoveis.entity.Property;
import com.meli.br.imoveis.entity.Room;
import com.meli.br.imoveis.repository.DistrictRepository;
import com.meli.br.imoveis.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.NoSuchElementException;

@Service
public class PropertyService {


    private final PropertyRepository propertyRepository;
    private final RoomService roomService;
    private final DistrictRepository districtRepository;

    @Autowired
    public PropertyService(PropertyRepository propertyRepository, RoomService roomService, DistrictRepository districtRepository) {
        this.propertyRepository = propertyRepository;
        this.roomService = roomService;
        this.districtRepository = districtRepository;
    }

    public BigDecimal calcArea(Property property){
        double s = property.getRoomList().stream().mapToDouble(roomService::calcArea).sum();
        return BigDecimal.valueOf(s);
    }

    public BigDecimal valueProperty(Property property){

        District district = districtRepository.findById(property.getPropDistrict()).orElseThrow(NoSuchElementException::new);
        return district.getValueDistrictM2().multiply(calcArea(property));

    }

    public Room biggestRoom(Property property){
        return property.getRoomList().stream().max(Comparator.comparing(roomService::calcArea)).orElseThrow(NoSuchElementException::new);
    }

    public boolean isValidDistrict(Property property){

        districtRepository.findById(property.getPropDistrict()).orElseThrow(NoSuchElementException::new);

        return true;
    }

}

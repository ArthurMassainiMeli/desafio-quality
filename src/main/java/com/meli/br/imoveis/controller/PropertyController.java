package com.meli.br.imoveis.controller;

import com.meli.br.imoveis.dto.RoomTotalDTO;
import com.meli.br.imoveis.entity.Property;
import com.meli.br.imoveis.entity.Room;
import com.meli.br.imoveis.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/property")
public class PropertyController {

    private final PropertyService service;

    @Autowired
    public PropertyController(PropertyService service) {
        this.service = service;
    }

    @PostMapping("/area")
    public ResponseEntity<BigDecimal> totalMeterProperty(@Valid @RequestBody Property property){
        return ResponseEntity.ok().body(service.calcArea(property));
    }

    @PostMapping("/value")
    public ResponseEntity<BigDecimal> totalValue(@Valid @RequestBody Property property){
        return ResponseEntity.ok().body(service.valueProperty(property));
    }

    @PostMapping("/biggest")
    public ResponseEntity<Room> maxRoom(@Valid @RequestBody Property property){
        return ResponseEntity.ok().body(service.biggestRoom(property));
    }

    @PostMapping("/rooms")
    public ResponseEntity<List<RoomTotalDTO>> roomsAreas(@Valid @RequestBody Property property){
        return ResponseEntity.ok().body(service.getRoomsWithAreaTotal(property));
    }

}

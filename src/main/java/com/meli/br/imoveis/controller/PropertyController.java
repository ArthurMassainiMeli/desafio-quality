package com.meli.br.imoveis.controller;

import com.meli.br.imoveis.dto.PropertyDTO;
import com.meli.br.imoveis.dto.RoomTotalDTO;
import com.meli.br.imoveis.entity.Property;
import com.meli.br.imoveis.entity.Room;
import com.meli.br.imoveis.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @ResponseStatus(HttpStatus.OK)
    public PropertyDTO totalMeterProperty(@Valid @RequestBody Property property) {
        BigDecimal area = service.calcArea(property);
        return new PropertyDTO(area, null);
    }

    @PostMapping("/value")
    @ResponseStatus(HttpStatus.OK)
    public PropertyDTO totalValue(@Valid @RequestBody Property property) {
        BigDecimal value = service.valueProperty(property);
        return new PropertyDTO(null, value);
    }

    @PostMapping("/biggest")
    public ResponseEntity<Room> maxRoom(@Valid @RequestBody Property property) {
        return ResponseEntity.ok().body(service.biggestRoom(property));
    }

    @PostMapping("/rooms")
    public ResponseEntity<List<RoomTotalDTO>> roomsAreas(@Valid @RequestBody Property property) {
        return ResponseEntity.ok().body(service.getRoomsWithAreaTotal(property));
    }

}

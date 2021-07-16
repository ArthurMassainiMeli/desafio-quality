package com.meli.br.imoveis.service;

import com.meli.br.imoveis.dto.RoomTotalDTO;
import com.meli.br.imoveis.entity.Room;
import com.meli.br.imoveis.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public double calcArea(Room r) {
        return r.getRoomWidth() * r.getRoomLength();
    }

    public List<RoomTotalDTO> getRoomsWithAreaTotal(List<Room> rooms){
        return rooms.stream().map(r -> RoomTotalDTO.convert(r, BigDecimal.valueOf(calcArea(r)))).collect(Collectors.toList());
    }

}

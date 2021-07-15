package com.meli.br.imoveis.dto;

import com.meli.br.imoveis.entity.Room;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class RoomTotalDTO {

    private String roomName;
    private BigDecimal totalArea;

    public RoomTotalDTO(String roomName, BigDecimal totalArea) {
        this.roomName = roomName;
        this.totalArea = totalArea;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public BigDecimal getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(BigDecimal totalArea) {
        this.totalArea = totalArea;
    }

    public static RoomTotalDTO convert(Room room, BigDecimal areaTotal){
        return new RoomTotalDTO(room.getRoomName(),areaTotal);
    }
}

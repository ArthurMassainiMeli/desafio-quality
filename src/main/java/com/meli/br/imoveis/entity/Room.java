package com.meli.br.imoveis.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Validated
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @NotBlank(message = "O campo não pode estar vazio.")
    @Pattern(regexp = "^[A-Z]{1}.+", message = "O nome do cômodo deve começar com uma letra maiúscula.")
    @Size(max = 30, message = "O nome do cômodo não pode exceder 30 caracteres.")
    @JsonAlias("room_name")
    private String roomName;

    @NotNull(message = "A largura do cômodo não pode estar vazia.")
    @Max(value = 25, message = "A largura máxima permitida por cômodo é de 25 metros.")
    @JsonAlias("room_width")
    private double roomWidth;

    @NotNull(message = "O comprimento do cômodo não pode estar vazio.")
    @Max(value = 33, message = "O comprimento máximo permitido por cômodo é de 33 metros.")
    @JsonAlias("room_length")
    private double roomLength;

    @ManyToOne
    @JsonIgnore
    private Property property;

    public Room(String roomName, double roomWidth, double roomLength) {
        this.roomName = roomName;
        this.roomWidth = roomWidth;
        this.roomLength = roomLength;
    }

    public Room() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public double getRoomWidth() {
        return roomWidth;
    }

    public void setRoomWidth(double roomWidth) {
        this.roomWidth = roomWidth;
    }

    public double getRoomLength() {
        return roomLength;
    }

    public void setRoomLength(double roomLength) {
        this.roomLength = roomLength;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

}

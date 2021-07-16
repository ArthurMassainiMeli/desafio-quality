package com.meli.br.imoveis.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @NotBlank(message = "O nome da propriedade não pode estar vazio.")
    @Size(max = 30, message = "O comprimento do nome não pode exceder 30 caracteres")
    @Pattern(regexp = "^[A-Z]{1}.+", message = "O nome da propriedade deve começar com uma letra maiúscula.")
    @JsonAlias("prop_name")
    private String propName;

    @NotBlank(message = "O bairro não pode estar vazio.")
    @Size(max = 30, message = "O comprimento do nome não pode exceder 45 caracteres")
    @JsonAlias("prop_district")
    private String propDistrict;

    @NotNull
    @Valid
    @OneToMany(targetEntity = Room.class)
    private List<Room> roomList;


    public Property(String propertyName, String propetyDistrict, List<Room> roomList) {
        this.propName = propertyName;
        this.propDistrict = propetyDistrict;
        this.roomList = roomList;
    }

    public Property() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getPropDistrict() {
        return propDistrict;
    }

    public void setPropDistrict(String propDistrict) {
        this.propDistrict = propDistrict;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

}

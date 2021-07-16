package com.meli.br.imoveis.entity;

import com.fasterxml.jackson.annotation.JsonAlias;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
public class District {

    @Id
    @NotNull
    private String name;

    @NotNull(message = "Que o valor do metro quadrado no bairro não pode estar vazio.")
    @DecimalMax(value = "99999999999.99",message = "O valor do metro quadrado do bairro não pode exceder 13 digitos.")
    @DecimalMin(value = "0.0",message = "O valor do metro quadrado do bairro não menor que 2 digitos.")
    @JsonAlias("value_district_m2")
    private BigDecimal valueDistrictM2;

    public District(String district, BigDecimal valueDistrictM2) {
        name = district;
        this.valueDistrictM2 = valueDistrictM2;
    }

    public District() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValueDistrictM2() {
        return valueDistrictM2;
    }

    public void setValueDistrictM2(BigDecimal valueDistrictM2) {
        this.valueDistrictM2 = valueDistrictM2;
    }
}

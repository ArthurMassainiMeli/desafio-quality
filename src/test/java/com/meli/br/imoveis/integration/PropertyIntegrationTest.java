package com.meli.br.imoveis.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.br.imoveis.dto.RoomTotalDTO;
import com.meli.br.imoveis.entity.District;
import com.meli.br.imoveis.entity.Property;
import com.meli.br.imoveis.entity.Room;
import com.meli.br.imoveis.repository.DistrictRepository;
import com.meli.br.imoveis.service.PropertyService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PropertyIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private PropertyService propertyService;


    @BeforeAll
    public void setUpDistrict() throws Exception {
        District district = new District("NORTH", BigDecimal.valueOf(100.0));
        String jsonDistrict = mapper.writeValueAsString(district);

        mockMvc.perform(
                post("/district")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonDistrict)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
    }

    @AfterAll
    public void restoreDb(){
        districtRepository.deleteAll();
    }


    @Test
    @DisplayName("O endpoint /property/area deve retornar a área corretamente.")
    public void shouldComputeArea() throws Exception {
        Property property = createProperty();
        String json = mapper.writeValueAsString(property);

        mockMvc.perform(
                post("/property/area")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.area").value(506.0));
    }

    @Test
    @DisplayName("O endpoint /property/value deve retornar o valor da propiedade corretamente.")
    public void shouldReturnCorrectValueOfProperty() throws Exception {
        Property property = createProperty();
        String json = mapper.writeValueAsString(property);

        mockMvc.perform(
                post("/property/value")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(50600.000));
    }

    @Test
    @DisplayName("O endpoint /property/biggest deve retornar o maior cômodo.")
    public void shouldReturnTheBiggestRoom() throws Exception {
        Property property = createProperty();
        String json = mapper.writeValueAsString(property);

        mockMvc.perform(
                post("/property/biggest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomName").value("Living Room"))
                .andExpect(jsonPath("$.roomWidth").value(14.0))
                .andExpect(jsonPath("$.roomLength").value(14.0));
    }

    @Test
    @DisplayName("O endpoint /property/rooms deve retornar uma lista de cômodos com sua área.")
    public void shouldReturnListOfRoomsWithArea() throws Exception {
        Property property = createProperty();
        String json = mapper.writeValueAsString(property);

       List<RoomTotalDTO> list = propertyService.getRoomsWithAreaTotal(property);
       String jsonList = mapper.writeValueAsString(list);

        mockMvc.perform(
                post("/property/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(jsonList));
    }

    private Property createProperty() {
        List<Room> rooms = List.of(
                new Room("Living Room", 14.0, 14.0),
                new Room("Kitchen", 15.0, 10.0),
                new Room("Bedroom", 16.0, 10.0)
        );
        return new Property("Jhon's house", "NORTH", rooms);
    }

}

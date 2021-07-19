package com.meli.br.imoveis.unit;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.br.imoveis.dto.RoomTotalDTO;
import com.meli.br.imoveis.entity.Property;
import com.meli.br.imoveis.entity.Room;
import com.meli.br.imoveis.service.PropertyService;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PropertyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PropertyService propertyService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("O endpoint /property/area deve retornar o status 200.")
    public void shouldComputeArea() throws Exception {
        Property property = createProperty();
        String json = mapper.writeValueAsString(property);

        when(propertyService.calcArea(any())).thenReturn(new BigDecimal("450.0"));

        mockMvc.perform(post("/property/area")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.area").value("450.0"));

    }

    @Test
    @DisplayName("O endpoint /property/value deve retornar o status 200.")
    public void shouldComputeValue() throws Exception {
        Property property = createProperty();
        String json = mapper.writeValueAsString(property);

        when(propertyService.valueProperty(any())).thenReturn(new BigDecimal("45000.0"));

        mockMvc.perform(post("/property/value")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.value").value("45000.0"));
    }

    @Test
    @DisplayName("O endpoint /property/biggest deve retornar o status 200.")
    public void shouldFindBiggestRoom() throws Exception {
        Property property = createProperty();
        String json = mapper.writeValueAsString(property);

        when(propertyService.biggestRoom(any())).thenReturn(property.getRoomList().get(2));

        mockMvc.perform(post("/property/biggest")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.roomName").value("Bedroom"))
                .andExpect(jsonPath("$.roomWidth").value(16.0))
                .andExpect(jsonPath("$.roomLength").value(10.0));
    }

    @Test
    @DisplayName("O endpoint /property/rooms deve retornar o status 200.")
    public void shouldFindAreasRoom() throws Exception {
        Property property = createProperty();
        List<RoomTotalDTO> roomsTotal = createListRoomTotal();
        String json = mapper.writeValueAsString(property);

        when(propertyService.getRoomsWithAreaTotal(any())).thenReturn(roomsTotal);
        String jsonList = mapper.writeValueAsString(roomsTotal);

        mockMvc.perform(post("/property/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk())
                .andExpect(content().json(jsonList));

    }

    private Property createProperty() {
        List<Room> rooms = List.of(
                new Room("Living Room",14.0,10.0),
                new Room("Kitchen",15.0,10.0),
                new Room("Bedroom",16.0,10.0)
        );

        return new Property("Jhon's house","NORTH",rooms);
    }

    private List<RoomTotalDTO> createListRoomTotal() {
        return List.of(
                new RoomTotalDTO("Living Room", new BigDecimal("140.0")),
                new RoomTotalDTO("Kitchen", new BigDecimal("150.0")),
                new RoomTotalDTO("Bedroom", new BigDecimal("160.0"))
        );
    }

}

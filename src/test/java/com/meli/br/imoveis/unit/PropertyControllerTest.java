package com.meli.br.imoveis.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.br.imoveis.dto.RoomTotalDTO;
import com.meli.br.imoveis.entity.Property;
import com.meli.br.imoveis.entity.Room;
import com.meli.br.imoveis.service.PropertyService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class PropertyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PropertyService propertyService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldComputeArea() throws Exception {
        Property property = createProperty();
        String json = mapper.writeValueAsString(property);

        when(propertyService.calcArea(property)).thenReturn(new BigDecimal("450.0"));

        mockMvc.perform(post("/property/area")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void shouldComputeValue() throws Exception {
        Property property = createProperty();
        String json = mapper.writeValueAsString(property);

        when(propertyService.valueProperty(property)).thenReturn(new BigDecimal("45000.000"));

        mockMvc.perform(post("/property/value")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk());
    }

    @Test
    public void shouldFindBiggestRoom() throws Exception {
        Property property = createProperty();
        String json = mapper.writeValueAsString(property);

        when(propertyService.biggestRoom(property)).thenReturn(property.getRoomList().get(2));

        mockMvc.perform(post("/property/biggest")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk());
    }

    @Test
    public void shouldFindAreasRoom() throws Exception {
        Property property = createProperty();
        List<RoomTotalDTO> roomsTotal = createListRoomTotal();
        String json = mapper.writeValueAsString(property);

        when(propertyService.getRoomsWithAreaTotal(property)).thenReturn(roomsTotal);

        mockMvc.perform(post("/property/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk());
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

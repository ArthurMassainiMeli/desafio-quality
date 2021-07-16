package com.meli.br.imoveis.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.br.imoveis.dto.RoomTotalDTO;
import com.meli.br.imoveis.entity.Property;
import com.meli.br.imoveis.entity.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PropertyIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldComputeArea() throws Exception {
        Property property = createProperty();
        String json = mapper.writeValueAsString(property);


        mockMvc.perform(post("/property/area")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }


    private Property createProperty() {
        List<Room> rooms = List.of(
                new Room("Living Room", 14.0, 10.0),
                new Room("Kitchen", 15.0, 10.0),
                new Room("Bedroom", 16.0, 10.0)
        );
        return new Property("Jhon's house", "NORTH", rooms);
    }

}

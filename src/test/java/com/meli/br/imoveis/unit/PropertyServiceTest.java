package com.meli.br.imoveis.unit;

import com.meli.br.imoveis.entity.District;
import com.meli.br.imoveis.entity.Property;
import com.meli.br.imoveis.entity.Room;
import com.meli.br.imoveis.repository.DistrictRepository;
import com.meli.br.imoveis.repository.PropertyRepository;
import com.meli.br.imoveis.service.PropertyService;
import com.meli.br.imoveis.service.RoomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@SpringBootTest
public class PropertyServiceTest {

    private PropertyService propertyService;

    @MockBean
    private RoomService roomService;

    @MockBean
    private PropertyRepository propertyRepository;

    @MockBean
    private DistrictRepository districtRepository;

    Property property;

    List<Room> rooms;

    @BeforeEach
    public void setup(){
        propertyService = new PropertyService(propertyRepository,roomService, districtRepository);

        rooms = List.of(
                new Room("Test",14.0,10.0),
                new Room("Test",15.0,10.0),
                new Room("Test",16.0,10.0)
        );

        property = new Property("Test","NORTH",rooms);

    }


    @Test
    @DisplayName("Dada uma propiedade retorna a área total.")
    public void givenPropertyWithRoomsShouldReturnTotalArea(){

        Mockito.when(districtRepository.existsById(property.getPropDistrict())).thenReturn(true);

        Mockito.when(roomService.calcArea(rooms.get(0))).thenReturn(140.0);

        Mockito.when(roomService.calcArea(rooms.get(1))).thenReturn(150.0);

        Mockito.when(roomService.calcArea(rooms.get(2))).thenReturn(160.0);



        BigDecimal area = propertyService.calcArea(property);

        Assertions.assertEquals(new BigDecimal("450.0"),area);

    }

    @Test
    @DisplayName("Retorna area total 0 se a propiedade não tiver salas.")
    public void givenPropertyWithoutRoomsShouldReturnTotalArea(){

        Mockito.when(districtRepository.existsById(property.getPropDistrict())).thenReturn(true);

        List<Room> rooms = List.of();

        Property property = new Property("Test","NORTH",rooms);

        BigDecimal area = propertyService.calcArea(property);

        Assertions.assertEquals(new BigDecimal("0.0"),area);

    }

    @Test
    @DisplayName("Valida se um bairro está cadastrado no repositorio.")
    public void successWhenDistrictExistInRepository(){

        Mockito.when(districtRepository.existsById(property.getPropDistrict())).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> propertyService.validateDistrict(property));

    }

    @Test
    @DisplayName("Retorna um erro se o bairro não está cadastrado no repositorio.")
    public void failWhenDistrictNotExistInRepository(){

        Mockito.when(districtRepository.existsById(property.getPropDistrict())).thenReturn(false);


        Assertions.assertThrows(NoSuchElementException.class,() -> propertyService.validateDistrict(property));

    }

    @Test
    @DisplayName("Retorna o maior comôdo de uma propriedade.")
    public void shouldReturnTheBiggestRoom(){

        Mockito.when(districtRepository.existsById(property.getPropDistrict())).thenReturn(true);

        Mockito.when(roomService.calcArea(rooms.get(0))).thenReturn(140.0);

        Mockito.when(roomService.calcArea(rooms.get(1))).thenReturn(150.0);

        Mockito.when(roomService.calcArea(rooms.get(2))).thenReturn(160.0);

        Room room = propertyService.biggestRoom(property);

        Assertions.assertEquals(property.getRoomList().get(2),room);
    }

    @Test
    @DisplayName("Retorna o valor da propiedade por metros quadrados.")
    public void successWhenCauclValuePerMeter(){

        Mockito.when(districtRepository.getById(property.getPropDistrict())).thenReturn(new District("NORTH", new BigDecimal("100.00")));

        Mockito.when(districtRepository.existsById(property.getPropDistrict())).thenReturn(true);

        Mockito.when(roomService.calcArea(rooms.get(0))).thenReturn(140.0);

        Mockito.when(roomService.calcArea(rooms.get(1))).thenReturn(150.0);

        Mockito.when(roomService.calcArea(rooms.get(2))).thenReturn(160.0);

        BigDecimal value = propertyService.valueProperty(property);
        Assertions.assertEquals( new BigDecimal("45000.000"),value);

    }

}

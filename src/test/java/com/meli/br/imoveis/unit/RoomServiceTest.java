package com.meli.br.imoveis.unit;

import com.meli.br.imoveis.entity.Room;
import com.meli.br.imoveis.service.RoomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RoomServiceTest {

    @InjectMocks
    private RoomService roomService;


    @Test
    public void givenRoomShouldReturnTotalArea(){

        Room room = new Room("Test",14.0,10.0);

        Assertions.assertEquals(140.0,roomService.calcArea(room));

    }

}

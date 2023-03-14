package com.otsa.useridentificator.mapper;

import com.otsa.useridentificator.dto.UserDto;
import com.otsa.useridentificator.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserDtoMapperTest {

    @Autowired
    private UserDtoMapper mapper;

    @Test
    public void testToDto() throws Exception {
        User userEntity = new User("Phillipe", LocalDate.of(2015, 4, 23), "FR");
        UserDto userDto = mapper.toDto(userEntity);

        assertNotNull(userDto);
        assertEquals(userEntity.getName(), userDto.getName());
        assertEquals(userEntity.getBirthdate().toString(), userDto.getBirthdate().toString());
        assertEquals(userEntity.getCountry(), userDto.getCountry());
    }

    @Test
    public void testToEntity() throws Exception {
        UserDto userDto = new UserDto("Phillipe", LocalDate.of(2015, 4, 23), "FR");
        User userEntity = mapper.toEntity(userDto);

        assertNotNull(userEntity);
        assertEquals(userDto.getId(), userEntity.getId());
        assertEquals(userDto.getName(), userEntity.getName());
        assertEquals(userDto.getBirthdate().toString(), userEntity.getBirthdate().toString());
        assertEquals(userDto.getCountry(), userEntity.getCountry());
    }

    @Test
    public void testUsersToUserDtos() throws Exception {
        User userEntity1 = new User("Phillipe", LocalDate.of(2015, 4, 23), "FR");
        User userEntity2 = new User("Rodrigez", LocalDate.of(1995, 4, 23), "FR");

        List<User> list = List.of(userEntity1, userEntity2);
        List<UserDto> userDtoList = mapper.usersToUserDtos(list);

        assertNotNull(userDtoList);
        assertEquals(2, userDtoList.size());
    }
}


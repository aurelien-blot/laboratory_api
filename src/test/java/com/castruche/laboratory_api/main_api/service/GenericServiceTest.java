package com.castruche.laboratory_api.main_api.service;

import com.castruche.laboratory_api.main_api.formatter.IFormatter;
import com.castruche.laboratory_api.myworld_api.dto.user.UserDto;
import com.castruche.laboratory_api.myworld_api.dto.user.UserLightDto;
import com.castruche.laboratory_api.myworld_api.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GenericServiceTest {
    @Mock
    JpaRepository<User, Long> repository;
    @Mock
    IFormatter<User, UserDto, UserLightDto> formatter;

    GenericService<User, UserDto, UserLightDto> service;

    @BeforeEach
    void setup() {
        service = new GenericService<>(repository, formatter) {};
    }

    @Test
    void selectById_shouldReturnNull_whenIdIsNull() {
        Assertions.assertNull(service.selectById(null));
        verify(repository, never()).findById(any());
    }

    @Test
    void create_shouldSaveEntityAndReturnDto() {
        String username = "Ginette";
        UserDto dto = new UserDto();
        dto.setUsername(username);
        dto.setId(1L);
        User entity = new User();
        entity.setId(1L);
        entity.setUsername("Ginette");

        when(formatter.dtoToEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(formatter.entityToDto(entity)).thenReturn(dto);

        UserDto result = service.create(dto);

        Assertions.assertEquals(username, result.getUsername());
        verify(repository).save(entity);
    }

    @Test
    void test(){
        assert (1+1==3);
    }
}

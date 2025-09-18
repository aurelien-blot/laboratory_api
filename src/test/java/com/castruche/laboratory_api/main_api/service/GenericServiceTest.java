package com.castruche.laboratory_api.main_api.service;

import com.castruche.laboratory_api.main_api.formatter.IFormatter;
import com.castruche.laboratory_api.myworld_api.dto.user.UserDto;
import com.castruche.laboratory_api.myworld_api.dto.user.UserLightDto;
import com.castruche.laboratory_api.myworld_api.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
        GenericService<User, UserDto, UserLightDto> real =
                new GenericService<>(repository, formatter) {};
        service = spy(real);
    }

    @Test
    void selectById_shouldReturnNull_andNotTouchRepo_whenIdIsNull() {
        User result = service.selectById(null);
        assertThat(result).isNull();
        verifyNoInteractions(repository);
    }

    @Test
    void selectById_shouldReturnEntity_whenFound() {
        User entity = new User();
        entity.setId(1L);
        entity.setUsername("Test");
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        User result = service.selectById(1L);

        assertThat(result).isSameAs(entity);
        verify(repository).findById(1L);
    }

    @Test
    void selectById_shouldReturnNull_whenNotFound() {
        when(repository.findById(-1L)).thenReturn(Optional.empty());

        User result = service.selectById(-1L);

        assertThat(result).isNull();
        verify(repository).findById(-1L);
    }

    @Test
    void selectDtoById_shouldReturnNull_andCallFormatterWithNull_whenIdIsNull() {
        User test = null;
        when(formatter.entityToDto(test)).thenReturn(null);

        UserDto result = service.selectDtoById(null);

        assertThat(result).isNull();
        verify(formatter).entityToDto(test);
        verifyNoInteractions(repository);
    }
    @Test
    void selectDtoById_shouldMapEntityToDto_whenFound() {
        User entity = new User();
        entity.setId(2L);
        entity.setUsername("Test");
        UserDto dto = new UserDto();
        dto.setId(2L);
        dto.setUsername("Test");

        when(repository.findById(2L)).thenReturn(Optional.of(entity));
        when(formatter.entityToDto(entity)).thenReturn(dto);

        UserDto result = service.selectDtoById(2L);

        assertThat(result).isSameAs(dto);
        verify(repository).findById(2L);
        verify(formatter).entityToDto(entity);
    }

    @Test
    void getAll_shouldReturnRepoFindAll() {
        List<User> entities = List.of(new User(1L, "A"), new User(2L, "B"));
        when(repository.findAll()).thenReturn(entities);

        List<User> result = service.getAll();

        assertThat(result).isEqualTo(entities);
        verify(repository).findAll();
        verifyNoInteractions(formatter);
    }

    @Test
    void getAllDto_shouldMapEntitiesToDtos() {
        List<User> entities = List.of(new User(1L, "A"), new User(2L, "B"));
        List<UserDto> dtos = List.of(new UserDto(1L, "A"), new UserDto(2L, "B"));
        when(repository.findAll()).thenReturn(entities);
        when(formatter.entityToDto(entities)).thenReturn(dtos);

        List<UserDto> result = service.getAllDto();

        assertThat(result).isEqualTo(dtos);
        verify(repository).findAll();
        verify(formatter).entityToDto(entities);
    }

    @Test
    void getAllDtoLight_shouldMapEntitiesToLightDtos() {
        List<User> entities = List.of(new User(1L, "A"), new User(2L, "B"));
        List<UserLightDto> light = List.of(new UserLightDto(1L, "A"), new UserLightDto(2L, "B"));
        when(repository.findAll()).thenReturn(entities);
        when(formatter.entityToLightDto(entities)).thenReturn(light);

        List<UserLightDto> result = service.getAllDtoLight();

        assertThat(result).isEqualTo(light);
        verify(repository).findAll();
        verify(formatter).entityToLightDto(entities);
    }


    @Test
    void selectByIdIn_shouldDelegateToRepository() {
        List<Long> ids = List.of(1L, 2L, 3L);
        List<User> entities = List.of(new User(1L, "A"), new User(2L, "B"));
        when(repository.findAllById(ids)).thenReturn(entities);

        List<User> result = service.selectByIdIn(ids);

        assertThat(result).isEqualTo(entities);
        verify(repository).findAllById(ids);
        verifyNoInteractions(formatter);
    }

    @Test
    void selectDtoByIdIn_shouldMapList() {
        List<Long> ids = List.of(1L, 2L);
        List<User> entities = List.of(new User(1L, "A"), new User(2L, "B"));
        List<UserDto> dtos = List.of(new UserDto(1L, "A"), new UserDto(2L, "B"));

        when(repository.findAllById(ids)).thenReturn(entities);
        when(formatter.entityToDto(entities)).thenReturn(dtos);

        List<UserDto> result = service.selectDtoByIdIn(ids);

        assertThat(result).isEqualTo(dtos);
        verify(repository).findAllById(ids);
        verify(formatter).entityToDto(entities);
    }

    @Test
    void create_shouldConvert_Save_CallHook_thenReturnDto() {
        UserDto input = new UserDto(null, "X");
        User toSave = new User(null, "X");
        User saved = new User(10L, "X");
        UserDto output = new UserDto(10L, "X");

        when(formatter.dtoToEntity(input)).thenReturn(toSave);
        when(repository.save(toSave)).thenReturn(saved);
        when(formatter.entityToDto(saved)).thenReturn(output);

        UserDto result = service.create(input);

        assertThat(result).isEqualTo(output);
        // Vérifie la séquence
        InOrder inOrder = inOrder(formatter, repository, service);
        inOrder.verify(formatter).dtoToEntity(input);
        inOrder.verify(repository).save(toSave);
        inOrder.verify(service).postCommonAction(input, saved);
        inOrder.verify(formatter).entityToDto(saved);
    }

    @Test
    public void createAll_ShouldSaveAllEntities() {
        List<User> entities = List.of(new User(1L, "A"), new User(2L, "B"));

        when(repository.saveAll(entities)).thenReturn(entities);

        List<User> result = service.createAll(entities);

        assertThat(result).isEqualTo(entities);
        verify(repository).saveAll(entities);
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(formatter);
    }

    @Test
    void update_shouldLoad_Update_Save_thenReturnDto() {
        UserDto input = new UserDto(42L, "new");
        User existing = new User(42L, "old");
        User updated = new User(42L, "new");
        UserDto output = new UserDto(42L, "new");

        when(repository.findById(42L)).thenReturn(Optional.of(existing));
        when(formatter.updateEntityFromDto(existing, input)).thenReturn(updated);
        when(repository.save(updated)).thenReturn(updated);
        when(formatter.entityToDto(updated)).thenReturn(output);

        UserDto result = service.update(input);

        assertThat(result).isEqualTo(output);
        InOrder inOrder = inOrder(repository, formatter);
        inOrder.verify(repository).findById(42L);
        inOrder.verify(formatter).updateEntityFromDto(existing, input);
        inOrder.verify(repository).save(updated);
        inOrder.verify(formatter).entityToDto(updated);
    }

    @Test
    void delete_shouldDelegateToRepository() {
        service.delete(7L);
        verify(repository).deleteById(7L);
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(formatter);
    }
}

package com.castruche.laboratory_api.main_api.service;

import com.castruche.laboratory_api.main_api.formatter.IFormatter;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class GenericService<ENTITY, DTO, LIGHT_DTO> {

    private final JpaRepository<ENTITY, Long> repository;
    private final IFormatter<ENTITY, DTO, LIGHT_DTO> formatter;

    public GenericService(JpaRepository<ENTITY, Long> repository, IFormatter<ENTITY, DTO, LIGHT_DTO> formatter) {
        this.repository = repository;
        this.formatter = formatter;
    }

    public ENTITY selectById(Long id) {
        if(id == null){
            return null;
        }
        return repository.findById(id).orElse(null);
    }
    public DTO selectDtoById(Long id) {
        ENTITY entity = this.selectById(id);
        return formatter.entityToDto(entity);
    }

    public List<ENTITY> getAll() {
        return repository.findAll();
    }


    public List<DTO> getAllDto() {
        List<ENTITY> entities = this.getAll();
        return formatter.entityToDto(entities);
    }

    public List<LIGHT_DTO> getAllDtoLight() {
        List<ENTITY> entities = this.getAll();
        return formatter.entityToLightDto(entities);
    }

    public List<ENTITY> selectByIdIn(List<Long> idList) {
        return repository.findAllById(idList);
    }

    public List<DTO> selectDtoByIdIn(List<Long> idList) {
        List<ENTITY> entities = this.selectByIdIn(idList);
        return formatter.entityToDto(entities);
    }

    @Transactional
    public DTO create(DTO dto) {
        ENTITY entity = formatter.dtoToEntity(dto);
        entity = repository.save(entity);
        postCommonAction(dto, entity);
        return formatter.entityToDto(entity);
    }

    @Transactional
    public DTO update(DTO dto) {
        ENTITY entity = formatter.dtoToEntity(dto);
        entity = repository.save(entity);
        return formatter.entityToDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public void postCommonAction(DTO dto, ENTITY entity){
        return;
    }
}

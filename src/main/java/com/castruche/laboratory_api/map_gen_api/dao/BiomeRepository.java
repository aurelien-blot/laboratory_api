package com.castruche.laboratory_api.map_gen_api.dao;

import com.castruche.laboratory_api.map_gen_api.entity.map.Biome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BiomeRepository extends JpaRepository<Biome, Long> {

    Biome findByTechnicalName(String technicalName);
}

package com.castruche.laboratory_api.map_gen_api.dao;

import com.castruche.laboratory_api.map_gen_api.entity.map.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapRepository extends JpaRepository<Map, Long> {

}

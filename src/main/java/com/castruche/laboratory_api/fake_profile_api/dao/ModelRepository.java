package com.castruche.laboratory_api.fake_profile_api.dao;

import com.castruche.laboratory_api.fake_profile_api.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {


}

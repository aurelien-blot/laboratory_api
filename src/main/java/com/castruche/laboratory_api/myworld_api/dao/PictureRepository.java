package com.castruche.laboratory_api.myworld_api.dao;

import com.castruche.laboratory_api.myworld_api.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

}

package com.angrysurfer.social.shrapnel.services.repository;

import com.angrysurfer.social.shrapnel.services.model.ColumnSpecModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ColumnSpecModelRepository extends JpaRepository<ColumnSpecModel, Long> {

    ColumnSpecModel findByName(String name);
}
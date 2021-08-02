package com.angrysurfer.social.shrapnel.service.repository;

import com.angrysurfer.social.shrapnel.service.model.DataSourceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSourceModelRepository extends JpaRepository<DataSourceModel, Long> {
    DataSourceModel findByName(String name);
}

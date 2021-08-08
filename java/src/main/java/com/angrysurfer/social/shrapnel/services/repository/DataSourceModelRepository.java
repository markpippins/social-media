package com.angrysurfer.social.shrapnel.services.repository;

import com.angrysurfer.social.shrapnel.services.model.DataSourceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSourceModelRepository extends JpaRepository<DataSourceModel, Long> {
    DataSourceModel findByName(String name);
}

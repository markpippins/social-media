package com.angrysurfer.social.shrapnel.export.service.repository;

import com.angrysurfer.social.shrapnel.export.service.model.DataSource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSourceRepository extends JpaRepository<DataSource, Long> {
    DataSource findByName(String name);
}

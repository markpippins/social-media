package com.angrysurfer.social.shrapnel.services.repository;

import com.angrysurfer.social.shrapnel.services.model.DataSource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSourceRepository extends JpaRepository<DataSource, Long> {
    DataSource findByName(String name);
}

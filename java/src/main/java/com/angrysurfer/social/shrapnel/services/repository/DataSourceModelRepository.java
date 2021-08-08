package com.angrysurfer.social.shrapnel.services.repository;

import com.angrysurfer.social.shrapnel.services.model.DBDataSource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSourceModelRepository extends JpaRepository<DBDataSource, Long> {
    DBDataSource findByName(String name);
}

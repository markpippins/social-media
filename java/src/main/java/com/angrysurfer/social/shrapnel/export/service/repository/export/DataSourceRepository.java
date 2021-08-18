package com.angrysurfer.social.shrapnel.export.service.repository.export;

import com.angrysurfer.social.shrapnel.export.service.model.export.DBDataSource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSourceRepository extends JpaRepository< DBDataSource, Long> {
    DBDataSource findByName(String name);
}

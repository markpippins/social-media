package com.angrysurfer.social.shrapnel.services.repository;

import com.angrysurfer.social.shrapnel.services.model.DBExport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExportModelRepository extends JpaRepository<DBExport, Long> {
    DBExport findByName(String name);
}

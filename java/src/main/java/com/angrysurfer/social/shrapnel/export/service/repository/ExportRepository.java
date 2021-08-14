package com.angrysurfer.social.shrapnel.export.service.repository;

import com.angrysurfer.social.shrapnel.export.service.model.Export;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExportRepository extends JpaRepository<Export, Long> {
    Export findByName(String name);
}

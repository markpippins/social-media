package com.angrysurfer.social.shrapnel.services.repository;

import com.angrysurfer.social.shrapnel.services.model.Export;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExportRepository extends JpaRepository<Export, Long> {
    Export findByName(String name);
}
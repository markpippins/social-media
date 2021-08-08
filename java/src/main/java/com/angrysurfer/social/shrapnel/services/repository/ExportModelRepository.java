package com.angrysurfer.social.shrapnel.services.repository;

import com.angrysurfer.social.shrapnel.services.model.ExportModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExportModelRepository extends JpaRepository<ExportModel, Long> {
    ExportModel findByName(String name);
}

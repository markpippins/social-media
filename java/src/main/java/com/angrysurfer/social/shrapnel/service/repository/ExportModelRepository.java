package com.angrysurfer.social.shrapnel.service.repository;

import com.angrysurfer.social.shrapnel.service.model.ExportModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExportModelRepository extends JpaRepository<ExportModel, Long> {
    ExportModel findByName(String name);
}

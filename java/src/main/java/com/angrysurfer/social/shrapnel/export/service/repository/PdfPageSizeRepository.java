package com.angrysurfer.social.shrapnel.export.service.repository;

import com.angrysurfer.social.shrapnel.export.service.model.PdfPageSize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PdfPageSizeRepository extends JpaRepository<PdfPageSize, String> {
    PdfPageSize findByName(String name);
}

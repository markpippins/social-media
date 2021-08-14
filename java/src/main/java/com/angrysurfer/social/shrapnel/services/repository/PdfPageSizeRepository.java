package com.angrysurfer.social.shrapnel.services.repository;

import com.angrysurfer.social.shrapnel.services.model.PdfPageSize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PdfPageSizeRepository extends JpaRepository<PdfPageSize, String> {
    PdfPageSize findByName(String name);
}

package com.angrysurfer.social.shrapnel.export.service.repository;

import com.angrysurfer.social.shrapnel.export.service.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldSpecRepository extends JpaRepository<Field, Long> {

    Field findByName(String name);
}

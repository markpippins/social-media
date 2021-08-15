package com.angrysurfer.social.shrapnel.export.service.repository.export;

import com.angrysurfer.social.shrapnel.export.service.model.export.Field;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldRepository extends JpaRepository<Field, Long> {

    Field findByName(String name);
}

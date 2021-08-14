package com.angrysurfer.social.shrapnel.services.repository;

import com.angrysurfer.social.shrapnel.services.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldSpecRepository extends JpaRepository<Field, Long> {

    Field findByName(String name);
}

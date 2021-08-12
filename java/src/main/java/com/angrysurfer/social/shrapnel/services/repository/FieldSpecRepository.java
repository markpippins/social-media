package com.angrysurfer.social.shrapnel.services.repository;

import com.angrysurfer.social.shrapnel.services.model.FieldSpec;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldSpecRepository extends JpaRepository<FieldSpec, Long> {

    FieldSpec findByName(String name);
}
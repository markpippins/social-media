package com.angrysurfer.social.shrapnel.services.repository;

import com.angrysurfer.social.shrapnel.services.model.FieldSpecModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldSpecModelRepository extends JpaRepository<FieldSpecModel, Long> {

    FieldSpecModel findByName(String name);
}

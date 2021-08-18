package com.angrysurfer.social.shrapnel.export.service.repository.export;

import com.angrysurfer.social.shrapnel.export.service.model.export.DBFieldType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldTypeRepository extends JpaRepository< DBFieldType, Integer> {

}

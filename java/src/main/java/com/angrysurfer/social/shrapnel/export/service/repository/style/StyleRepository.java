package com.angrysurfer.social.shrapnel.export.service.repository.style;

import com.angrysurfer.social.shrapnel.export.service.model.export.DBField;
import com.angrysurfer.social.shrapnel.export.service.model.style.Style;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StyleRepository extends JpaRepository< Style, Long > {

	DBField findByName(String name);
}

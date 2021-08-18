package com.angrysurfer.social.shrapnel.export.service.repository.value;

import com.angrysurfer.social.shrapnel.export.service.model.sqlgen.Join;
import com.angrysurfer.social.shrapnel.export.service.model.value.LongValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LongValueRepository extends JpaRepository< LongValue, Long > {
}

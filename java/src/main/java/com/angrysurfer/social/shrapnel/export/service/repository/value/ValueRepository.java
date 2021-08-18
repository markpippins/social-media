package com.angrysurfer.social.shrapnel.export.service.repository.value;

import com.angrysurfer.social.shrapnel.export.service.model.sqlgen.Join;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValueRepository extends JpaRepository< Join, Long > {
}

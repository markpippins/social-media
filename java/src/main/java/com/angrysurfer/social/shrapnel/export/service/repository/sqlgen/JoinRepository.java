package com.angrysurfer.social.shrapnel.export.service.repository.sqlgen;

import com.angrysurfer.social.shrapnel.export.service.model.sqlgen.Join;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinRepository extends JpaRepository< Join, Long > {
}

package com.angrysurfer.social.shrapnel.export.service.repository.sqlgen;

import com.angrysurfer.social.shrapnel.export.service.model.sqlgen.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryRepository extends JpaRepository< Query, Long > {
}

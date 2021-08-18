package com.angrysurfer.social.shrapnel.export.service.repository.sqlgen;

import com.angrysurfer.social.shrapnel.export.service.model.sqlgen.Column;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnRepository extends JpaRepository< Column, Long > {
}

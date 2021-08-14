package com.angrysurfer.social.shrapnel.export.service.repository.qbe;

import com.angrysurfer.social.shrapnel.export.service.model.qbe.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryRepository extends JpaRepository< Query, Long > {
}

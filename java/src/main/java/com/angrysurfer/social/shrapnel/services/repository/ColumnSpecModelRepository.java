package com.angrysurfer.social.shrapnel.services.repository;

import com.angrysurfer.social.shrapnel.services.model.DBFieldSpec;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnSpecModelRepository extends JpaRepository<DBFieldSpec, Long> {

    DBFieldSpec findByName(String name);
}

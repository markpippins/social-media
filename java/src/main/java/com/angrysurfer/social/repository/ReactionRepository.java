package com.angrysurfer.social.repository;

import com.angrysurfer.social.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long>{

}

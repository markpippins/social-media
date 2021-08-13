package com.angrysurfer.social.media.repository;

import com.angrysurfer.social.media.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long>{

}

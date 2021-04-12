package com.angrysurfer.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.angrysurfer.social.model.Reaction;

public interface ReactionRepository extends JpaRepository<Reaction, Long>{

}

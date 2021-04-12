package com.angrysurfer.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.angrysurfer.social.model.Edit;

public interface EditRepository extends JpaRepository<Edit, Long>{

//    Set<Edit> findByPostId(Post post);
}

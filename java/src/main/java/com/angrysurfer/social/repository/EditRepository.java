package com.angrysurfer.social.repository;

import com.angrysurfer.social.model.Edit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditRepository extends JpaRepository<Edit, Long>{

//    Set<Edit> findByPostId(Post post);
}

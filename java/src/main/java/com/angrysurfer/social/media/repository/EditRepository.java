package com.angrysurfer.social.media.repository;

import com.angrysurfer.social.media.model.Edit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditRepository extends JpaRepository<Edit, Long>{

//    Set<Edit> findByPostId(Post post);
}

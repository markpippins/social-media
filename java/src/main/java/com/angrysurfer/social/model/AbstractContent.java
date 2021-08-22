package com.angrysurfer.social.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
abstract class AbstractContent implements IContent, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8556528798660585653L;

	@CreationTimestamp
	@Getter
	@Setter
	private LocalDateTime created;

	@UpdateTimestamp
	@Getter
	@Setter
	private LocalDateTime updated;

	@Lob
	@Getter
	@Setter
	private String text;

	@Lob
	@Getter
	@Setter
	private String url;

	@Getter
	@Setter
	private Long rating;

	public String getPostedDate() {
		DateTimeFormatter newPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return this.created.format(newPattern);
	}
}
package com.angrysurfer.social.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "Edit")
public class Edit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7243938370276557466L;

	@Id
	@SequenceGenerator(name = "edit_sequence", sequenceName = "edit_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "edit_sequence")
	@Column(name = "id", updatable = false, nullable = false, unique = true)
	@Getter
	private Long id;

	@CreationTimestamp
	private LocalDateTime created;

	@UpdateTimestamp
	private LocalDateTime updated;

	@Lob
	@Getter
	@Setter
	private String text;

	public Edit() {
	}

	public Edit(String previous) {
		this.setText(previous);
	}

}

package com.angrysurfer.social.shrapnel.export.service.model.qbe;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@javax.persistence.Table(name = "qbe_join_model")//, schema = "shrapnel")
public class Join {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@javax.persistence.Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@Getter
	@Setter
	@JoinColumn(name = "join_column_a_id")
	private Column joinColumnA;

	@ManyToOne(fetch = FetchType.EAGER)
	@Getter
	@Setter
	@JoinColumn(name = "join_column_b_id")
	private Column joinColumnB;
}

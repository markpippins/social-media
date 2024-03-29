package com.angrysurfer.shrapnel.export.service.model.sqlgen;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@javax.persistence.Table(name = "qbe_join", schema = "shrapnel")
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

	@ManyToOne(fetch = FetchType.EAGER)
	@Getter
	@Setter
	@JoinColumn(name = "join_type_code")
	private JoinType joinType;

	@Transient
	public JoinTypeEnum getType() {
		return Objects.isNull(this.joinType) ? null : JoinTypeEnum.from(this.joinType.getCode());
	}
}

package com.angrysurfer.social.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Comment")
//@Table(schema = "social")
public class Comment extends AbstractContent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1902851597891565438L;

	@Id
	@SequenceGenerator(name = "comment_sequence", sequenceName = "comment_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_sequence")
	@Column(name = "id", updatable = false, nullable = false, unique = true)
	@Getter
	private Long id;

	@ManyToOne
	@Getter
	@Setter
	protected Comment parent;

	@ManyToOne(fetch = FetchType.EAGER)
	@Getter
	@Setter
	@JoinColumn(name = "posted_by_user_id")
	private User postedBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@Getter
	@Setter
	private Post post;

	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
	@Getter
	private Set<Comment> replies = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "comment_reaction", joinColumns = { @JoinColumn(name = "reaction_id") }, inverseJoinColumns = {
			@JoinColumn(name = "comment_id") })
	@Getter
	private Set<Reaction> reactions = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "comment_edit", joinColumns = { @JoinColumn(name = "edit_id") }, inverseJoinColumns = {
			@JoinColumn(name = "comment_id") })
	@Getter
	private Set<Edit> edits = new HashSet<>();

	public Comment() {
	}

	public Comment(User user, String text) {
		setText(text);
		setPostedBy(user);
	}

	public Comment(User user, String text, Post post) {
		this(user, text);
		setPost(post);
	}

	public Comment(User user, String text, Comment parent) {
		this(user, text);
		setParent(parent);
	}

}

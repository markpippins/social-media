package com.angrysurfer.social.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity()
public class Interest implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1896715641419245592L;

    @Id
    @SequenceGenerator(name = "interest_sequence", sequenceName = "interest_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interest_sequence")
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    @Getter
    private Long id;

    @Getter
    @Setter
    private String name;

}

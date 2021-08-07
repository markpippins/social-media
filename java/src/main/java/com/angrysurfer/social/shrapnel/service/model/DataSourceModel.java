package com.angrysurfer.social.shrapnel.service.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class DataSourceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "query", nullable = false)
    private String query;

//    @Transient
//    public Collection<Object> getData() {
//        return Collections.EMPTY_LIST;
//    }
}

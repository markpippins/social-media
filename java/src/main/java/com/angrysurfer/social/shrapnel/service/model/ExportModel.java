package com.angrysurfer.social.shrapnel.service.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class ExportModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "data_source_id")
    public DataSourceModel dataSource;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "model_column", joinColumns = {@JoinColumn(name = "model_id")}, inverseJoinColumns = {
            @JoinColumn(name = "column_id")})
    @Getter
    private Set<ColumnSpecModel> columnSpecs = new HashSet<>();
}

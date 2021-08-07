package com.angrysurfer.social.shrapnel.services.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
public class ExportModel {
    @ManyToOne
    @JoinColumn(name = "data_source_id")
    public DataSourceModel dataSource;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "model_column", joinColumns = {@JoinColumn(name = "model_id")}, inverseJoinColumns = {
            @JoinColumn(name = "column_id")})
    @Getter
    private Set<ColumnSpecModel> columnSpecs = new HashSet<>();

    public static boolean isInitialized(ExportModel model) {
        return Objects.nonNull(model.name)
                && model.getColumnSpecs().size() > 0
                && Objects.nonNull(model.getDataSource())
                && Objects.nonNull(model.getDataSource().getQuery());
    }
}

package com.angrysurfer.social.shrapnel.services.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;
import java.util.*;

import static com.angrysurfer.social.shrapnel.services.factory.impl.JdbcMetaExportFactoryImpl.SQL_FOLDER;

@Getter
@Setter
@Entity
@Table(name = "export_model")
public class ExportModel {
    //
    //
    @ManyToOne
    @JoinColumn(name = "data_source_id")
    public DataSourceModel dataSource;
    //
    //
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    //
    //
    @Column(name = "name", nullable = false)
    private String name;
    //
    //
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "export_model_field_spec", joinColumns = {@JoinColumn(name = "model_id")}, inverseJoinColumns = {
            @JoinColumn(name = "field_spec_id")})
    @Getter
    private Set<FieldSpecModel> fieldSpecs = new HashSet<>();

    public boolean isConfigured() {
        final boolean[] isConfigured = {true};

        if (Objects.isNull(getName())
                || Objects.isNull(getDataSource())
                || Objects.isNull(getDataSource().getQuery()))
            isConfigured[0] = false;

        Map<Integer, FieldSpecModel> indexMap = new HashMap<>();
        getFieldSpecs().forEach(field -> {
            if (Objects.isNull(field.getPropertyName())
                    || Objects.isNull(field.getFieldType())
                    || Objects.isNull(field.getIndex())
                    || indexMap.containsKey(field.getIndex()))
                isConfigured[0] = false;
            indexMap.put(field.getIndex(), field);
        });

        File sql = new File(SQL_FOLDER + dataSource.getQuery() + ".sql");
        if (!sql.exists())
            isConfigured[0] = false;

        return isConfigured[0];
    }
}

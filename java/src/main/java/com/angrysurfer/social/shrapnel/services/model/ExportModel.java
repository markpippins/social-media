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
    //
    // data source
    //
    @ManyToOne
    @JoinColumn(name = "data_source_id")
    public DataSourceModel dataSource;
    //
    // id
    //
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    //
    // name
    //
    @Column(name = "name", nullable = false)
    private String name;
    //
    // columns
    //
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "export_model_field_spec", joinColumns = {@JoinColumn(name = "model_id")}, inverseJoinColumns = {
            @JoinColumn(name = "field_spec_id")})
    @Getter
    private Set<FieldSpecModel> fieldSpecs = new HashSet<>();

    public boolean isConfigured() {
        final boolean[] valid = {true};

        if (Objects.isNull(getName()) ||
                Objects.isNull(getDataSource()) ||
                Objects.isNull(getDataSource().getQuery()))
            // bad model config
            valid[0] = false;

        getFieldSpecs().forEach(c -> {
            if (Objects.isNull(c.getPropertyName()) || Objects.isNull(c.getIndex()) || Objects.isNull(c.getType()))
                // bad field config
                valid[0] = false;
        });

        return valid[0];
    }
}

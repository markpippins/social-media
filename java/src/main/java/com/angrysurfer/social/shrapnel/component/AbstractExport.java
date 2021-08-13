package com.angrysurfer.social.shrapnel.component;

import com.angrysurfer.social.shrapnel.component.field.FieldSpecs;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public abstract class AbstractExport implements IExport {

    private String name;

    private FieldSpecs fields = new FieldSpecs();
}

package com.angrysurfer.social.shrapnel.export;

import com.angrysurfer.social.shrapnel.export.component.field.Fields;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public abstract class AbstractExport implements IExport {

    private String name;

    private Fields fields = new Fields();
}

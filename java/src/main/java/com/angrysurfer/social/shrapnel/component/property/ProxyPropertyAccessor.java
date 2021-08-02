package com.angrysurfer.social.shrapnel.component.property;

public interface ProxyPropertyAccessor extends PropertyAccessor {

    PropertyAccessor getPropertyAccessor();

    void setPropertyAccessor(PropertyAccessor propertyAccessor);
}

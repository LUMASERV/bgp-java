package com.lumaserv.bgp.protocol.attribute;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DevelopmentAttribute implements PathAttribute {

    byte[] data;

    public DevelopmentAttribute(byte typeCode, byte[] data) {
        this.data = data;
    }

    public byte[] build() {
        return data;
    }

    public byte getTypeCode() {
        return (byte) 255;
    }

}

package com.lumaserv.bgp.protocol.attribute;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OriginAttribute implements PathAttribute {

    byte origin;

    public OriginAttribute(byte typeCode, byte[] data) {
        origin = data[0];
    }

    public byte getTypeCode() {
        return 1;
    }

    public byte[] build() {
        return new byte[0];
    }

}

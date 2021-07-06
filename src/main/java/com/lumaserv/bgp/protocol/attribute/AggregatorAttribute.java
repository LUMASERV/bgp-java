package com.lumaserv.bgp.protocol.attribute;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AggregatorAttribute implements PathAttribute {

    int as;
    byte[] origin;

    public AggregatorAttribute(byte typeCode, byte[] data) {
        as = ((data[0] & 0xFF) << 8) | (data[1] & 0xFF);
        origin = new byte[4];
        System.arraycopy(data, 2, origin, 0, origin.length);
    }

    public byte getTypeCode() {
        return 7;
    }

    public byte[] build() {
        return new byte[0];
    }

}

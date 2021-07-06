package com.lumaserv.bgp.protocol.attribute;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AS4AggregatorAttribute implements PathAttribute {

    int as;
    byte[] origin;

    public AS4AggregatorAttribute(byte typeCode, byte[] data) {
        as = ((data[0] & 0xFF) << 24) |
                ((data[1] & 0xFF) << 16) |
                ((data[2] & 0xFF) << 8) |
                (data[3] & 0xFF);
        origin = new byte[4];
        System.arraycopy(data, 4, origin, 0, origin.length);
    }

    public byte getTypeCode() {
        return 18;
    }

    public byte[] build() {
        return new byte[0];
    }

}

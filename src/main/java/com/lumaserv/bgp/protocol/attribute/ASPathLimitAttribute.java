package com.lumaserv.bgp.protocol.attribute;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ASPathLimitAttribute implements PathAttribute {

    byte upperBound;
    int as;

    public ASPathLimitAttribute(byte typeCode, byte[] data) {
        upperBound = data[0];
        as = ((data[1] & 0xFF) << 24) |
                ((data[2] & 0xFF) << 16) |
                ((data[3] & 0xFF) << 8) |
                (data[4] & 0xFF);
    }

    public byte getTypeCode() {
        return 21;
    }

    public byte[] build() {
        return new byte[0];
    }

}

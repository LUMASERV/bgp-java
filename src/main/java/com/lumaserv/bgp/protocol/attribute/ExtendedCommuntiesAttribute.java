package com.lumaserv.bgp.protocol.attribute;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExtendedCommuntiesAttribute implements PathAttribute {

    byte type;
    byte subType;
    int as;
    int an;

    public ExtendedCommuntiesAttribute(byte typeCode, byte[] data) {
        type = data[0];
        subType = data[1];
        as = ((data[2] & 0xFF) << 24) |
                ((data[3] & 0xFF) << 16) |
                ((data[4] & 0xFF) << 8) |
                (data[5] & 0xFF);
        an = ((data[6] & 0xFF) << 8) | (data[7] & 0xFF);
    }

    public byte getTypeCode() {
        return 16;
    }

    public byte[] build() {
        return new byte[0];
    }

}

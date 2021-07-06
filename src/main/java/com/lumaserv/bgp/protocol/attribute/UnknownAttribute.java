package com.lumaserv.bgp.protocol.attribute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UnknownAttribute implements PathAttribute {

    byte typeCode;
    byte[] data;

    public byte[] build() {
        return data;
    }

    public byte getTypeCode() {
        return typeCode;
    }

}

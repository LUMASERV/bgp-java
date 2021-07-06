package com.lumaserv.bgp.protocol.attribute;

public class AtomicAggregateAttribute implements PathAttribute {

    public AtomicAggregateAttribute(byte typeCode, byte[] data) {

    }

    public byte getTypeCode() {
        return 6;
    }

    public byte[] build() {
        return new byte[0];
    }

}

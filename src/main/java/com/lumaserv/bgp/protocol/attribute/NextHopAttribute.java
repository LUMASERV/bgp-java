package com.lumaserv.bgp.protocol.attribute;

public class NextHopAttribute implements PathAttribute {

    byte[] address;

    public NextHopAttribute(byte typeCode, byte[] data) {
        this.address = new byte[data.length];
        System.arraycopy(data, 0, address, 0, address.length);
    }

    public byte getTypeCode() {
        return 3;
    }

    public byte[] build() {
        return address;
    }

}

package com.lumaserv.bgp.protocol.attribute;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommunitiesAttribute implements PathAttribute {

    List<Community> communities = new ArrayList<>();

    public CommunitiesAttribute(byte typeCode, byte[] data) {
        int offset = 0;
        while (offset < data.length) {
            communities.add(new Community()
                    .setValueA(((data[offset] & 0xFF) << 8) | (data[offset + 1] & 0xFF))
                    .setValueB(((data[offset + 2] & 0xFF) << 8) | (data[offset + 3] & 0xFF))
            );
            offset += 4;
        }
    }

    public byte getTypeCode() {
        return 8;
    }

    public byte[] build() {
        return new byte[0];
    }

    @Setter
    @Getter
    public static class Community {
        int valueA;
        int valueB;
    }

}

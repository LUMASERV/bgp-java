package com.lumaserv.bgp.protocol.attribute;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ASPathAttribute implements PathAttribute {

    List<Segment> segments = new ArrayList<>();

    public ASPathAttribute(byte typeCode, byte[] data) {
        int offset = 0;
        while (offset < data.length) {
            Segment segment = new Segment().setType(data[offset]);
            byte length = data[offset + 1];
            for(int i=0; i<length; i++)
                segment.asns.add(((data[offset + 2 + (i * 2)] & 0xFF) << 8) | (data[offset + 3 + (i * 2)] & 0xFF));
            offset += 2 + (length * 2);
            segments.add(segment);
        }
    }

    public byte getTypeCode() {
        return 2;
    }

    public byte[] build() {
        return new byte[0];
    }

    @Getter
    @Setter
    public static class Segment {
        byte type;
        List<Integer> asns = new ArrayList<>();
    }

}

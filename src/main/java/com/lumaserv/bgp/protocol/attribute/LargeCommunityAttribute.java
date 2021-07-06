package com.lumaserv.bgp.protocol.attribute;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LargeCommunityAttribute implements PathAttribute {

    List<LargeCommunity> communities = new ArrayList<>();

    public LargeCommunityAttribute(byte typeCode, byte[] data) {
        for(int i=0; i<data.length; i+=12) {
            communities.add(new LargeCommunity()
                    .setGlobalAdministrator(
                            ((data[i] & 0xFF) << 24) |
                            ((data[i+1] & 0xFF) << 16) |
                            ((data[i+2] & 0xFF) << 8) |
                            (data[i+3] & 0xFF)
                    )
                    .setLocalDataPart1(
                            ((data[i+4] & 0xFF) << 24) |
                            ((data[i+5] & 0xFF) << 16) |
                            ((data[i+6] & 0xFF) << 8) |
                            (data[i+7] & 0xFF)
                    )
                    .setLocalDataPart2(
                            ((data[i+8] & 0xFF) << 24) |
                            ((data[i+9] & 0xFF) << 16) |
                            ((data[i+10] & 0xFF) << 8) |
                            (data[i+11] & 0xFF)
                    )
            );
        }
    }

    public byte getTypeCode() {
        return 32;
    }

    public byte[] build() {
        return new byte[0];
    }

    @Setter
    @Getter
    public static class LargeCommunity {
        int globalAdministrator;
        int localDataPart1;
        int localDataPart2;
    }

}

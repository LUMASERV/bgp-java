package com.lumaserv.bgp.protocol.attribute;

public interface PathAttribute {

    byte getTypeCode();
    byte[] build();

    static PathAttribute from(byte typeCode, byte[] data) {
        switch (typeCode & 0xFF) {
            case 1:
                return new OriginAttribute(typeCode, data);
            case 2:
                return new ASPathAttribute(typeCode, data);
            case 3:
                return new NextHopAttribute(typeCode, data);
            case 6:
                return new AtomicAggregateAttribute(typeCode, data);
            case 7:
                return new AggregatorAttribute(typeCode, data);
            case 8:
                return new CommunitiesAttribute(typeCode, data);
            case 16:
                return new ExtendedCommuntiesAttribute(typeCode, data);
            case 17:
                return new AS4PathAttribute(typeCode, data);
            case 18:
                return new AS4AggregatorAttribute(typeCode, data);
            case 21:
                return new ASPathLimitAttribute(typeCode, data);
            case 32:
                return new LargeCommunityAttribute(typeCode, data);
            case 255:
                return new DevelopmentAttribute(typeCode, data);
        }
        return new UnknownAttribute(typeCode, data);
    }

}

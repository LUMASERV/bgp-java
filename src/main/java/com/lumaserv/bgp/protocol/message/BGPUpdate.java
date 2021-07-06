package com.lumaserv.bgp.protocol.message;

import com.lumaserv.bgp.protocol.IPPrefix;
import com.lumaserv.bgp.protocol.attribute.PathAttribute;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BGPUpdate {

    List<IPPrefix> withdrawnPrefixes = new ArrayList<>();
    List<PathAttribute> attributes = new ArrayList<>();
    List<IPPrefix> prefixes = new ArrayList<>();

    public BGPUpdate(byte[] message) {
        int routesLength = ((message[0] & 0xFF) << 8) | (message[1] & 0xFF);
        int offset = 2;
        int offsetOffset = 2;
        while ((offset - offsetOffset) < routesLength) {
            IPPrefix prefix = new IPPrefix()
                    .setLength(message[offset])
                    .setAddress(new byte[4]);
            offset++;
            int addressLen = (int) Math.ceil(prefix.getLength() / 8d);
            System.arraycopy(message, offset, prefix.getAddress(), 0, addressLen);
            offset += addressLen;
            withdrawnPrefixes.add(prefix);
        }
        int attributesLength = ((message[offset] & 0xFF) << 8) | (message[offset + 1] & 0xFF);
        offset += 2;
        offsetOffset = offset;
        while ((offset - offsetOffset) < attributesLength) {
            byte flags = message[offset];
            byte typeCode = message[offset + 1];
            int length = message[offset + 2] & 0xFF;
            offset += 3;
            if((flags & 0b0001_0000) > 0) {
                length <<= 8;
                length |= message[offset] & 0xFF;
                offset++;
            }
            byte[] data = new byte[length];
            System.arraycopy(message, offset, data, 0, length);
            offset += length;
            attributes.add(PathAttribute.from(typeCode, data));
        }
        while (offset < message.length) {
            IPPrefix prefix = new IPPrefix()
                    .setLength(message[offset])
                    .setAddress(new byte[4]);
            offset++;
            int addressLen = (int) Math.ceil(prefix.getLength() / 8d);
            System.arraycopy(message, offset, prefix.getAddress(), 0, addressLen);
            offset += addressLen;
            prefixes.add(prefix);
        }
    }

}

package com.lumaserv.bgp.protocol.message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BGPOpen {

    byte version;
    int asn;
    int holdTime;
    byte[] identifier;

    public BGPOpen(byte[] message) {
        version = message[0];
        asn = ((message[1] & 0xFF) << 8) | (message[2] & 0xFF);
        holdTime = ((message[3] & 0xFF) << 8) | (message[4] & 0xFF);
        identifier = new byte[4];
    }

    public byte[] build() {
        byte[] message = new byte[6 + identifier.length];
        message[0] = version;
        message[1] = (byte) (asn >> 8);
        message[2] = (byte) (asn & 0xFF);
        message[3] = (byte) (holdTime >> 8);
        message[4] = (byte) (holdTime & 0xFF);
        System.arraycopy(identifier, 0, message, 5, identifier.length);
        return message;
    }

}

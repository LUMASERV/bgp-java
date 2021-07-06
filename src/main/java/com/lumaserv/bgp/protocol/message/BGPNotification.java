package com.lumaserv.bgp.protocol.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BGPNotification {

    byte majorErrorCode;
    byte minorErrorCode;

    public BGPNotification(byte[] message) {
        this.majorErrorCode = message[0];
        this.minorErrorCode = message[1];
    }

}

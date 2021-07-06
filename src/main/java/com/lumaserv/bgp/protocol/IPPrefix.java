package com.lumaserv.bgp.protocol;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.InetAddress;
import java.net.UnknownHostException;

@NoArgsConstructor
@Setter
@Getter
public class IPPrefix {

    byte[] address;
    byte length;

    public String toString() {
        try {
            return InetAddress.getByAddress(address).getHostAddress() + "/" + length;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

}

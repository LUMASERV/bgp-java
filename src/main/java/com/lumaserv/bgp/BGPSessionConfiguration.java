package com.lumaserv.bgp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class BGPSessionConfiguration {

    String name;
    int localAs;
    byte[] localIdentifier;
    int remoteAs;
    byte[] remoteIdentifier;
    BGPListener listener;

}

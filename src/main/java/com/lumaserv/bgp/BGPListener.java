package com.lumaserv.bgp;

import com.lumaserv.bgp.protocol.message.BGPUpdate;

public interface BGPListener {

    void onOpen(BGPSession session);
    void onUpdate(BGPSession session, BGPUpdate update);
    void onClose(BGPSession session);

}

package com.lumaserv.bgp;

import com.lumaserv.bgp.protocol.BGPPacket;
import com.lumaserv.bgp.protocol.message.BGPUpdate;
import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class BGPSession implements Runnable {

    @Getter
    final BGPSessionConfiguration configuration;
    final InputStream inputStream;
    final OutputStream outputStream;

    public BGPSession(Socket socket, BGPSessionConfiguration configuration) throws IOException {
        this.configuration = configuration;
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
    }

    public void keepAlive() {
        try {
            outputStream.write(new BGPPacket().setType(BGPPacket.Type.KEEPALIVE).setMessage(new byte[0]).build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handle(BGPPacket packet) {
        switch (packet.getType()) {
            case KEEPALIVE:
                keepAlive();
                break;
            case UPDATE: {
                configuration.getListener().onUpdate(this, new BGPUpdate(packet.getMessage()));
                break;
            }
        }
    }

    public void run() {
        try {
            while (true)
                handle(BGPPacket.read(inputStream));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}

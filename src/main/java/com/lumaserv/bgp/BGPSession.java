package com.lumaserv.bgp;

import com.lumaserv.bgp.protocol.BGPPacket;
import com.lumaserv.bgp.protocol.message.BGPUpdate;
import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

public class BGPSession implements Runnable {

    @Getter
    final BGPSessionConfiguration configuration;
    final InputStream inputStream;
    final OutputStream outputStream;
    @Getter
    boolean closed;

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
            case NOTIFICATION:
                closed = true;
                configuration.getListener().onClose(this);
                break;
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
            while (!closed) {
                try {
                    handle(BGPPacket.read(inputStream));
                } catch (SocketException ignored) {
                    closed = true;
                    configuration.getListener().onClose(this);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}

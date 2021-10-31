package com.lumaserv.bgp;

import com.lumaserv.bgp.protocol.BGPPacket;
import com.lumaserv.bgp.protocol.message.BGPOpen;
import lombok.Getter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class BGPServer implements Runnable {

    final ServerSocket serverSocket;
    @Getter
    final List<BGPSessionConfiguration> sessionConfigurations = new ArrayList<>();

    public BGPServer() throws IOException {
        serverSocket = new ServerSocket(179);
    }

    private static boolean checkEqual(byte[] a, byte[] b) {
        if(a == b)
            return true;
        if(a == null || b == null)
            return false;
        if(a.length != b.length)
            return false;
        for(int i=0; i<a.length; i++) {
            if(a[i] != b[i])
                return false;
        }
        return true;
    }

    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                BGPPacket packet = BGPPacket.read(socket.getInputStream());
                if(packet.getType() != BGPPacket.Type.OPEN)
                    continue;
                BGPOpen request = new BGPOpen(packet.getMessage());
                BGPSessionConfiguration config = sessionConfigurations.stream()
                        .filter(c -> c.getRemoteAs() == request.getAsn())
                        .findFirst()
                        .orElse(null);
                if(config == null)
                    continue;
                BGPOpen response = new BGPOpen()
                        .setAsn(config.getLocalAs())
                        .setHoldTime(request.getHoldTime())
                        .setVersion(request.getVersion())
                        .setIdentifier(config.getLocalIdentifier());
                try {
                    socket.getOutputStream().write(new BGPPacket().setType(BGPPacket.Type.OPEN).setMessage(response.build()).build());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BGPSession session = new BGPSession(socket, config);
                session.keepAlive();
                new Thread(session).start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}

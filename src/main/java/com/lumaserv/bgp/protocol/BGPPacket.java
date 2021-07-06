package com.lumaserv.bgp.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Setter
@Getter
@NoArgsConstructor
public class BGPPacket {

    Type type;
    byte[] message;

    public BGPPacket(byte[] packet) {
        type = Type.fromValue(packet[18]);
        message = new byte[packet.length-19];
        System.arraycopy(packet, 19, message, 0, message.length);
    }

    public byte[] build() {
        byte[] packet = new byte[message.length + 19];
        Arrays.fill(packet, 0, 16, (byte) 0xFF);
        packet[16] = (byte)(packet.length >> 8);
        packet[17] = (byte)(packet.length & 0xFF);
        packet[18] = type.getValue();
        System.arraycopy(message, 0, packet, 19, message.length);
        return packet;
    }

    public static BGPPacket read(InputStream stream) throws IOException {
        byte[] packet = new byte[18];
        int value;
        for(int i=0; i<packet.length; i++) {
            value = stream.read();
            if(value == -1)
                throw new IOException("Unexpected end of stream");
            packet[i] = (byte) value;
        }
        int length = ((packet[16] & 0xFF) << 8) | (packet[17] & 0xFF);
        byte[] newPacket = new byte[length];
        System.arraycopy(packet, 0, newPacket, 0, packet.length);
        packet = newPacket;
        for(int i=18; i<packet.length; i++) {
            value = stream.read();
            if(value == -1)
                throw new IOException("Unexpected end of stream");
            packet[i] = (byte) value;
        }
        return new BGPPacket(packet);
    }

    @AllArgsConstructor
    @Getter
    public enum Type {
        OPEN((byte) 1),
        UPDATE((byte) 2),
        NOTIFICATION((byte) 3),
        KEEPALIVE((byte) 4),
        ROUTE_REFRESH((byte) 5);
        final byte value;
        public static Type fromValue(byte value) {
            for(Type t : values()) {
                if(t.value == value)
                    return t;
            }
            return null;
        }
    }

}

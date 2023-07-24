package org.example.protocol.request;

import lombok.Data;
import org.example.protocol.command.Command;
import org.example.protocol.command.Packet;

@Data
public class MessageRequestPacket extends Packet {
    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}

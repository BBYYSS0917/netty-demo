package org.example.protocol.response;

import lombok.Data;
import org.example.protocol.command.Command;
import org.example.protocol.command.Packet;

@Data
public class MessageResponsePacket extends Packet {
    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}

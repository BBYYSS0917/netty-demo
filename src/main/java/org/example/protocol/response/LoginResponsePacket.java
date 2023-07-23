package org.example.protocol.response;

import lombok.Data;
import org.example.protocol.command.Command;
import org.example.protocol.command.Packet;

@Data
public class LoginResponsePacket extends Packet {
    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}

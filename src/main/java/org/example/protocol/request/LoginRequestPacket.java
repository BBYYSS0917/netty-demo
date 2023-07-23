package org.example.protocol.request;

import lombok.Data;
import org.example.protocol.command.Command;
import org.example.protocol.command.Packet;

@Data
public class LoginRequestPacket extends Packet {
    private String userId;
    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}

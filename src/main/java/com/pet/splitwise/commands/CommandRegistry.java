package com.pet.splitwise.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class CommandRegistry {
    private List<Command> commands;

    @Autowired
    public CommandRegistry(RegisterUserCommand registerUserCommand
            , UpdateProfileCommand updateProfileCommand
            , SettleUpUserCommand settleUpUserCommand
            , SettleUpGroupCommand settleUpGroupCommand) {
        commands = new ArrayList<>();
        commands.add(registerUserCommand);
        commands.add(updateProfileCommand);
        commands.add(settleUpUserCommand);
        commands.add(settleUpGroupCommand);
    }

    public void execute(String input) {
        for (Command command : commands) {
            if (command.matches(input)) {
                command.execute(input);
                break;
            }
        }
    }
}

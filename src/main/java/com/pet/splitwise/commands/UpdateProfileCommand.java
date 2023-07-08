package com.pet.splitwise.commands;

import org.springframework.stereotype.Component;

@Component
public class UpdateProfileCommand implements Command{
    @Override
    public boolean matches(String input) {
        return false;
    }

    @Override
    public void execute(String input) {

    }
}

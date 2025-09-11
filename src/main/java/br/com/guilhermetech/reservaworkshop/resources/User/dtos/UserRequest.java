package br.com.guilhermetech.reservaworkshop.resources.User.dtos;

import br.com.guilhermetech.reservaworkshop.entities.User;

public record UserRequest(String name, String email) {
    public User convertToEntity(){
        return new User(name, email);
    }
}

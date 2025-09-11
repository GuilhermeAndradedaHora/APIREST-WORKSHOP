package br.com.guilhermetech.reservaworkshop.resources.User.Authentication.dtos;

import br.com.guilhermetech.reservaworkshop.entities.User;

public record AuthRequest(String email, String password) {
}

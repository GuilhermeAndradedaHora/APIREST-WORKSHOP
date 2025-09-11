package br.com.guilhermetech.reservaworkshop.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType {
    ADMIN(1),
    PART(2);

    //Armazena enums no banco como int e depois converter para enum em Java
    private final int value;
    public static UserType valueOf(int value) {
        for (UserType uc : UserType.values()) {
            if (uc.getValue() == value) {
                return uc;
            }
        }
        throw new IllegalArgumentException("Invalid UserCondition value");
    }
}

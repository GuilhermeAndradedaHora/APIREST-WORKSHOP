package br.com.guilhermetech.reservaworkshop.entities.enums;

import lombok.Getter;

public enum UserType {
    ADMIN(1),
    PART(2);

    @Getter
    private int value;
    private UserType(int value) {
        this.value = value;
    }
    public static UserType valueOf(int value) {
        for (UserType uc : UserType.values()) {
            if (uc.getValue() == value) {
                return uc;
            }
        }
        throw new IllegalArgumentException("Invalid UserCondition value");
    }
}

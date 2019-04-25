package com.sxkl.project.cloudnote.user.entity;

public class User {

    private static final String NAME = "wy";
    private static final String PASSWORD = "v";

    public static String getName() {
        return NAME;
    }

    public static boolean checkPassword(String password) {
        return PASSWORD.equals(password);
    }

    public static boolean login(String name, String password) {
        return NAME.equals(name) && PASSWORD.equals(password);
    }
}

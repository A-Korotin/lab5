package net.auth;

import org.apache.commons.codec.digest.DigestUtils;

public final class User {
    public String login;
    public String password;

    public User(String login, String password) {
       this.login = login;
       this.password = DigestUtils.md5Hex(password).toUpperCase();
       System.out.printf("%s %s%n",this.login, this.password);
    }

    public static void main(String[] a) {
        User user = new User("41214", "qwerty");
    }
}

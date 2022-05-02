package net.auth;

import commands.Login;
import commands.dependencies.Instances;
import io.ConsoleOutput;
import org.apache.commons.codec.digest.DigestUtils;

public final class User {
    public String login;
    public String password;

    public User(){}

    public User(String login, String password) {
       this.login = login;
       this.password = DigestUtils.md5Hex(password).toUpperCase();
       System.out.printf("%s %s%n",this.login, this.password);
    }

    public static void main(String[] a) {
       var login = new Login();
       login.user = new User("123", "123");
       Instances i = new Instances();
       i.outPutter = new ConsoleOutput();
       login.execute(i);

    }
}

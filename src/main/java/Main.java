import net.Server;

import java.io.IOException;
import java.net.BindException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("HELLO!");
        try {
            Server server = new Server("localhost", 4444);
            server.run();
        } catch (BindException e) {
            System.out.printf("Не удалось запустить сервер (%s)", e.getMessage());
        } catch (SQLException e ) {
            System.out.printf("Ошибка SQL:%s%n", e.getMessage());
        }

    }
}

public class Main {

    public static void main(String[] args) {
        CommandEnv.defaultInit();
        try{
            CommandEnv.mainLoop();
        }
        catch (RuntimeException runtimeException){
            System.out.print("Не совпадают типы данных");
        }
    }
}

/*
* Интерфейс вывода информации*/
public interface OutPuter {
    void outPut(String msg);
    void outPut(Dragon dragon);
    void outPut(int number);

}
/*
Класс для вывода информации в консоль. Имплементируется от интерфейса OutPuter*/
class ConsoleOutPut implements OutPuter {
    public void outPut(String msg) {
        System.out.println(msg);
    }

    @Override
    public void outPut(Dragon dragon) {
        System.out.println(dragon.toString());
    }

    @Override
    public void outPut(int number) {
        System.out.println(number);
    }
}
/*
Класс для вывода информации в файл. Имплементируется от интерфейса OutPuter*/
class FileOutPut implements OutPuter {
    @Override
    public void outPut(String msg) {

    }

    @Override
    public void outPut(Dragon dragon) {

    }

    @Override
    public void outPut(int number) {

    }
}
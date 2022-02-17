public interface OutPuter {
    void outPut(String msg);
    void outPut(Dragon dragon);
    void outPut(int number);

}

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
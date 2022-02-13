public interface OutPuter {
    void outPut(String msg);
    void outPut(Dragon dragon);
}

class ConsoleOutPut implements OutPuter {
    public void outPut(String msg) {
        System.out.println(msg);
    }

    @Override
    public void outPut(Dragon dragon) {
        System.out.println(dragon);
    }
}

class FileOutPut implements OutPuter {
    @Override
    public void outPut(String msg) {

    }

    @Override
    public void outPut(Dragon dragon) {

    }
}
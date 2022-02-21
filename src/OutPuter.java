public interface OutPuter {
    void outPut(String msg);
    void outPut(DaoElement element);
    void outPut(int number);

}

class ConsoleOutPut implements OutPuter {
    public void outPut(String msg) {
        System.out.println(msg);
    }

    @Override
    public void outPut(DaoElement element) {
        System.out.println(element);
    }

    @Override
    public void outPut(int number) {
        System.out.println(number);
    }
}

package io;
import collection.DaoElement;

/**
* Интерфейс вывода информации*/
public interface OutPutter {
    void output(String msg);
    void output(DaoElement dragon);
    void output(int number);

}

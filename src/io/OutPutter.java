package io;
import dragon.Dragon;

/**
* Интерфейс вывода информации*/
public interface OutPutter {
    void output(String msg);
    void output(Dragon dragon);
    void output(int number);

}

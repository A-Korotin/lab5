package io;

import dragon.Dragon;

/**
 Класс для вывода информации в консоль. Имплементируется от интерфейса io.OutPutter*/
public class ConsoleOutput implements OutPutter {
    public void output(String msg) {
        System.out.println(msg);
    }

    @Override
    public void output(Dragon element) {
        System.out.println(element);
    }

    @Override
    public void output(int number) {
        System.out.println(number);
    }
}
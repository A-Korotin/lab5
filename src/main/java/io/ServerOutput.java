package io;

import com.fasterxml.jackson.core.JsonProcessingException;
import dragon.Dragon;

import java.util.ArrayList;
import java.util.List;

public class ServerOutput implements OutPutter{
    private final List<String> list = new ArrayList<>();

    @Override
    public void output(Dragon dragon) {
        try {
            list.add(dragon.description());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T extends Number> void output(T t) {
        list.add(String.valueOf(t));
    }

    @Override
    public void output(String msg) {
        list.add(msg);
    }

    @Override
    public String compound(){
        String result = null;

        for (String element : list){
            result = result + System.lineSeparator() + element;
        }

        list.clear();
        return result;
    }
    
}

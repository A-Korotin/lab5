package io;

import com.fasterxml.jackson.core.JsonProcessingException;
import dragon.Dragon;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
    public List<String> compound(){
        List<String> listOfString = new ArrayList<>();
        String result = "";

        for (String element : list){
            result = result + System.lineSeparator() + element;
            try {
                if (getMemoryLength(result) >= 1000){
                    listOfString.add(result);
                    result = "";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (listOfString.size() == 0)
            listOfString.add(result);

        list.clear();
        return listOfString;
    }

    public static int getMemoryLength(Object object) throws java.io.IOException
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(stream);

        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
        objectOutputStream.close();
        stream.close();

        return stream.toByteArray().length;
    }
    
}

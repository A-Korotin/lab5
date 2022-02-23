package io;

import java.util.*;
import java.io.*;
/**Абстрактный класс чтения введённой или записанной в файле информации*/
public abstract class InputReader {
    protected boolean askForInput;

    public boolean getAskForInput() {
        return askForInput;
    }

    protected List<String> additionalProperties = new ArrayList<>();

    public abstract List<List<String>> getInput();

    public void addProperties(String prop) {
        additionalProperties.add(prop);
    }
    }

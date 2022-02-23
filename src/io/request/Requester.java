package io.request;

import java.io.InputStream;
import java.io.PrintStream;

public interface Requester {
    String request(ValidationFunction function, String startMsg, String errorMsg);
    void setInputStream(InputStream stream);
    void setPrintStream(PrintStream stream);
}


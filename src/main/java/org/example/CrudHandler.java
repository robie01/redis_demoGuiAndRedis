package org.example;

public interface CrudHandler {
    void create();
    void update();
    void delete();
    String read(String inputData);
}

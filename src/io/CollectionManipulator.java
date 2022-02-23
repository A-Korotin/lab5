package io;

import collection.DAO;
import collection.DaoElement;
import collection.DragonDAO;

import javax.json.*;
import java.util.*;
import java.io.*;
/**
* Интерфейс для взаимодействия с коллекцией */
interface CollectionManipulator {
    void save(DAO<? extends DaoElement> collection);
    DAO<? extends DaoElement> get();
}

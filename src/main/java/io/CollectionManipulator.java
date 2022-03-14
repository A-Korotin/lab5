package io;

import collection.DAO;
import collection.Describable;
import dragon.Dragon;
import collection.DragonDAO;

import javax.json.*;
import java.util.*;
import java.io.*;
/**
* Интерфейс для взаимодействия с коллекцией */
interface CollectionManipulator {
    void save(Describable collection);
    DAO get();
}

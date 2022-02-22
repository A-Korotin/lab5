package io;

import collection.DAO;
import collection.DragonDAO;

import javax.json.*;
import java.util.*;
import java.io.*;
/**
* Интерфейс для взаимодействия с коллекцией */
interface CollectionManipulator {
    void save(DAO collection);
    DAO get();
}
/**Класс, реализующий методы сохранения данных в файл и получение данных из файла для дальнейшего взаимодействия с ними*/
class FileManipulator implements CollectionManipulator {
    /**
     * Метод сохранения коллекции в файл
     * @param collection - коллекция
     * */
    @Override
    public void save(DAO collection) {
        Map<String, String> env = System.getenv();
        String filepath = env.get("DAO_COLLECTION_FILEPATH");

        try (FileOutputStream stream = new FileOutputStream(filepath); OutputStreamWriter writer = new OutputStreamWriter(stream)) {
            JsonObject description = collection.getJSONDescription();
            writer.write(description.toString());

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    /**
     * Метод возвращения коллекции, считанной из файла
     * @return new collection.DragonDAO() - коллекция, считанная из файла
     * */
    @Override
    public DAO get() {
        Map<String, String> env = System.getenv();
        String filepath = env.get("DAO_COLLECTION_FILEPATH");

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             FileInputStream fileInputStream = new FileInputStream(filepath);
             BufferedInputStream inputStream = new BufferedInputStream(fileInputStream)) {

            int nextByte;
            while ((nextByte = inputStream.read()) != -1)
                bos.write((char) nextByte);

            String input = bos.toString();
            JsonReader reader = Json.createReader(new StringReader(input));
            JsonObject daoJson = reader.readObject();
            return new DragonDAO(daoJson);

        } catch (IOException | RuntimeException e) {
            throw new RuntimeException("Значения файла JSON были изменены вручную, что привело к ошибке. " +
                                       "Была инициализирована пустая коллекция.");
        }
    }

}

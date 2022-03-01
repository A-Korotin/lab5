package io;

import collection.DAO;
import collection.Describable;
import com.fasterxml.jackson.databind.JsonNode;
import dragon.Dragon;
import collection.DragonDAO;
import json.Json;

import java.io.*;
import java.util.Map;

/**Класс, реализующий методы сохранения данных в файл и получение данных из файла для дальнейшего взаимодействия с ними*/
public class FileManipulator implements CollectionManipulator {
    /**
     * Метод сохранения коллекции в файл
     * @param collection - коллекция
     * */
    @Override
    public void save(Describable collection) {
        Map<String, String> env = System.getenv();
        String filepath = env.get("DAO_COLLECTION_FILEPATH");

        try (FileOutputStream stream = new FileOutputStream(filepath); OutputStreamWriter writer = new OutputStreamWriter(stream)) {
            String description = collection.description();
            writer.write(description);

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
            JsonNode node = Json.parse(input);
            DragonDAO out = Json.fromJson(node, DragonDAO.class);
            return out;


        } catch (IOException | RuntimeException e) {
            throw new RuntimeException("Значения файла JSON были изменены вручную, что привело к ошибке. " +
                    "Была инициализирована пустая коллекция.");
        }
    }

}

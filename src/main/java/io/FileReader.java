package io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для чтения информации из файла, наследуется от абстрактного класса io.InputReader*/
public final class FileReader extends InputReader {

    {
        askForInput = false;
    }
    /**
     * Метод получения данных из файла
     * @return output - список списков, считанный из файла
     * */
    public List<String> getInput() {
        String filePath = additionalProperties.get(0);

        try (FileInputStream fis = new FileInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            int nextByte;

            while((nextByte = bis.read()) != -1)
                bos.write((char) nextByte);

            String allData = bos.toString();

            return new ArrayList<>(List.of(allData.split(System.lineSeparator())));


        } catch (FileNotFoundException e) {
            throw new RuntimeException("Файл не найден и\\или нет прав на чтение");
        } catch (IOException e) {
            throw new RuntimeException("Файл не был успешно прочитан на " + filePath);
        }
    }
}

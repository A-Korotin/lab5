package io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для чтения информации из файла, наследуется от абстрактного класса io.InputReader*/
public class FileReader extends InputReader {

    {
        askForInput = false;
    }
    /**
     * Метод получения данных из файла
     * @return output - список списков, считанный из файла
     * */
    public List<List<String>> getInput() {
        String filePath = additionalProperties.get(0);
        File file;
        FileInputStream fileInputStream = null;
        BufferedInputStream inputStream = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            file = new File(filePath);
            fileInputStream = new FileInputStream(file);
            inputStream = new BufferedInputStream(fileInputStream);

            int nextByte;
            while((nextByte = inputStream.read()) != -1)
                bos.write((char)nextByte);

            String allData = bos.toString();
            List<String> lines = new ArrayList<>(List.of(allData.split(System.lineSeparator())));
            List<List<String>> output = new ArrayList<>();
            for (String line: lines) {
                List<String> separatedLine = new ArrayList<>(List.of(line.split(" ")));
                separatedLine.removeIf(String::isEmpty);
                output.add(separatedLine);
            }
            return output;
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("Файл не найден и\\или нет прав на чтение");
        }
        catch (IOException e) {
            throw new RuntimeException("Файл не был успешно прочитан на " + filePath);
        }
        finally {
            try {
                if (inputStream != null)
                    inputStream.close();

                if (fileInputStream != null)
                    fileInputStream.close();

            } catch (IOException e) {
                throw new RuntimeException("Input stream не был успешно закрыт на " + filePath);
            }
        }
    }
}

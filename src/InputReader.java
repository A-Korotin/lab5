import java.util.*;
import java.io.*;

public abstract class InputReader {

    protected List<String> additionalProperties = new ArrayList<>();

    public abstract List<List<String>> getInput();

    public void addProperties(String prop) {
        additionalProperties.add(prop);
    }

    public static void main(String[] args){
        InputReader reader = new FileReader();
        reader.addProperties("test.txt");

        List<List<String>> input = reader.getInput();
        for (List<String> l: input) {
            for (String s : l)
                System.out.print(s);
        }
    }
}

class ConsoleReader extends InputReader {
    public List<List<String>> getInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.isBlank())
            throw new RuntimeException("Blank input!");
        List<String> output = new ArrayList<>(List.of(input.split(" ")));
        output.removeIf(String::isBlank);

        List<List<String>> out = new ArrayList<>();
        out.add(output);
        return out;
    }
}

class FileReader extends InputReader {

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
            throw new RuntimeException("File not found!");
        }
        catch (IOException e) {
            throw new RuntimeException("File not successfully read at " + filePath);
        }
        finally {
            try {
                if (inputStream != null)
                    inputStream.close();

                if (fileInputStream != null)
                    fileInputStream.close();

            } catch (IOException e) {
                throw new RuntimeException("Input stream not closed successfully at " + filePath);
            }

        }
    }
}




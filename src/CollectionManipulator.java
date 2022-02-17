import javax.json.*;
import java.util.*;
import java.io.*;

interface CollectionManipulator {
    void save(DAO collection);
    DAO get();
}

class FileManipulator implements CollectionManipulator {

    @Override
    public void save(DAO collection) {
        Map<String, String> env = System.getenv();
        String filepath = env.get("DAO_COLLECTION_FILEPATH");

        File file = new File(filepath);
        try (FileOutputStream stream = new FileOutputStream(file); OutputStreamWriter writer = new OutputStreamWriter(stream)) {
            JsonObject description = collection.getJSONDescription();
            writer.write(description.toString());

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public DAO get() {
        Map<String, String> env = System.getenv();
        String filepath = env.get("DAO_COLLECTION_FILEPATH");

        File file = new File(filepath);
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             FileInputStream fileInputStream = new FileInputStream(file);
             BufferedInputStream inputStream = new BufferedInputStream(fileInputStream)) {

            int nextByte;
            while ((nextByte = inputStream.read()) != -1)
                bos.write((char) nextByte);

            String input = bos.toString();
            JsonReader reader = Json.createReader(new StringReader(input));
            JsonObject daoJson = reader.readObject();
            return new DragonDAO(daoJson);

        } catch (IOException e) {
            // пропустить и вернуть новую коллекцию
        }
        return new DragonDAO();
    }

}

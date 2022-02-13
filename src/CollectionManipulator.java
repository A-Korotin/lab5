import javax.json.*;
import java.util.Map;
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
        FileOutputStream stream = null;
        OutputStreamWriter writer = null;
        try {
            stream = new FileOutputStream(file);
             writer = new OutputStreamWriter(stream);
            JsonObject description = collection.getJSONDescription();
            writer.write(description.toString());

        } catch (IOException e) {
            System.out.println(e.getMessage());

        }
        finally {
            try {
                writer.close();
                stream.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            catch (NullPointerException e) {
            }
        }
    }

    @Override
    public DAO get() {
        Map<String, String> env = System.getenv();
        String filepath = env.get("DAO_COLLECTION_FILEPATH");

        File file = new File(filepath);
        FileInputStream fileInputStream = null;
        BufferedInputStream inputStream = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            fileInputStream = new FileInputStream(file);
            inputStream = new BufferedInputStream(fileInputStream);
            int nextByte;
            while((nextByte = inputStream.read()) != -1)
                bos.write((char) nextByte);

            String input = bos.toString();
            JsonReader reader = Json.createReader(new StringReader(input));
            JsonObject daoJson = reader.readObject();
            return new DragonDAO(daoJson);

        } catch (IOException e) {


        } finally {


        }
        return null;
    }

}

package data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Database {
    protected String filePath;
    protected int autoIncrementId = 1;

    public int autoIncrement() {
        autoIncrementId++;
        return autoIncrementId;
    }

        protected abstract void loadData();
        protected abstract void saveData();
        public abstract void updateAutoIncrementId();



    protected String readFile() throws Exception {
        try {
            FileReader reader = new FileReader(filePath);
            StringBuilder content = new StringBuilder();
            int character;

            while ((character = reader.read()) != -1) {
                content.append((char) character);
            }

            reader.close();
            return content.toString();
        } catch (Exception e) {
            throw new Exception("Error reading file(" + filePath + "): " + e.getMessage());
        }
    }

    protected void writeFile(String data) throws Exception {
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(data);
            writer.close();
        } catch (Exception e) {
            throw new Exception("Error writing to file(" + filePath + "): " + e.getMessage());
        }
    }
}


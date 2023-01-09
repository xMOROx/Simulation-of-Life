package Settings;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {
    private final File filePath;
    private final CSVWriter writer;

    public CsvWriter(String filePath) throws IOException {
        this.filePath = new File(filePath);
        if (!this.filePath.exists()) {
            this.filePath.createNewFile();
        }
        try {
            this.writer = new CSVWriter(new FileWriter(filePath));
            String[] header = {"Days", "Number of all animals", "Number of all plants", "Number of free fields", "Number of occupied fields", "Average energy level", "Life expectancy", "The most popular genome"};
            this.writer.writeNext(header);
        } catch (IOException e) {
            throw new IOException("File not found");
        }
    }

    public void writeData(List<String[]> data) throws IOException {
        this.writer.writeAll(data);
    }

    public void close() throws IOException {
        this.writer.close();
    }
}

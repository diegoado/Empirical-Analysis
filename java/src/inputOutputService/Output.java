package inputOutputService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Output {

    private File file;

    public Output(String path) {
        file = new File(path);

        // Create a folder if it no exists
        file.getParentFile().mkdirs();
    }

    public void saveFile(String[] lines) throws IOException {
        if (!file.exists())
            // Create the file if it no exists
            file.createNewFile();
        FileWriter writer = new FileWriter(file);
        BufferedWriter buffered = new BufferedWriter(writer);

        for (String string : lines) {
            buffered.write(string);
            buffered.write(Input.LINE_BREAK);
        }
        buffered.close();
        writer.close();
    }
}

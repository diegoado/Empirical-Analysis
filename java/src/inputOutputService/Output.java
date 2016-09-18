package inputOutputService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Output {

    private File file;
    private String path;

    public Output(String path) {
        this.path = path;
        file = new File(path);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void saveFile(String[] linhas) throws IOException {
        if(!file.exists())
            file.createNewFile();
        FileWriter writer = new FileWriter(file);
        BufferedWriter buffered = new BufferedWriter(writer);

        for (String string : linhas) {
            buffered.write(string);
            buffered.write(Input.LINE_BREAK);
        }
        buffered.close();
        writer.close();
    }
}

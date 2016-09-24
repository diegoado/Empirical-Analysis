package inputOutputService;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Input {

    private String path;
    private StringBuilder text;
    public static final String LINE_BREAK = System.getProperty("line.separator");

    public Input(String path) {
        this.path = path;
    }

    public String[] getFile() throws IOException {
        if(text != null){
            return text.toString().split(LINE_BREAK);
        }
        RandomAccessFile file = new RandomAccessFile(path, "r");
        FileChannel channel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(100000000);

        int bytesRead = channel.read(buffer);
        text = new StringBuilder();

        while (bytesRead != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                char c = (char) buffer.get();
                text.append(c);
            }

            text.deleteCharAt(text.length() - 1);
            buffer.clear();
            bytesRead = channel.read(buffer);
        }
        file.close();
        return text.toString().split(LINE_BREAK);
    }
}

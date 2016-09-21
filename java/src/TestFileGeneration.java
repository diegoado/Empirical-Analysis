import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import inputOutputService.Input;
import inputOutputService.Output;
import sorting.SortingImpl;
import sorting.concurrent.ConcurrentMergesort;
import sorting.concurrent.ConcurrentMergesortThreadLimited;
import sorting.concurrent.ConcurrentRandomQuicksort;
import sorting.concurrent.ConcurrentRandomQuicksortThreadLimited;
import sorting.sequential.SequentialMergesort;
import sorting.sequential.SequentialRandomQuicksort;

public class TestFileGeneration {

	private static final String PATH = "/home/renato/entrance/input%d.txt";
	private static final String OUT_PATH = "/home/renato/output/java%s.csv";
	private static final String[] FILES_NAMES = new String[]{"seqQuick","seqMerge","concQuick","concQuickLtd","concMerge","concMergeLtd"};
	private static Output out;
	private static Input input;

	public static void main(String[] args){
		List<SortingImpl<String>> algoritimos = new ArrayList<>();
		algoritimos.add(new SequentialRandomQuicksort<>());
		algoritimos.add(new SequentialMergesort<>());
		algoritimos.add(new ConcurrentRandomQuicksort<>());
		algoritimos.add(new ConcurrentRandomQuicksortThreadLimited<>());
		algoritimos.add(new ConcurrentMergesort<>());
		algoritimos.add(new ConcurrentMergesortThreadLimited<>());

		String[][] outputFiles = new String[6][50];
		String[] words = null;
		Long media;

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 50; j++) {
				input = new Input(String.format(PATH, j+1));
				media = 0l;
				for (int k = 0; k < 10; k++) {
					try {
						words = input.getFile();
					} catch (IOException e) {
						System.err.println("Input file not found");
						System.exit(1);
					}

					long startTime = System.nanoTime();
					algoritimos.get(i).sort(words);
					long finishTime = System.nanoTime();

					media = media + (finishTime - startTime);
				}
				outputFiles[i][j] = String.valueOf(media/10l);
			}
			
			out = new Output(String.format(OUT_PATH, FILES_NAMES[i]));
			try {
				out.saveFile(outputFiles[i]);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}	
		}
	}
}

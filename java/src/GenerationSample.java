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

public class GenerationSample {

	private static final String PATH = "/home/renato/entrance/input%d.txt";
	private static final String OUT_PATH = "/home/renato/output/java%s.csv";
	private static final String[] FILES_NAMES = new String[]{"seqQuick","seqMerge","concQuick","concQuickLtd","concMerge","concMergeLtd"};

	public static void main(String[] args) {
		List<SortingImpl<String>> algorithms = new ArrayList<>();

		algorithms.add(new SequentialRandomQuicksort<>());
		algorithms.add(new SequentialMergesort<>());
		algorithms.add(new ConcurrentRandomQuicksort<>());
		algorithms.add(new ConcurrentRandomQuicksortThreadLimited<>());
		algorithms.add(new ConcurrentMergesort<>());
		algorithms.add(new ConcurrentMergesortThreadLimited<>());

		double media;
		long iniTime, endTime;

		String[] words = null;
		String[][] outputFiles = new String[6][50];

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 50; j++) {
				Input input = new Input(String.format(PATH, j + 1));
				media = 0;
				for (int k = 0; k < 10; k++) {
					try {
						words = input.getFile();
					} catch (IOException e) {
						System.err.println("Input file not found");
						System.exit(1);
					}

					iniTime = System.nanoTime();
					algorithms.get(i).sort(words);
					endTime = System.nanoTime();

					// Calculating time difference in Milliseconds
					media += (endTime - iniTime) / 1e6;
				}
				outputFiles[i][j] = String.valueOf(media / 10);
			}
			
			Output out = new Output(String.format(OUT_PATH, FILES_NAMES[i]));
			try {
				out.saveFile(outputFiles[i]);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}	
		}
	}
}

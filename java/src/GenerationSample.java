import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

	private static String OUTPUT_FOLDER = File.separator + "samples" + File.separator + "java%s.csv";
	private static String INPUT_FOLDER = File.separator + "entrance" + File.separator + "input%d.txt";

	private static List<SortingImpl<String>> algorithms = new ArrayList<>();
	private static final String[] FILES_NAMES = new String[]{"SeqQuick", "SeqMerge", "ConcQuick", "ConcQuickLtd", "ConcMerge", "ConcMergeLtd"};

	public static void main(String[] args) {
		String basePath = new File(System.getProperty("user.dir")).getParent();
		String dataPath = basePath + INPUT_FOLDER;
		String testPath = basePath + OUTPUT_FOLDER;
		
		algorithms.add(new SequentialRandomQuicksort<>());
		algorithms.add(new SequentialMergesort<>());
		algorithms.add(new ConcurrentRandomQuicksort<>());
		algorithms.add(new ConcurrentRandomQuicksortThreadLimited<>(8));
		algorithms.add(new ConcurrentMergesort<>());
		algorithms.add(new ConcurrentMergesortThreadLimited<>(8));

		double media;
		long iniTime, endTime;

		String[] words = null;
		String[][] outputFiles = new String[6][51];

		for (int i = 0; i < 6; i++) {
			outputFiles[i][0] = "file;time";
			for (int j = 0; j < 50; j++) {
				Input input = new Input(String.format(dataPath, j + 1));
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
 				outputFiles[i][j + 1] = String.format(new Locale("en", "US"), "%d;%.4f", words.length, media / 10);
			}
			
			Output out = new Output(String.format(testPath, FILES_NAMES[i]));
			try {
				out.saveFile(outputFiles[i]);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}	
		}
	}
}

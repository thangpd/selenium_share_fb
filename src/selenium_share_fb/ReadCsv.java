package selenium_share_fb;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class ReadCsv {

	
	
	public static void readCSV(String file_path,String csv_write) throws IOException, InterruptedException {

		try (Reader reader = java.nio.file.Files.newBufferedReader(Paths.get(file_path));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
				BufferedWriter writer = java.nio.file.Files.newBufferedWriter(Paths.get(csv_write));
				CSVPrinter csvPrinter = new CSVPrinter(writer,
						CSVFormat.DEFAULT.withHeader("ID", "Link", "Get link share"));) {
			int i = 0;
			for (CSVRecord csvRecord : csvParser) {
				// Accessing Values by Column Index
				String rank = csvRecord.get(0);
				String link = csvRecord.get(1);

//	                System.out.println("Record No - " + csvRecord.getRecordNumber());
//	                System.out.println("---------------");
				System.out.println("Rank : " + rank);
				System.out.println("Link : " + link);
//	                System.out.println("---------------\n\n");
				List<String> list_url_share = null;
				if (!link.isEmpty()) {
//					list_url_share = selenium_share(link);
				}
				if (list_url_share != null) {

					for (String str : list_url_share) {
						System.out.println(str);
						csvPrinter.printRecord(Arrays.asList(rank, link, str));
					}
				} else {
					csvPrinter.printRecord(Arrays.asList(rank, link, "error. Need checks."));
				}

				if (i > 300) {
					csvPrinter.flush();
					System.out.println("break");
					break;
				}
				i++;

			}

		}

	}
	
	
}

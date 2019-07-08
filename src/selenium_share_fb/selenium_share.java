package selenium_share_fb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class selenium_share {
	private static final String SAMPLE_CSV_FILE_PATH = "./temp1.csv";
	private static final String SAMPLE_CSV_WRITE_FILE = "./write3.csv";

	public static void main(String[] arg) throws InterruptedException, IOException {
		//get facebook share.
		
//		sel_google as = new sel_google();
		
		
		
		
		
		readCSV();

	}
	

	public static void readCSV() throws IOException, InterruptedException {

		try (Reader reader = java.nio.file.Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
				BufferedWriter writer = java.nio.file.Files.newBufferedWriter(Paths.get(SAMPLE_CSV_WRITE_FILE));
				CSVPrinter csvPrinter = new CSVPrinter(writer,
						CSVFormat.DEFAULT.withHeader("ID", "Link", "Get link share"));) {
			int i = 0;
			for (CSVRecord csvRecord : csvParser) {
				// Accessing Values by Column Index
				System.out.println(csvRecord.get(0));
//				String rank = csvRecord.get(0);
				String link = csvRecord.get(0);

	                System.out.println("Record No - " + csvRecord.getRecordNumber());
	                System.out.println("---------------");
//				System.out.println("Rank : " + rank);
				System.out.println("Link : " + link);
	                System.out.println("---------------\n\n");
				List<String> list_url_share = null;
				if (!link.isEmpty()) {
					list_url_share = selenium_share(link);
				}
				if (list_url_share != null) {
					for (String str : list_url_share) {
						System.out.println(str);
						csvPrinter.printRecord(Arrays.asList(i, link, str));
					}
				} else {
					csvPrinter.printRecord(Arrays.asList(i, link, "error. Need checks."));
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

	public static List<String> convertWebtoArr(List<WebElement> list_link_share) {
		if (list_link_share != null) {
			List<String> list_url_share = new ArrayList<String>();

			for (WebElement web : list_link_share) {
				String ahref = "null";
				try {
					WebElement a_href_share = web.findElement((By.xpath("./span[1]/span[1]/a")));
					ahref = a_href_share.getAttribute("href");
				} catch (Exception ex) {
					System.out.println("can't get ahref");
				}

//			System.out.println(a_href_share);
//			System.out.println(a_href_share.getAttribute("href"));
//			csvPrinter.printRecord(Arrays.asList(rank, link, a_href_share.getAttribute("href")));
				list_url_share.add(ahref);
			}
			List<String> newList = new ArrayList<String>(new HashSet<String>(list_url_share));
			return newList;
		} else {

			return null;

		}
	}

	public static List<String> selenium_share(String url_share) throws InterruptedException {

		
		
		System.setProperty("webdriver.gecko.driver", "chromedriver");

		WebDriver driver = new ChromeDriver();

		// create file named Cookies to store Login Information
		File file = new File("Cookies.data");
		if (!file.exists()) {
			driver.get("https://facebook.com");

			driver.findElement(By.id("email")).sendKeys("hacker.dicaucodoi@gmail.com");
			driver.findElement(By.id("pass")).sendKeys("Coloterolita!@#$");
			driver.findElement(By.id("loginbutton")).click();
			System.out.println("Successfully logged in");

			try {
				// Delete old file if exists
				file.delete();
				file.createNewFile();
				FileWriter fileWrite = new FileWriter(file);
				
				BufferedWriter Bwrite = new BufferedWriter(fileWrite);

				// loop for getting the cookie information
				for (Cookie ck : driver.manage().getCookies()) {
					Bwrite.write((ck.getName() + ";" + ck.getValue() + ";" + ck.getDomain() + ";" + ck.getPath() + ";"
							+ ck.getExpiry() + ";" + ck.isSecure()));
					Bwrite.newLine();
				}
				Bwrite.close();
				fileWrite.close();

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} else {

			// open facebook before apply cookie
			driver.get("https://facebook.com");
			try {

				FileReader fileReader = new FileReader(file);
				BufferedReader Buffreader = new BufferedReader(fileReader);
				String strline;
				while ((strline = Buffreader.readLine()) != null) {
					System.out.println(strline);
					StringTokenizer token = new StringTokenizer(strline, ";");
					while (token.hasMoreTokens()) {
						String name = token.nextToken();
						String value = token.nextToken();
						String domain = token.nextToken();
						String path = token.nextToken();
						Date expiry = null;
						DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
						String val;
						if (!(val = token.nextToken()).equals("null")) {
							expiry = df.parse(val);
						}
						Boolean isSecure = new Boolean(token.nextToken()).booleanValue();
						Cookie ck = new Cookie(name, value, domain, path, expiry, isSecure);
						System.out.println(ck);
						driver.manage().addCookie(ck); // This will add the stored cookie to your current session
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			// reopen facebook.
			// https://www.facebook.com/me.n.nam.9/posts/1619928014804292
			// https://www.facebook.com/hoanganh.bae96/videos/988536971344014/
			if (!url_share.isEmpty()) {
				driver.get(url_share);
			} else {
				return null;
			}

		}
		// href="/shares/view/?av=100024890809605"

		try {
			Thread.sleep(4000);
			System.out.println("share click");
			driver.findElement(By.cssSelector("a[href*='/shares/view']")).click();
		} catch (Exception ex) {
		}

//		WebElement page = wait.until(ExpectedConditions.elementToBeClickable(pageLocator(pageNumber)));
		WebElement uiMorepage = null;
		Boolean go_on;

		// css=.uiMorePager
		try {
			Thread.sleep(4000);
			uiMorepage = driver.findElement(By.cssSelector(".uiMorePager"));
			go_on = true;
		} catch (Exception ex) {
			go_on = false;
		}

		if (go_on) {
			do {
				System.out.println(".uiMorePager");

				try {
//					Thread.sleep(1000);
					uiMorepage.click();

					Thread.sleep(2000);
					uiMorepage = driver.findElement(By.cssSelector(".uiMorePager"));
				} catch (Exception ex) {
					// ex.printStackTrace();
					go_on = false;
					System.out.println("false go on");
				}
			} while (go_on);
		}
		List<WebElement> group_share = new ArrayList<WebElement>();
		try {
			// group_share=driver.findElements(By.cssSelector("div[id*='feed_subtitle']"));

			// feed_subtitle_
			// aria-label
			// [not(@aria-label) or not(@data-testid) or not(@role)]
			group_share = driver.findElements(By.xpath("//div[contains(@id,'feed_subtitle_')]"));
			System.out.println(group_share);
		} catch (Exception ex) {

			ex.printStackTrace();

		}
		if (group_share.size() > 0) {
			System.out.println("end");
			List<String> res = convertWebtoArr(group_share);
			driver.quit();

			return res;
			/*
			 * for (WebElement web : group_share) { System.out.println(web);
			 * System.out.println(web.getAttribute("href")); }
			 */
		} else {

			System.out.println("null");

			driver.quit();
			return null;

		}
	}
}

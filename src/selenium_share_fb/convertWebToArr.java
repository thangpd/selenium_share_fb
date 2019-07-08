package selenium_share_fb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class convertWebToArr {



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

	
}

package selenium_share_fb;

import org.testng.asserts.*;

import org.testng.Assert;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class FirstTestCase {
	public static void main (String args[]) {
		System.setProperty("webdriver.gecko.driver", "chromedriver");

		WebDriver driver = new ChromeDriver();
		
		
		String baseUrl="https://batdongsan.com.vn/nha-dat-ban/";
		String pagination="p";
		
		driver.get(baseUrl);
		
		
		
		driver.close();
		
		
		
		
	}
	

}

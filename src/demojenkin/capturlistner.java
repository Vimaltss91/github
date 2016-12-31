package demojenkin;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.Test;

public class capturlistner {

	public WebDriver driver;
	
	@Test
	public void test(){
		driver= new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		EventFiringWebDriver event1= new EventFiringWebDriver(driver);
		Listner handle= new Listner();
		event1.register(handle);
		event1.get("https://www.facebook.com/");
		event1.get("https://qamig.cdi.metlife.com/siteminderagent/forms/login.fcc?TYPE=33554433&REALMOID=06-8814253a-8673-10c7-b249-83e0944bff17&GUID=&SMAUTHREASON=0&METHOD=GET&SMAGENTNAME=$SM$1eMjEbxC6mE%2fFoqyBrVjrXOjZwB17an%2fSWYEqTE5Iarg3%2f0AQN2tiKWTCCjbA0wI&TARGET=$SM$%2fcdiweb%2findex%2ejsp");
		//event1.findElement(By.id("email")).sendKeys("vimaltss91");
		//event1.findElement(By.id(id))
		//event1.navigate().to("https://www.gmail.com/");
	}
	
}

package demojenkin;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class gmail {

public WebDriver driver;

@BeforeTest
public void before(){
	driver= new ChromeDriver();
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	driver.get("https://accounts.google.com/ServiceLogin?service=mail&continue=https://mail.google.com/mail/#identifier");
}

@Test
public void test() throws Exception{
	driver.findElement(By.id("Email")).sendKeys("vimaltss91");
	driver.findElement(By.id("next")).submit();
	driver.findElement(By.id("Passwd")).sendKeys("vimaltss135");
	driver.findElement(By.id("signIn")).submit();
	Thread.sleep(500);
	driver.findElement(By.xpath("//*[@class='T-I J-J5-Ji T-I-KE L3']")).click();
	String expectedtitle=driver.getTitle();
	System.out.println("Expected title is "+ expectedtitle);
	SoftAssert softass= new SoftAssert();
	String actual="Inbox (3,836) - vimaltss91@gmail.com - Gmsdil";
	softass.assertEquals(expectedtitle, actual);
	System.out.println("Clicked compose");
	Thread.sleep(200);
	//driver.findElement(By.xpath("//div[@class='Am Al editable LW-avf']")).sendKeys("hello");
	softass.assertAll();
	System.out.print("Test methode successful");
}

@Test
@Parameters ({"fname","lname"})
public void compose(String fname, String lname) throws Exception{
	System.out.println("Parameter first name "+ fname);
	System.out.println("Parameter last name "+ lname);
	String expectedtitle1=driver.getTitle();
	System.out.println("Expected title is in compose methode "+ expectedtitle1);
	SoftAssert softass= new SoftAssert();
	String actual="Inbox (3,836) - vimaltss91@gmail.com - Gmsdil";
	softass.assertEquals(expectedtitle1, actual);
	//softass.assertAll();
	softass.assertAll();
	System.out.print("compose methode successful");
}


}

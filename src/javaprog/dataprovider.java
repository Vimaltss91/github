package javaprog;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class dataprovider {

	public WebDriver driver;
	public WebElement element;
	
	@BeforeClass
	public void before() throws Exception{
		
		driver= new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.get("http://localhost:8087/login.do");
	}
	
	@Test 
//	@Parameters({"nusername","npass"})
	public void test() throws Exception{
		try{
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.xpath("//*[@name='pwd']")).sendKeys("manager");
		driver.findElement(By.id("loginButton")).click();
		WebDriverWait wait= new WebDriverWait(driver,10);
		element=wait.until(ExpectedConditions.elementToBeClickable(By.id("logoutLink")));
		Thread.sleep(3000);
		JavascriptExecutor js= (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0,4000)", "");
		//element.click();
		}
		catch(Exception e){
			System.out.println("No processed properly "+e);
		}
	}
	
	/*@DataProvider
	public Object[][] login(){
		return new Object[][]{
			{"admin","manager"},
			{"admin","manager"},
			{"admin","manager1"}
		};
	}*/
	
	
	
	
	
	
	
	
}

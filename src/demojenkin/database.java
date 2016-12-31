package demojenkin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class database {

	String mySQL,myDB,mySQLDB,myDriver,myUser,myPass;
	String myQ1,myQ2;
	Connection mycon=null;
	Statement  mySt;
	PreparedStatement myPre;
	ResultSet rs1,rs2;
	String p1,p2;
	public WebDriver driver;
	public String email;
	
	@BeforeTest
	public void before(){
		
		//Github updated.
		mySQL="jdbc:mysql://localhost:3306/";
		myDB="dlearn";
		mySQLDB= mySQL+myDB;
		myDriver="com.mysql.jdbc.Driver";
		myUser="root";
		myPass="12345";
		driver= new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://accounts.google.com/ServiceLogin?service=mail&continue=https://mail.google.com/mail/#identifier");
	}
	
	@Test
	public void test() throws Exception{
		System.out.println("inside test Method");
	//	Object myDC = Class.forName(myDriver).newInstance();
		mycon=DriverManager.getConnection(mySQLDB,myUser,myPass);
		mySt=mycon.createStatement();
		//myQ2="Delete from tcourse where id=6";
		//myQ2= "INSERT INTO tCourse (id,name,cost) VALUES(6,'vimaltss91@gmail.com','1234')";
		//mySt.executeUpdate(myQ2);
		myQ1= "Select *from tCourse where id=6";
		System.out.println("After Query Statement");
		rs1=mySt.executeQuery(myQ1);
		while(rs1.next()){
	    email= rs1.getString("name");
		System.out.print(rs1.getInt("id") + "------");
		System.out.print(rs1.getString("name") + "------------");
		System.out.print(rs1.getString("cost")+"  ---------------");
		System.out.println();
	}
		driver.findElement(By.id("Email")).sendKeys(email);
	}
	@AfterTest
	public void myTeardown() throws Exception{
		// close the DB connection
		mycon.close();
	}
}

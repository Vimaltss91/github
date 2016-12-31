package demojenkin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class excel {
	
	public WebDriver driver;
	public String user,pass;
	public int xRows,xCols,count;
	public String xData[][];
	public String result;
	
	@BeforeTest
	public void setIp() throws Exception{
	String xpath="C:/Users/IBM_ADMIN/Downloads/DSI.xls";
	xlread(xpath);
	driver= new InternetExplorerDriver();
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	driver.manage().window().maximize();
	}
	
	@Test
	public void test() throws Exception{
		
		for(count=1;count< xRows;count++){
			user=xData[count][0];
			pass=xData[count][1];
			
		driver.get("https://accounts.google.com/Login#identifier");
		username();
		password();
		
		}
	}
	
	public void username()throws Exception{
		
		System.out.println("User value from excel sheet is "+ user);
		//Thread.sleep(2000);
		
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("Email")).sendKeys(user);
		driver.findElement(By.id("next")).click();
		Thread.sleep(100);
		if (driver.findElement(By.id("errormsg_0_Email")).isDisplayed()){
			System.out.println("Invalid User name");
			String res ="Invalid Username";
			xData[count][2]=res;
		}
		else{
			String res1="Valid Username";
			xData[count][2]=res1;
		
		}
	}
	
	public void password() throws Exception{
			//System.out.println("Inside pass "+count);
		    String logintitle="https://accounts.google.com/Login#password";
		    Thread.sleep(1000);
		    String title=driver.getCurrentUrl();
		    System.out.println("Actual title is   " +logintitle);
		    System.out.println("Expected title is " +title);
		    
		    if (logintitle!=title){
		    	System.out.println("Inside Password if condition");
		    	driver.findElement(By.id("Passwd")).clear();
					driver.findElement(By.id("Passwd")).sendKeys(pass);
					driver.findElement(By.id("signIn")).click();
				}
		    else{
		    	System.out.println("If condition is not passed");
		    }
		    }
			
	
	@AfterTest
	public void teardown() throws Exception{
		String xwrite="C:/Users/IBM_ADMIN/Downloads/DSI_Res.xls";
		xlwrite(xwrite,xData);
		
	}
	
public void xlread(String spath) throws Exception{
	File myxl= new File(spath);
	FileInputStream mystream = new FileInputStream(myxl);
	HSSFWorkbook myb= new HSSFWorkbook(mystream);
	HSSFSheet mysheet= myb.getSheetAt(1);
	xRows= mysheet.getLastRowNum()+1;
	xCols= mysheet.getRow(0).getLastCellNum();
	xData= new String[xRows][xCols];
	
	for (int i=0; i<xRows;i++){
		HSSFRow row= mysheet.getRow(i);
		for(int j=0; j<xCols;j++){
			HSSFCell cell=row.getCell(j);
			String value= cellToString(cell);
			xData[i][j]=value;
			System.out.println(value);
		}
	}
}

public static String cellToString(HSSFCell cell){
	int type= cell.getCellType();
	Object result;
	switch(type){
	case HSSFCell.CELL_TYPE_BLANK:
		result="-";
		break;
	case HSSFCell.CELL_TYPE_BOOLEAN:
		result=cell.getBooleanCellValue();
		break;
	case HSSFCell.CELL_TYPE_NUMERIC:
		result=cell.getStringCellValue();
		break;
	case HSSFCell.CELL_TYPE_STRING:
		result=cell.getStringCellValue();
		break;
	case HSSFCell.CELL_TYPE_ERROR:
		throw new RuntimeException("This is eerro cell");
	case HSSFCell.CELL_TYPE_FORMULA:
		throw new RuntimeException("THis is formula:");
		default:throw new RuntimeException("Default Error");
	}
	return result.toString();
}
public void xlwrite(String xlpath, String[][] xldata) throws Exception{
	File outfile= new File(xlpath);
	HSSFWorkbook wb= new HSSFWorkbook();
	HSSFSheet sheet= wb.createSheet("Final");
	
	for(int myrow=0; myrow<xRows;myrow++){
		HSSFRow row=sheet.createRow(myrow);
		for( int mycol=0;mycol<xCols;mycol++){
			HSSFCell cell= row.createCell(mycol);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(xldata[myrow][mycol]);
		}
		FileOutputStream fout= new FileOutputStream(outfile);
		wb.write(fout);
		fout.flush();
		fout.close();
	}
}

}

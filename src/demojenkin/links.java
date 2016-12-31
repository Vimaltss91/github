package demojenkin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class links {

	public WebDriver driver;
	int xRows,xCols,links;
	String [][] xData;
	
	@BeforeTest
	public void before() throws Exception{
		String xpath="C:/Users/IBM_ADMIN/Downloads/DSI.xls";
		xlRead(xpath);
	driver= new ChromeDriver();	
	driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	driver.manage().window().maximize();
	driver.get("https://www.apk4fun.com/history/6159/");		
	}
	
	@Test
	public void main(){
		linkscoll();
	}
	
	public void linkscoll(){
		List <WebElement> link= driver.findElements(By.tagName("a"));
		System.out.println("Total links are "+link.size());
		boolean isvalid;
		links= link.size();
		for(int i=1;i<5;i++){
			isvalid= getResponseCode(link.get(i).getAttribute("href"));
			if (isvalid){
				String valid=link.get(i).getAttribute("href");
				xData[i][0]=valid;
				System.out.println("Valid link is " +link.get(i).getAttribute("href"));
			}else{
					String invalid=link.get(i).getAttribute("href");
					xData[i][1]=invalid;
					System.out.println("InValid link is " +link.get(i).getAttribute("href"));
				}
			}
		}
	
	public  boolean getResponseCode(String url){
		boolean isvalid=false;
		
		try{
			URL u=new URL(url);
			HttpURLConnection h= (HttpURLConnection) u.openConnection();
			h.setRequestMethod("GET");
			h.connect();
			System.out.print("Response code is "+h.getResponseCode());
			
			if(h.getResponseCode()!=404){
				isvalid=true;
			}
		}catch (Exception e){
			//System.out.println("Exception is "+e);
		}
		
		
		return isvalid;
	}
	
	@AfterTest
	public void teardown() throws Exception{
		String xRPath = "C:/Users/IBM_ADMIN/Downloads/DSI_Res.xls";
		xlwrite(xRPath, xData);
	}
	
	
	public void xlRead(String sPath) throws Exception{
		File myxl = new File(sPath);
		FileInputStream myStream = new FileInputStream(myxl);

		HSSFWorkbook myWB = new HSSFWorkbook(myStream);
		//HSSFSheet mySheet = new HSSFSheet(myWB);
		//HSSFSheet mySheet = myWB.getSheetAt(0);	// Referring to 1st sheet
		HSSFSheet mySheet = myWB.getSheetAt(0);	// Referring to 3rd sheet
		//int xRows = mySheet.getLastRowNum()+1;
		//int xCols = mySheet.getRow(0).getLastCellNum();
		xRows = mySheet.getLastRowNum()+1;
		xCols = mySheet.getRow(0).getLastCellNum();
		//String[][] xData = new String[xRows][xCols];
		xData = new String[xRows][xCols];
        for (int i = 0; i < xRows; i++) {
	           HSSFRow row = mySheet.getRow(i);
	            for (int j = 0; j < xCols; j++) {
	               HSSFCell cell = row.getCell(j); // To read value from each col in each row
	               String value = cellToString(cell);
	               xData[i][j] = value;
	        
	               }
	           
	        }

	}

	public static String cellToString(HSSFCell cell) {
	// This function will convert an object of type excel cell to a string value
        int type = cell.getCellType();
        Object result;
        switch (type) {
            case HSSFCell.CELL_TYPE_NUMERIC: //0
                result = cell.getNumericCellValue();
                break;
            case HSSFCell.CELL_TYPE_STRING: //1
                result = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_FORMULA: //2
                throw new RuntimeException("We can't evaluate formulas in Java");
            case HSSFCell.CELL_TYPE_BLANK: //3
                result = "-";
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN: //4
                result = cell.getBooleanCellValue();
                break;
            case HSSFCell.CELL_TYPE_ERROR: //5
                throw new RuntimeException ("This cell has an error");
            default:
                throw new RuntimeException("We don't support this cell type: " + type);
        }
        return result.toString();
    }
	public void xlwrite(String xlPath, String[][] xldata) throws Exception {

    	File outFile = new File(xlPath);
        HSSFWorkbook wb = new HSSFWorkbook();
           // Make a worksheet in the XL document created
        /*HSSFSheet osheet = wb.setSheetName(1,"TEST");*/
        HSSFSheet osheet = wb.createSheet("Links");
        // Create row at index zero ( Top Row)
    	for (int myrow = 0; myrow < xRows; myrow++) {
    		//System.out.println("Inside XL Write");
	        HSSFRow row = osheet.createRow(myrow);
	        // Create a cell at index zero ( Top Left)
	        for (int mycol = 0; mycol < xCols; mycol++) {
	        	HSSFCell cell = row.createCell(mycol);
	        	// Lets make the cell a string type
	        	cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        	// Type some content
	        	cell.setCellValue(xldata[myrow][mycol]);
	        	//System.out.print("..." + xldata[myrow][mycol]);
	        }
	        //System.out.println("..................");
	        // The Output file is where the xls will be created
	        FileOutputStream fOut = new FileOutputStream(outFile);
	        // Write the XL sheet
	        wb.write(fOut);
	        fOut.flush();
//		    // Done Deal..
	        fOut.close();
    	}
    }
	
}

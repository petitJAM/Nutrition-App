package foodImaging;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jxl.LabelCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

public class NutritionFacts {
	
	public int calories;
	public int calFromFat;
	public int totalFat;
	public int protein;
	public int sugar;
	public int fiber;
	public int carbs;
	public int sodium;
	
	public String name;
	
	private static final int calCol = 2;
	private static final int 


	public NutritionFacts(String modelName) {
		this.name = modelName;
	}
	
	public void readExcel() {
		try
	    {
	      String filename = "data//Food Data.xls";
	      WorkbookSettings ws = new WorkbookSettings();
	      ws.setLocale(new Locale("en", "EN"));

	      Workbook workbook = Workbook.getWorkbook(
	        new File(filename),ws);

	      Sheet s  = workbook.getSheet(0);
	      readDataSheet(s);
	      workbook.close();      
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    catch (BiffException e)
	    {
	      e.printStackTrace();
	    }
	}

	private void readDataSheet(Sheet s) {
	    LabelCell lc = s.findLabelCell(this.name);
	    int row = lc.getRow();
	    this.calories = (int) ((NumberCell) s.getCell(calCol, row)).getValue();
	    this.calFromFat = (int) ((NumberCell) s.getCell(calCol, row)).getValue();
	    this.totalFat = (int) ((NumberCell) s.getCell(calCol, row)).getValue();
	    this.protein = (int) ((NumberCell) s.getCell(calCol, row)).getValue();
	    this.sugar = (int) ((NumberCell) s.getCell(calCol, row)).getValue();
	    this.fiber = (int) ((NumberCell) s.getCell(calCol, row)).getValue();
	    this.carbs = (int) ((NumberCell) s.getCell(calCol, row)).getValue();
	    this.sodium = (int) ((NumberCell) s.getCell(calCol, row)).getValue();
	    
		
	}

}

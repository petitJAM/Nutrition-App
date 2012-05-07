package foodImaging;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jxl.LabelCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

/**
 * This class is used for storing/collecting the nutrition information for each food.
 * 
 * @author Rob Wagner
 * Last Updated
 */

public class NutritionFacts {
	
	public float calories;
	public float calFromFat;
	public float totalFat;
	public float protein;
	public float sugar;
	public float fiber;
	public float carbs;
	public float sodium;
	
	public String name;
	
	private static final int calCol = 2;
	private static final int fatCol = 4;
	private static final int proCol = 3;
	private static final int sugCol = 8;
	private static final int fibCol = 7;
	private static final int carbCol = 6;
	private static final int sodCol = 14;


	public NutritionFacts(String modelName) {
		this.name = modelName;
		readExcel();
	}
	
	public void readExcel() {
		try
	    {
	      String filename = "data//Food Data.xls";
	      WorkbookSettings ws = new WorkbookSettings();
	      ws.setLocale(new Locale("en", "EN"));

	      Workbook workbook = Workbook.getWorkbook(new File(filename),ws);

	      Sheet s  = workbook.getSheet(0);
	      readDataRow(s);
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

	/**
	 * Reads the row of the corresponding food in the excel spreadsheet
	 * 
	 * @param s - excel worksheet
	 */
	private void readDataRow(Sheet s) {
	    LabelCell lc = s.findLabelCell(this.name);
	    int row = lc.getRow();
	    this.calories = getValue(s, calCol, row);
	    this.totalFat = getValue(s, fatCol, row);
	    this.calFromFat = Math.round(this.totalFat * 9);
	    this.protein = getValue(s, proCol, row);
	    this.sugar = getValue(s, sugCol, row);
	    this.fiber = getValue(s, fibCol, row);
	    this.carbs = getValue(s, carbCol, row);
	    this.sodium = getValue(s, sodCol, row);
	}
	
	/**
	 * Handles getting the values from the spreadsheet nicely
	 * 
	 * @param s - the worksheet
	 * @param col
	 * @param row
	 * @return value of the cell (as a double)
	 */
	
	private float getValue (Sheet s, int col, int row) {
		String val = s.getCell(col, row).getContents();
		if(val.isEmpty())
			return 0;
		return Float.parseFloat(val);
	}
	
	/**
	 * @return Handy printout for testing purposes.
	 */
	public String stringify() {
		return ("=====================================================\n"
				+ "Name: " + this.name + "\n"
				+ "Calories: " + this.calories + "\n"
				+ "Fat: " + this.totalFat + "\n"
				+ "Calories from Fat: " + this.calFromFat + "\n"
				+ "Protein: " + this.protein + "\n"
				+ "Sugar: " + this.sugar + "\n"
				+ "Fiber: " + this.fiber + "\n"
				+ "Carbs: " + this.carbs + "\n"
				+ "Sodium: " + this.sodium + "\n" +
				"=====================================================\n");
	}
}

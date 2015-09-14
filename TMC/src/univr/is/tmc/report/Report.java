package univr.is.tmc.report;

import univr.is.tmc.database.MyDriver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.jdbc.JDBCCategoryDataset;
import org.jfree.data.jdbc.JDBCPieDataset;
import org.jfree.data.jdbc.JDBCXYDataset;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.util.Rotation;

import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;

public class Report {


	//private String maxVelVeicolo;			// Allarme
	//private String countFurtiVeicolo;
	//private String countAllarmiVeicolo;

	// ============== COSTRUTTORE ====================================================

	public Report() { }

	// ============== METODI ==========================================================

	/**
	 * Ricava il numero di allarmi per ogni Veicolo
	 *
	 * @return JDBCCategoryDataset contiene velocita massima di ogni veicolo
	 */

	public static JDBCCategoryDataset maxVelVeicolo() 
	{
		JDBCCategoryDataset res = null;
		try {
			MyDriver driver = MyDriver.getInstance();
			String query = "SELECT targa, MAX(velocita) FROM Allarme GROUP BY targa";
			res = driver.executeCategory(query);
		} catch (SQLException e) {
			System.out.println("Select fallita! " + e);
		}
		return res;
	}

	/**
	 * Ricava il numero di furti per ogni Veicolo
	 *
	 * @return JDBCCategoryDataset contiene il numero di Furti per veicolo
	 */

	public static JDBCPieDataset countFurtiVeicolo() 
	{
		JDBCPieDataset res = null;
		try {
			MyDriver driver = MyDriver.getInstance();
			String query = "SELECT targa, COUNT(*) FROM Furto GROUP BY targa";
			res = driver.executePie(query);
		} catch (SQLException e) {
			System.out.println("Select fallita! " + e);
		}
		return res;
	}

	/**
	 * Ricava il numero di allarmi per ogni Veicolo
	 *
	 * @return JDBCCategoryDataset contiene il numero di Allarmi per veicolo
	 */

	public static JDBCPieDataset countAllarmiVeicolo() 
	{
		JDBCPieDataset res = null;
		try {
			MyDriver driver = MyDriver.getInstance();
			String query = "SELECT targa, COUNT(*) FROM Allarme GROUP BY targa";
			res = driver.executePie(query);
		} catch (SQLException e) {
			System.out.println("Select fallita! " + e);
		}
		return res;
	}

	/**
	 * Esporta Grafici in PNG
	 */

	public static void exportPNG(File name, JFreeChart chart, int x, int y) throws IOException
	{
		ChartUtilities.saveChartAsPNG(name,chart,x,y);
	}

}

package univr.is.tmc.servlet;

import univr.is.tmc.report.Report;

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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReportServlet extends HttpServlet {

    /**
	 * ReportServlet, 
     * IF (azione = "Stampa Report")
	 * 	 esegue stampaReport()
	 * 	 messaggio = esito
	 * 	 vai a stampaEffettuata.jsp
	 * 
	 */

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		String azione = request.getParameter("azione");

		if (azione.equalsIgnoreCase("Stampa Report")) {
			// Azzera
			if (this.stampaReport()) {
				// Azzeramento andato a buon fine
				request.getSession().setAttribute("messaggio", "Report stampato con successo!");
			} else {
				// Azzeramento non andato a buon fine
				request.getSession().setAttribute("messaggio", "Errore!");
			}
			response.sendRedirect("stampaEffettuata.jsp");
		}
	}

	private boolean stampaReport()
	{
		try {
			JFreeChart graficoMaxVel = ChartFactory.createBarChart(
				"Velocità massima Veicoli", 
				"Veicoli", 
				"Velocità", 
				Report.maxVelVeicolo(), 
				PlotOrientation.VERTICAL, true, true, false);
		
			JFreeChart graficoFurtiVeicoli = ChartFactory.createPieChart(
				"Numero furti per Veicolo", 
				Report.countFurtiVeicolo(), 
				true, true, false);

			JFreeChart graficoAllarmiVeicoli = ChartFactory.createPieChart(
				"Numero allarmi per Veicolo", 
				Report.countAllarmiVeicolo(), 
				true, true, false);

			//exportPNG(new File("MaxVel.png"),graficoMaxVel,450,450);
			//exportPNG(new File("FurtiVeicoli.png"),graficoFurtiVeicoli,450,450);
			//exportPNG(new File("AllarmiVeicoli.png"),graficoAllarmiVeicoli,450,450);

			String path = getServletContext().getRealPath("/");
		    Document document = new Document();
		    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path+"resources/pdf/finalReport.pdf"));
		    document.open();

		    PdfContentByte cb = writer.getDirectContent();
		    float width = PageSize.A4.getWidth();
		    float height = PageSize.A4.getHeight() / 3;

		    PdfTemplate gr1 = cb.createTemplate(width, height);
		    Graphics2D g1d1 = new PdfGraphics2D(gr1, width, height);
		    Rectangle2D r1d1 = new Rectangle2D.Double(0, 0, width, height);
		    graficoMaxVel.draw(g1d1, r1d1);
		    g1d1.dispose();
		    cb.addTemplate(gr1, 0, height*2);

		    PdfTemplate gr2 = cb.createTemplate(width, height);
		    Graphics2D g2d2 = new PdfGraphics2D(gr2, width, height);
		    Rectangle2D r2d2 = new Rectangle2D.Double(0, 0, width, height);
		    graficoFurtiVeicoli.draw(g2d2, r2d2);
		    g2d2.dispose();
		    cb.addTemplate(gr2, 0, height);

		    PdfTemplate gr3 = cb.createTemplate(width, height);
		    Graphics2D g3d3 = new PdfGraphics2D(gr3, width, height);
		    Rectangle2D r3d3 = new Rectangle2D.Double(0, 0, width, height);
		    graficoAllarmiVeicoli.draw(g3d3, r3d3);
		    g3d3.dispose();
		    cb.addTemplate(gr3, 0, 0);

		    document.close();

		}catch(IOException e){
			return false;
		}
		catch(DocumentException e){
			return false;
		}
		return true;
	}
}

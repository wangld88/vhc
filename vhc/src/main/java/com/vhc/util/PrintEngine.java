package com.vhc.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vhc.core.model.Inventory;
import com.vhc.core.model.Product;


public class PrintEngine implements Printable {

	private final int PAGE_WIDTH = 130;
	private final int PAGE_HEIGHT = 200;
	private final int IMAGE_WIDTH = 120;
	private final int IMAGE_HEIGHT = 180;
	private final String DATA_FORMAT = "dd/MMM/yyyy HH:mm";

	private final Logger logger = LoggerFactory.getLogger(PrintEngine.class);

	PrinterJob printJob;

	private List<Inventory> saleList;

	public PrintEngine() throws Exception {
		PrintService printService = PrintServiceLookup
				   .lookupDefaultPrintService();
		logger.info("Print Service: " + printService.getName());

		printJob = PrinterJob.getPrinterJob();
		printJob.setPrintService(printService);
		printJob.setPrintable(this);

	}

	public void setPrintData(List<Inventory> saleList) {
		this.saleList = saleList;
	}

	@Override
	public int print(Graphics g, PageFormat pageFormat, int page) {

		logger.info("Start Print the following page: "+page);
		if(pageFormat == null) {
			Paper paper = new Paper();

			paper.setSize(PAGE_WIDTH, PAGE_HEIGHT);
			pageFormat = new PageFormat();
			pageFormat.setPaper(paper);
		} else {
			logger.info("X:" + pageFormat.getImageableX() + ",Y: " + pageFormat.getImageableY() + ", width: " + pageFormat.getImageableWidth() + ", height: " + pageFormat.getImageableHeight());
			Paper paper = pageFormat.getPaper();
			paper.setSize(PAGE_WIDTH, PAGE_HEIGHT);
			paper.setImageableArea(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
			pageFormat.setPaper(paper);
		}

		if (page == 0) {
			SimpleDateFormat formatter = new SimpleDateFormat(DATA_FORMAT);
			Calendar cal = Calendar.getInstance();
			Date current = cal.getTime();

			String outputDate = formatter.format(current);

			Graphics2D g2d = (Graphics2D) g;
			g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
			FontMetrics fontMetrics = g.getFontMetrics();
			g2d.setColor(Color.black);
			g2d.translate(pageFormat.getImageableWidth(), pageFormat.getImageableHeight());

			g.setFont(new Font("TimesRoman", Font.BOLD, 11));
			g.drawString("My Pairs", getPosition("My Pairs", fontMetrics), 10);

			g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
			fontMetrics = g.getFontMetrics();
			g.drawString("3303 Bayview Ave, North York, ON", getPosition("3303 Bayview Ave, North York, ON", fontMetrics), 30);
			g.drawString("Phone: 416-730-1356", getPosition("Phone: 416-730-1356", fontMetrics), 50);
			g.drawString(outputDate, 0, 70);

			BigDecimal subTotal = BigDecimal.ZERO;
			BigDecimal tax = BigDecimal.ZERO;
			BigDecimal total = BigDecimal.ZERO;

			int line = 100;
			for(Inventory i : saleList) {
				Product p = i.getItem().getProduct();
				subTotal = subTotal.add(p.getRetail());
				g.drawString(getSpace(p.getName(),"$"+p.getRetail().toPlainString(),fontMetrics), 0, line);
				line += 20;
				tax = tax.add(p.getRetail().multiply(new BigDecimal(p.getTax())).divide(new BigDecimal("100")));
			}
			tax = tax.setScale(2, BigDecimal.ROUND_HALF_UP);
			total = total.add(subTotal).add(tax);
			g.drawString(getSpace("Sub-Total:","$"+subTotal.toPlainString(),fontMetrics), 0, line);
			g.drawString(getSpace("Tax:","$"+tax.toPlainString(),fontMetrics), 0, line+20);
			g.drawString(getSpace("Total:","$"+total.toPlainString(),fontMetrics), 0, line+40);
			g.drawString("*RETURN POLICY*", getPosition("*RETURN POLICY*", fontMetrics), line+70);
			g.drawString("Exchange/credit/refund within", getPosition("Exchange/credit/refund within", fontMetrics), line+80);
			g.drawString("30 days with original receipt", getPosition("30 days with original receipt", fontMetrics), line+90);
			g.drawString("THANK YOU!", getPosition("THANK YOU!", fontMetrics), line+100);

			return (PAGE_EXISTS);
		} else {
			return (NO_SUCH_PAGE);
		}
	}


	private int getPosition(String str, FontMetrics fontMetrics) {
		int rtn = 0;

		rtn = (IMAGE_WIDTH - fontMetrics.stringWidth(str))/2;

		logger.info("Position: "+rtn);
		return rtn;
	}


	private String getSpace(String str1, String str2, FontMetrics fontMetrics) {
		String rtn = "";
		int num = IMAGE_WIDTH - fontMetrics.stringWidth(str1) - fontMetrics.stringWidth(str2);

		logger.info("space width: " + fontMetrics.stringWidth(" 2") + ", string: "+fontMetrics.stringWidth("S"));
		for(int i=0; i<num/3; i++)
			rtn += " ";

		rtn = str1 + rtn + str2;

		return rtn;
	}

	public void printBanner(String text) {

		int width = 200;
		int height = 30;

		//BufferedImage image = ImageIO.read(new File("/Users/mkyong/Desktop/logo.jpg"));
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setFont(new Font("SansSerif", Font.BOLD, 20));

		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics.drawString(text, 10, 20);

		//save this image
		//ImageIO.write(image, "png", new File("/users/mkyong/ascii-art.png"));

		for (int y = 0; y < height; y++) {
		    StringBuilder sb = new StringBuilder();
		    for (int x = 0; x < width; x++) {

		        sb.append(image.getRGB(x, y) == -16777216 ? " " : "$");

		    }

		    if (sb.toString().trim().isEmpty()) {
		        continue;
		    }

		    System.out.println(sb);
		}

	}

	public void print() {
		try {
			printJob.print();
		} catch(PrinterException pe) {
			pe.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

}

package util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class HelloWorldPrinter
    implements Printable {

	 public static void main(String args[]) {

		 new HelloWorldPrinter();
	    }
  public int print(Graphics g, PageFormat pf, int page)
      throws PrinterException {

    // We have only one page, and 'page'
    // is zero-based
    if (page > 0) {
         return NO_SUCH_PAGE;
    }

    // User (0,0) is typically outside the
    // imageable area, so we must translate
    // by the X and Y values in the PageFormat
    // to avoid clipping.
    Graphics2D g2d = (Graphics2D)g;
    g2d.translate(pf.getImageableX(), pf.getImageableY());

    // Now we perform our rendering
    g.drawString("Hello world!", 100, 100);

    // tell the caller that this page is part
    // of the printed document
    return PAGE_EXISTS;
  }
}
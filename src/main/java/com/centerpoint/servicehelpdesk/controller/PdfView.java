package com.centerpoint.servicehelpdesk.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.centerpoint.servicehelpdesk.entity.Ticket;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class PdfView extends AbstractPdfView {
	Font ffont = new Font(Font.FontFamily.UNDEFINED, 5, Font.ITALIC);
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		    response.setHeader("Content-Disposition", "attachment; filename=\"TicketListReport.pdf\"");
		    //document.open();
		    Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 20,Font.BOLD | Font.UNDERLINE, BaseColor.BLACK);
		    PdfContentByte cb = writer.getDirectContent();
		    Phrase header = new Phrase("Ticket Report", redFont);
		    
		   //Image image1 = Image.getInstance("");
		    
		    ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
	                header,
	                (document.right() - document.left()) / 2 + document.leftMargin(),
	                document.top() + 10, 0);  
		   /* Paragraph paragraph = new Paragraph("Ticket List Report",redFont);
		    paragraph.setAlignment(Element.ALIGN_CENTER);
		    document.add(paragraph);*/
	        @SuppressWarnings("unchecked")
			List<Ticket> users = (List<Ticket>) model.get("users");
	        Paragraph right = new Paragraph("From : "+model.get("sdate")+"   To: "+model.get("tdate"));
	        right.setAlignment(Element.ALIGN_CENTER);
	        document.add(right);
	        PdfPTable table = new PdfPTable(11);
	        table.setHeaderRows(1);
	        table.setWidthPercentage(105.0f);
	       /* float[] columnWidths = new float[]{50f, 50f, 50f, 50f,50f, 60f, 60f, 50f,50f};
	        table.setWidths(columnWidths);*/
	        table.setSpacingBefore(10);

	        // define font for table header row
	        Font font = FontFactory.getFont(FontFactory.TIMES);
	        font.setColor(BaseColor.WHITE);
	 

	        // define table header cell
	        PdfPCell cell = new PdfPCell();
	        cell.setBackgroundColor(BaseColor.DARK_GRAY);
	        cell.setPadding(5);

	        // write table header
	        cell.setPhrase(new Phrase("TicketId", font));
	        table.addCell(cell);
	        
	        cell.setPhrase(new Phrase("Department", font));
	        table.addCell(cell);
	        
	        cell.setPhrase(new Phrase("ServiceCat.", font));
	        table.addCell(cell);
	        
	        cell.setPhrase(new Phrase("Stages", font));
	        table.addCell(cell);
	        
	        cell.setPhrase(new Phrase("Status", font));
	        table.addCell(cell);
	        
	        cell.setPhrase(new Phrase("Subject", font));
	        table.addCell(cell);
	        
	        cell.setPhrase(new Phrase("OverDueDays", font));
	        table.addCell(cell);

	        cell.setPhrase(new Phrase("TicketDate", font));
	        table.addCell(cell);
	        cell.setPhrase(new Phrase("RaisedBy", font));
	        table.addCell(cell);
	        
	        cell.setPhrase(new Phrase("RequestDept.", font));
	        table.addCell(cell);
	        
	        cell.setPhrase(new Phrase("AssignedTo", font));
	        table.addCell(cell);
	        
	       for(Ticket user : users){
	    	   try {
	    		   table.addCell(user.getTicketId().toString());
	    		   table.addCell(user.getDepartmentEntity().getDepartmentShortName());
	    		   table.addCell(user.getServiceCategories().getServiceCategorieName());
	    		   table.addCell(user.getStages().getStagesName());
	    		   table.addCell(user.getStatus().getStatusName());
	    		   table.addCell(user.getSubject());
	    		   table.addCell(user.getOverDueDays().toString());
	    		   SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd");
	    		   String ticketDate="";
	   			   try {
	   				ticketDate = sdf.format(user.getTicketDate());
	   			    }catch(Exception e) {
	   			   }
	    		   table.addCell(ticketDate);
	    		   table.addCell(user.getUser().getUserName()+" "+user.getUser().getUsersurName());
	    		   table.addCell(user.getUserDepartment().getDepartmentShortName());
	    		   table.addCell(user.getUser1().getUserName()+" "+user.getUser1().getUsersurName());
	    	  
	    	   }catch(NullPointerException e) {
	    		   table.addCell("");
	    	   }
	            
	            
	        }

	        document.add(table);
		
	}
}

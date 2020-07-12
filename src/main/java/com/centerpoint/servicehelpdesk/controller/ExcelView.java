package com.centerpoint.servicehelpdesk.controller;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.centerpoint.servicehelpdesk.entity.Ticket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class ExcelView extends AbstractXlsView{

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"TicketListReport.xls\"");

        @SuppressWarnings("unchecked")
        List<Ticket> users = (List<Ticket>) model.get("users");
        String from = (String) model.get("sdate");
        String to = (String) model.get("tdate");

        // create excel xls sheet
        Sheet sheet = workbook.createSheet("Ticket Excel");
        sheet.setDefaultColumnWidth(20);

        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);
        // create header row
        Row header = sheet.createRow(0);
        header.createCell(5).setCellValue("Ticket Report");
        header.getCell(5).setCellStyle(style);
        
        Row header2 = sheet.createRow(1);
        header2.createCell(3).setCellValue("From : ");
        header2.getCell(3).setCellStyle(style);
        header2.createCell(4).setCellValue(from);
        header2.getCell(4).setCellStyle(style);
        header2.createCell(5).setCellValue("");
        header2.getCell(5).setCellStyle(style);
        header2.createCell(6).setCellValue("To : ");
        header2.getCell(6).setCellStyle(style);
        header2.createCell(7).setCellValue(to);
        header2.getCell(7).setCellStyle(style);
        
        
        
        
        Row header1 = sheet.createRow(2);
        header1.createCell(0).setCellValue("TicketId");
        header1.getCell(0).setCellStyle(style);
        header1.createCell(1).setCellValue("Department");
        header1.getCell(1).setCellStyle(style);
        header1.createCell(2).setCellValue("ServiceCat.");
        header1.getCell(2).setCellStyle(style);
        header1.createCell(3).setCellValue("Stages");
        header1.getCell(3).setCellStyle(style);
        header1.createCell(4).setCellValue("Status");
        header1.getCell(4).setCellStyle(style);
        header1.createCell(5).setCellValue("OverDueDays");
        header1.getCell(5).setCellStyle(style);
        header1.createCell(6).setCellValue("Subject");
        header1.getCell(6).setCellStyle(style);
        header1.createCell(7).setCellValue("TicketDate");
        header1.getCell(7).setCellStyle(style);
        header1.createCell(8).setCellValue("RaisedBy");
        header1.getCell(8).setCellStyle(style);
        header1.createCell(9).setCellValue("RequestDept.");
        header1.getCell(9).setCellStyle(style);
        header1.createCell(10).setCellValue("AssignedTo");
        header1.getCell(10).setCellStyle(style);
        
        int rowCount = 3;
        for(Ticket user : users){
        	
            Row userRow =  sheet.createRow(rowCount++);
            try {
            userRow.createCell(0).setCellValue(user.getTicketId());
            userRow.createCell(1).setCellValue(user.getDepartmentEntity().getDepartmentShortName());
            userRow.createCell(2).setCellValue(user.getServiceCategories().getServiceCategorieName());
            userRow.createCell(3).setCellValue(user.getStages().getStagesName());
            userRow.createCell(4).setCellValue(user.getStatus().getStatusName());
            userRow.createCell(5).setCellValue(user.getOverDueDays());
           
            userRow.createCell(6).setCellValue(user.getSubject());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
 		    String ticketDate="";
			   try {
				ticketDate = sdf.format(user.getTicketDate());
			    }catch(Exception e) {
			   }
            userRow.createCell(7).setCellValue(ticketDate);
            userRow.createCell(8).setCellValue(user.getUser().getUserName()+" "+user.getUser().getUsersurName());
            userRow.createCell(9).setCellValue(user.getUserDepartment().getDepartmentShortName());
            userRow.createCell(10).setCellValue(user.getUser1().getUserName()+" "+user.getUser1().getUsersurName());

            
        }catch(NullPointerException e) {
        	  userRow.createCell(10, CellType.STRING).setCellValue("");
 	   }
        }

    }

}

package com.centerpoint.servicehelpdesk.schedular;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import com.centerpoint.servicehelpdesk.entity.Status;
import com.centerpoint.servicehelpdesk.entity.Ticket;
import com.centerpoint.servicehelpdesk.repoDao.TicketRepo;

@Configuration
@EnableScheduling
@Transactional
public class HelpSchedular {
	
	@Autowired
    private TicketRepo ticketRepo;
	
	@Scheduled(cron = "0 0/1 * * * ?")
	public void overDueDateTask() throws ParseException {
		Status status = new Status();
		status.setStatusId(1l);
		List<Ticket> ticket = ticketRepo.findAllByStatus(status);
		DateFormat format = new SimpleDateFormat("DD-mm-yyyy HH:mm");
		String format3 = format.format(new Date());
		for (int i = 0; i < ticket.size(); i++) {
			Date ticketDate = ticket.get(i).getTicketDate();
			DateFormat format1 = new SimpleDateFormat("DD-mm-yyyy HH:mm");
			String format2 = format1.format(ticketDate);
			String[] arrOfStr = format2.split(" ", 2);
			String[] arrOfStr1 = format3.split(" ", 2);
			if(arrOfStr1[1].trim().equals(arrOfStr[1].trim())) {
				Integer overDueDays = ticket.get(i).getOverDueDays();
				ticket.get(i).setOverDueDays(overDueDays + 1);
				ticketRepo.save(ticket.get(i));
				}
		}
	}
}

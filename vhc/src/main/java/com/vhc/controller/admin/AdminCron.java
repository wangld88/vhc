package com.vhc.controller.admin;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.vhc.core.model.Event;
import com.vhc.core.model.Eventproduct;
import com.vhc.core.model.Product;
import com.vhc.core.model.Status;


@Controller
public class AdminCron extends AdminBase {

	private Logger logger = LoggerFactory.getLogger(AdminCron.class);

	@Scheduled(cron = "0 0 * * * ?")
	public void checkEvent() {

		try {
			logger.info("Daily process on Event started!");
			Status status = statusService.getByNameAndReftbl("Active", "general");
			Event event = eventService.getCurrentEvent(status);

			if(event != null) {
				List<Eventproduct> eps = eventproductService.getByEventid(event.getEventid());

				for(Eventproduct ep : eps) {
					Product p = ep.getProduct();
					BigDecimal eventprice = ep.getPrice(); //p.getRetail().subtract(p.getRetail().multiply(new BigDecimal(ep.getPercentage())).divide(new BigDecimal(100)));
					p.setEventprice(eventprice);
					p = productService.save(p);
				}
				logger.info("Event products found and updated: {}", eps!=null?eps.size():-1);
			}

			//Process Ended event in today
			Event endEvent = eventService.getEndedEvent(status);

			if(endEvent != null) {
				List<Eventproduct> eps = eventproductService.getByEventid(endEvent.getEventid());

				for(Eventproduct ep : eps) {
					Product p = ep.getProduct();
					p.setEventprice(null);  //BigDecimal.ZERO
					p = productService.save(p);
				}

				status = statusService.getByNameAndReftbl("Inactive", "general");
				endEvent.setStatus(status);
				endEvent = eventService.save(endEvent);
				logger.info("Ended event products found and updated: {}", eps!=null?eps.size():-1);
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("Error found in Event process: ", e.getMessage());
		}
	}
}

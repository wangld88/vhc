package com.vhc.controller.admin;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.vhc.core.model.Event;
import com.vhc.core.model.Eventproduct;
import com.vhc.core.model.Product;
import com.vhc.core.model.Status;
import com.vhc.core.service.EventService;
import com.vhc.core.service.EventproductService;
import com.vhc.core.service.ProductService;
import com.vhc.core.service.StatusService;


@Component
public class AdminCron {

	private Logger logger = LoggerFactory.getLogger(AdminCron.class);

	@Autowired
	protected EventService eventService;

	@Autowired
	protected StatusService statusService;

	@Autowired
	protected ProductService productService;

	@Autowired
	protected EventproductService eventproductService;

	
	@Scheduled(cron = "0 0 * * * ?")
	public void checkEvent() {
		logger.info("checkEvent started!");

		try {
			logger.info("[Cron] Daily process on Event started!");
			Status status = statusService.getByNameAndReftbl("Active", "general");
			
			List<Event> pastEvents = eventService.getPastEvent(status);
			//System.out.println("Events found: "+(pastEvents != null?pastEvents.size():0));
			if(pastEvents != null && !pastEvents.isEmpty()) {
				for(Event e : pastEvents) {
					boolean rst = unsetEvent(e);
					if(rst) {
						logger.info("Event ID ({}) has been reset successfully", e.getEventid());
					} else {
						logger.info("Event ID ({}) failed to reset!", e.getEventid());
					}
				}
			}
			
			Event currentEvent = eventService.getCurrentEvent(status);
			//System.out.println("Events found: "+(currentEvent != null));

			if(currentEvent != null) {
				//System.out.println("Event ID we process : "+currentEvent.getEventid());
				List<Eventproduct> eps = eventproductService.getByEventid(currentEvent.getEventid());

				for(Eventproduct ep : eps) {
					Product p = ep.getProduct();
					BigDecimal eventprice = ep.getPrice(); //p.getRetail().subtract(p.getRetail().multiply(new BigDecimal(ep.getPercentage())).divide(new BigDecimal(100)));
					p.setEventprice(eventprice);
					p = productService.save(p);
				}
				logger.info("Event products found and updated: {}", eps!=null?eps.size():-1);
			} else {
				logger.info("There is no active Event currently");
			}
			//System.out.println("Events found: "+(currentEvent!=null));

			//Process Ended event in today
			Event endEvent = eventService.getEndedEvent(status);

			if(endEvent != null) {
				boolean rst = unsetEvent(endEvent);
//				List<Eventproduct> eps = eventproductService.getByEventid(endEvent.getEventid());
//
//				for(Eventproduct ep : eps) {
//					Product p = ep.getProduct();
//					p.setEventprice(null);  //BigDecimal.ZERO
//					p = productService.save(p);
//				}
//
//				status = statusService.getByNameAndReftbl("Inactive", "general");
//				endEvent.setStatus(status);
//				endEvent = eventService.save(endEvent);
				logger.info("Ended event products found and updated: {}", rst);
			}
			logger.info("End of checkEvent!");
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("Error found in Event process: ", e.getMessage());
		}
	}
	
	private boolean unsetEvent(Event e) {
		try {
			List<Eventproduct> eps = eventproductService.getByEventid(e.getEventid());
			
			for(Eventproduct ep : eps) {
				Product p = ep.getProduct();
				p.setEventprice(null);
				p = productService.save(p);
			}
			
			Status status = statusService.getByNameAndReftbl("Inactive", "general");
			e.setStatus(status);
			e = eventService.save(e);
		
		} catch(Exception ex) {
			ex.printStackTrace();
			logger.error("Error found in unsetEvent: ", ex.getMessage());
			return false;
		}
		return true;
	}
}

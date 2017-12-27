package com.vhc.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.thymeleaf.exceptions.TemplateProcessingException;

import com.vhc.service.AddressService;
import com.vhc.service.BrandService;
import com.vhc.service.CityService;
import com.vhc.service.ColorService;
import com.vhc.service.ImageService;
import com.vhc.service.InventoryService;
import com.vhc.service.ItemService;
import com.vhc.service.TypeService;
import com.vhc.service.ProductService;
import com.vhc.service.RegionService;
import com.vhc.service.ShipmentService;
import com.vhc.service.SizeService;
import com.vhc.service.StatusService;
import com.vhc.service.StoreService;
import com.vhc.service.SupplierService;
import com.vhc.service.UserService;


public class BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired
	protected ProductService productService;
	
	@Autowired
	protected CityService cityService;
	
	@Autowired
	protected AddressService addressService;

	@Autowired
	protected TypeService typeService;

	@Autowired
	protected BrandService brandService;

	@Autowired
	protected SupplierService supplierService;

	@Autowired
	protected ShipmentService shipmentService;
	
	@Autowired
	protected ItemService itemService;
	
	@Autowired
	protected StoreService storeService;
	
	@Autowired
	protected UserService userService;
	
	@Autowired
	protected ColorService colorService;
	
	@Autowired
	protected RegionService regionService;
	
	@Autowired
	protected SizeService sizeService;

	@Autowired
	protected InventoryService inventoryService;
	
	@Autowired
	protected StatusService statusService;
	
	@Autowired
	protected ImageService imageService;
	
	
	protected static final String DATE_FORMAT = "MM/dd/yyyy";
	
	protected static final String ERROR_VIEW = "error/error";
	
	
	@ExceptionHandler(TemplateProcessingException.class)
	public ModelAndView handleTemplateProcessingException(Exception e) {
		
		final ModelAndView model = initModelView(null);
		
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		model.setStatus(HttpStatus.NOT_FOUND);
		model.addObject("errordetails", "No such page found!");
		//model.addObject(RETURN_MESSAGE, new Message(Message.ERROR, "The requested resource could not be found!"));
		logger.info("Error: " + e.getMessage());
		
        return model;
    }
	

	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView handleNoHandlerFoundException(Exception e) {
		
		final ModelAndView model = initModelView("norule");
		model.setStatus(HttpStatus.NOT_FOUND);
		model.addObject("errordetails", "No such page found!");
		//model.addObject(RETURN_MESSAGE, new Message(Message.ERROR, "The requested resource could not be found!"));
		logger.info("Error: " + e.getMessage());
		
        return model;
    }
	

	@ExceptionHandler({ Exception.class })
	public ModelAndView uncaughtExceptionHandling (Exception e, HttpServletResponse httpresponse) {

		logger.info("General Error is caught" + e.getMessage());
		e.printStackTrace();
		//String errorMsg = "";
		String errorDetails = "N/A";
		
		final ModelAndView model = initModelView(ERROR_VIEW);
		
		if (e != null) {
			errorDetails = e.getMessage();
			
			if (e.getCause() != null && e.getCause().getMessage() != null) {
				errorDetails += " - " + e.getCause().getMessage();
			}
			
		}
		
		model.addObject("errordetails", errorDetails);

		return model;
		
	}

	public Calendar parseDate(String date) {
		
		Calendar rtn = Calendar.getInstance();
		
		try {
			
			SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
			
			Date inputDate = formatter.parse(date);
			
			rtn.setTime(inputDate);
			
			logger.info(String.format("Parsed Date: %s", rtn.getTime()));
			
		} catch(Exception e) {
			logger.error(String.format("Error in parseDate: %s", e.getMessage()));;
		}
		
		return rtn;
	}

	private ModelAndView initModelView(String view) {
		ModelAndView model = new ModelAndView(ERROR_VIEW);
		/*if(view != null && !view.isEmpty() && !ERROR_VIEW.equals(view)) {
			model = new ModelAndView(view);
		}
		
		Boolean isAdmin = userService.isAdmin();
		
		model.addObject(USER_INFO, user);
		model.addObject(IS_ADMIN, isAdmin);

		if(isAdmin) {
			List<Type> types = typeService.findAll();

			model.addObject(ADMIN_STAGE, STAGE_HOME);
			model.addObject(TYPES, types);
		}*/
		
		return model;
	}

}

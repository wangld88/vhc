package com.vhc.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.vhc.service.AddressService;
import com.vhc.service.BrandService;
import com.vhc.service.CityService;
import com.vhc.service.ManufactureService;
import com.vhc.service.ProductService;
import com.vhc.service.ShipmentService;
import com.vhc.service.SupplierService;


public class BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired
	protected ProductService productService;
	
	@Autowired
	protected CityService cityService;
	
	@Autowired
	protected AddressService addressService;

	@Autowired
	protected ManufactureService manufactureService;

	@Autowired
	protected BrandService brandService;

	@Autowired
	protected SupplierService supplierService;

	@Autowired
	protected ShipmentService shipmentService;
	
	protected static final String DATE_FORMAT = "yyyy-MM-dd";
	
	protected static final String ERROR_VIEW = "error/error";
	
	
	@ExceptionHandler({ Exception.class })
	public ModelAndView uncaughtExceptionHandling (Exception e, HttpServletResponse httpresponse) {

		String errorMsg = "";
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

package com.vhc.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.vhc.security.PromocodeService;
import com.vhc.service.AccountService;
import com.vhc.service.AddressService;
import com.vhc.service.BrandService;
import com.vhc.service.CategoryService;
import com.vhc.service.CategoryproductService;
import com.vhc.service.CityService;
import com.vhc.service.ColorService;
import com.vhc.service.CountryService;
import com.vhc.service.CreditcardService;
import com.vhc.service.CustomerService;
import com.vhc.service.DebitcardService;
import com.vhc.service.GiftcardService;
import com.vhc.service.ImageService;
import com.vhc.service.InventoryService;
import com.vhc.service.InvoiceService;
import com.vhc.service.ItemService;
import com.vhc.service.LocationService;
import com.vhc.service.OrderService;
import com.vhc.service.OrderitemService;
import com.vhc.service.PageService;
import com.vhc.service.PageimageService;
import com.vhc.service.PaymentService;
import com.vhc.service.PaymentmethodService;
import com.vhc.service.TypeService;
import com.vhc.service.ProductService;
import com.vhc.service.ProducttagService;
import com.vhc.service.ProvinceService;
import com.vhc.service.PurchaseorderService;
import com.vhc.service.RegionService;
import com.vhc.service.RoleService;
import com.vhc.service.ShipmentService;
import com.vhc.service.SizeService;
import com.vhc.service.StaffService;
import com.vhc.service.StatusService;
import com.vhc.service.StoreService;
import com.vhc.service.StyleService;
import com.vhc.service.SupplierService;
import com.vhc.service.TagService;
import com.vhc.service.TransactionService;
import com.vhc.service.UserService;
import com.vhc.service.UserroleService;
import com.vhc.util.Message;
import com.vhc.util.MessageHandler;


public class BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	protected MessageHandler messageHandler;

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

	@Autowired
	protected PurchaseorderService purchaseorderService;

	@Autowired
	protected RoleService roleService;

	@Autowired
	protected UserroleService userroleService;

	@Autowired
	protected BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	protected StaffService staffService;

	@Autowired
	protected CustomerService customerService;

	@Autowired
	protected AccountService accountService;

	@Autowired
	protected OrderService orderService;

	@Autowired
	protected OrderitemService orderitemService;

	@Autowired
	protected InvoiceService invoiceService;

	@Autowired
	protected PaymentService paymentService;

	@Autowired
	protected TransactionService transactionService;

	@Autowired
	protected CountryService countryService;

	@Autowired
	protected ProvinceService provinceService;

	@Autowired
	protected StyleService styleService;

	@Autowired
	protected LocationService locationService;

	@Autowired
	protected CategoryService categoryService;

	@Autowired
	protected CategoryproductService categoryproductService;

	@Autowired
	protected PageService pageService;

	@Autowired
	protected TagService tagService;

	@Autowired
	protected ProducttagService producttagService;

	@Autowired
	protected GiftcardService giftcardService;

	@Autowired
	protected PaymentmethodService paymentmethodService;

	@Autowired
	protected PromocodeService promocodeService;

	@Autowired
	protected CreditcardService creditcardService;

	@Autowired
	protected DebitcardService debitcardService;

	@Autowired
	protected PageimageService pageimageService;


	protected static final String DATE_FORMAT = "MM/dd/yyyy";

	private static final String ALERT_MESSAGE = "errorMessage";


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

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String methodNotSupportExceptionHandling(HttpServletRequest request,
    		HttpServletResponse response,
    		ModelMap model,
    		HttpRequestMethodNotSupportedException e) {

        //final ModelAndView model = initModelView(null);
        logger.error("[x] HttpRequestMethodNotSupportedException: Http Request Method Not Supported!");
        //model.setStatus(HttpStatus.NOT_FOUND);
        model.addAttribute("errordetails", "No such page found!");
        model.addAttribute(ALERT_MESSAGE, new Message(Message.ERROR, "The requested method could not be found!"));

        return "/error/error";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandlerFoundException(ModelMap model, Exception e) {

        //final ModelAndView model = initModelView("graduploadform");
        logger.error("[x] NoHandlerFoundException: No such page found!");
        //model.setStatus(HttpStatus.NOT_FOUND);
        model.addAttribute("errordetails", "No such page found!");
        model.addAttribute(ALERT_MESSAGE, new Message(Message.ERROR, "The requested resource could not be found!"));

        return "/error/error";
    }

	/*@ExceptionHandler(TemplateProcessingException.class)
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

	private ModelAndView initModelView(String view) {
		ModelAndView model = new ModelAndView(ERROR_VIEW);
		if(view != null && !view.isEmpty() && !ERROR_VIEW.equals(view)) {
			model = new ModelAndView(view);
		}

		Boolean isAdmin = userService.isAdmin();

		model.addObject(USER_INFO, user);
		model.addObject(IS_ADMIN, isAdmin);

		if(isAdmin) {
			List<Type> types = typeService.findAll();

			model.addObject(ADMIN_STAGE, STAGE_HOME);
			model.addObject(TYPES, types);
		}

		return model;
	}*/

}

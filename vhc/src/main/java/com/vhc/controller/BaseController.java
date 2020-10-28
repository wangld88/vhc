package com.vhc.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.vhc.core.service.PromocodeService;
import com.vhc.core.service.AccountService;
import com.vhc.core.service.AddressService;
import com.vhc.core.service.BrandService;
import com.vhc.core.service.CategoryService;
import com.vhc.core.service.CategoryproductService;
import com.vhc.core.service.CityService;
import com.vhc.core.service.ColorService;
import com.vhc.core.service.CountryService;
import com.vhc.core.service.CreditcardService;
import com.vhc.core.service.CustomerService;
import com.vhc.core.service.DebitcardService;
import com.vhc.core.service.GiftcardHistoryService;
import com.vhc.core.service.GiftcardService;
import com.vhc.core.service.ImageService;
import com.vhc.core.service.InventoryHistoryService;
import com.vhc.core.service.InventoryService;
import com.vhc.core.service.InvoiceService;
import com.vhc.core.service.ItemService;
import com.vhc.core.service.LocationService;
import com.vhc.core.service.OrderService;
import com.vhc.core.service.OrderitemService;
import com.vhc.core.service.PageService;
import com.vhc.core.service.PageimageService;
import com.vhc.core.service.PaymentService;
import com.vhc.core.service.PaymentmethodService;
import com.vhc.core.service.TypeService;
import com.vhc.core.service.ProductService;
import com.vhc.core.service.ProducttagService;
import com.vhc.core.service.ProvinceService;
import com.vhc.core.service.PurchaseorderService;
import com.vhc.core.service.RegionService;
import com.vhc.core.service.RoleService;
import com.vhc.core.service.ShipmentService;
import com.vhc.core.service.ShippingmethodService;
import com.vhc.core.service.SizeService;
import com.vhc.core.service.StaffService;
import com.vhc.core.service.StatusService;
import com.vhc.core.service.StoreService;
import com.vhc.core.service.StyleService;
import com.vhc.core.service.SupplierService;
import com.vhc.core.service.TagService;
import com.vhc.core.service.TemplateService;
import com.vhc.core.service.TransactionService;
import com.vhc.core.service.UserService;
import com.vhc.core.service.UserroleService;
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
	protected InventoryHistoryService inventoryHistoryService;

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

	@Autowired
	protected GiftcardHistoryService giftcardHistoryService;

	@Autowired
	protected ShippingmethodService shippingmethodService;

	@Autowired
	protected TemplateService templateService;

	protected static final String DATE_FORMAT = "MM/dd/yyyy";

	//private static final String ALERT_MESSAGE = "errorMessage";


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

	/*@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
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

        logger.error("[x] NoHandlerFoundException: No such page found!");

        model.addAttribute("errordetails", "Bad Request!");
        model.addAttribute(ALERT_MESSAGE, new Message(Message.ERROR, "The requested resource could not be found!"));

        return "/error/error";
    }


	@ExceptionHandler({ Exception.class })
	public String uncaughtExceptionHandling (ModelMap model, Exception e) {

		logger.info("General Error is caught" + e.getMessage());
		e.printStackTrace();

		String errorDetails = "N/A";

		if (e != null) {
			errorDetails = e.getMessage();

			if (e.getCause() != null && e.getCause().getMessage() != null) {
				errorDetails += " - " + e.getCause().getMessage();
			}

		}
        model.addAttribute("errordetails", errorDetails);
        model.addAttribute(ALERT_MESSAGE, new Message(Message.ERROR, "Unexpected Error!"));

        return "/error/error";
	}*/

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

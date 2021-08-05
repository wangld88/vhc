package com.vhc.controller.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vhc.core.model.City;
import com.vhc.core.model.Customer;
import com.vhc.core.model.Status;
import com.vhc.core.model.Store;
import com.vhc.core.model.Type;
import com.vhc.core.model.Giftcard;
import com.vhc.core.model.GiftcardHistory;
import com.vhc.core.model.Promocode;
import com.vhc.core.model.User;
import com.vhc.core.util.Message;
import com.vhc.service.util.ExcelProcessor;


@Controller
@RequestMapping({"/admin"})
public class AdminGiftcard extends AdminBase {

	private final Logger logger = LoggerFactory.getLogger(AdminGiftcard.class);

	@RequestMapping(method={RequestMethod.GET}, value={"/giftcards"})
	public String dspGiftcards(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/giftcards";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("[ADM Ship] The login user {} is not a super admin.", loginUser!=null?loginUser.getUserid():"Not Existing");
			return "redirect:/admin/logout";
		}

		List<Giftcard> mfs = giftcardService.getAll();
		model.addAttribute("giftcards", mfs);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");
		model.addAttribute("submenu", "giftcards");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/giftcard"})
	public String dspGiftcard(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/giftcard";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			return "redirect:/admin/logout";
		}

		List<City> cities = cityService.getAll();
		List<Status> statuss = statusService.getByReftbl("giftcards");

		List<Store> stores = storeService.getAll();
		List<Customer> customers = customerService.getAll();
		
		logger.info("[VHC Adm] Gift card customers: ", customers.size());
		
		model.addAttribute("stores", stores);
		model.addAttribute("statuss", statuss);
		model.addAttribute("cities", cities);
		model.addAttribute("customers", customers);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/giftcardupload"})
	public String dspGiftcardUpload(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/giftcardupload";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			return "redirect:/admin/logout";
		}

		List<City> cities = cityService.getAll();
		List<Status> statuss = statusService.getByReftbl("giftcards");

		model.addAttribute("statuss", statuss);
		model.addAttribute("cities", cities);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");

		return rtn;
	}

	@RequestMapping(method={RequestMethod.POST}, value={"/giftcardupload"})
	public String doGiftcardUpload(@RequestParam("file") MultipartFile file, ModelMap model, HttpSession httpSession) {
		String rtn = "admin/giftcardupload";
		User loginUser = getSuperAdmin();
		Message msg = new Message();

		logger.info("Gift card upload is called!!!");
		if(loginUser == null) {
			return "redirect:/admin/logout";
		}

		try {
		    InputStream in = file.getInputStream();
		    File currDir = new File("/disk2/upload"); //new File("C:\\temp\\"); //
		    String path = currDir.getAbsolutePath();
		    String fileLocation = path + "/" + file.getOriginalFilename(); //path.substring(0, path.length() - 1) + file.getOriginalFilename();
		    logger.info("File Location: "+fileLocation+", path: "+path);

		    FileOutputStream f = new FileOutputStream(fileLocation);
		    int ch = 0;
		    while ((ch = in.read()) != -1) {
		        f.write(ch);
		    }
		    f.flush();
		    f.close();

		    List<Store> stores = storeService.getAll();

		    ExcelProcessor processor = new ExcelProcessor();

			List<Giftcard> cards = processor.read(fileLocation, loginUser, stores);

			logger.warn("Size of the upload cards: {}", cards.size());
			for(Giftcard card : cards) {

				Giftcard old = giftcardService.getByCode(card.getCode());

				if(old != null) {
					logger.warn("Card ({}) already in system",card.getCode());
					//card.setGiftcardid(old.getGiftcardid());
					continue;
				}

				card = giftcardService.save(card);

				//History
				GiftcardHistory gfHistory = new GiftcardHistory();
				Type type = typeService.getByNameReftbl("Create", "giftcards");
				gfHistory.setType(type);
				gfHistory.setAmount(card.getAmount());
				gfHistory.setGiftcard(card);
				gfHistory.setOperatedate(Calendar.getInstance());
				gfHistory.setOperatedby(loginUser);
				giftcardHistoryService.save(gfHistory);
			}
			msg.setStatus(Message.SUCCESS);
			msg.setMessage("The gift card batch process completed successfully!");
		} catch(Exception e) {
			msg.setStatus(Message.ERROR);
			msg.setMessage("The gift card batch process failed, found error: " + e.getMessage());
			e.printStackTrace();
		}

		List<City> cities = cityService.getAll();
		List<Status> statuss = statusService.getByReftbl("giftcards");

		model.addAttribute("message", msg);
		model.addAttribute("statuss", statuss);
		model.addAttribute("cities", cities);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");

		return rtn;
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/giftcard/{giftcardid}"})
	public String updateGiftcard(ModelMap model, @PathVariable("giftcardid") Long giftcardid, HttpSession httpSession) {
		String rtn = "admin/giftcard";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			return "redirect:/admin/logout";
		}

		long mfid = giftcardid.longValue();
		Giftcard giftcard = giftcardService.getById(mfid);

		List<City> cities = cityService.getAll();
		List<Status> statuss = statusService.getByReftbl("giftcards");

		List<Store> stores = storeService.getAll();

		List<Customer> customers = customerService.getAll();
		logger.info("[VHC Adm] Gift card customers: ", customers.size());
		
		model.addAttribute("customers", customers);
		model.addAttribute("stores", stores);
		model.addAttribute("statuss", statuss);
		model.addAttribute("giftcard", giftcard);
		model.addAttribute("cities", cities);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");
		model.addAttribute("submenu", "giftcards");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/giftcard/log/{giftcardid}"})
	public String dspGiftcardHistory(ModelMap model, @PathVariable("giftcardid") Long giftcardid, HttpSession httpSession) {
		String rtn = "admin/giftcardhistory";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			return "redirect:/admin/logout";
		}

		long mfid = giftcardid.longValue();
		Giftcard giftcard = giftcardService.getById(mfid);

		List<GiftcardHistory> histories = giftcardHistoryService.getByGiftcard(giftcard);

		model.addAttribute("histories", histories);
		model.addAttribute("giftcard", giftcard);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");
		model.addAttribute("submenu", "giftcards");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/giftcard"})
	public String doGiftcard(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "giftcards";

		logger.info("doGiftcard is call!!!!!");

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			return "redirect:/admin/logout";
		}

		String cardid = requestParams.get("giftcardid");
		String code = requestParams.get("code");
		//String pin = requestParams.get("pin");
		String amount = requestParams.get("amount");
		String balance = requestParams.get("balance");
		String statusid = requestParams.get("statusid");
		String storeid = requestParams.get("storeid");
		String comments = requestParams.get("comments");

		Giftcard old = giftcardService.getByCode(code);
		Giftcard giftcard = null;
		Type type = typeService.getByNameReftbl("Create", "giftcards");
		BigDecimal hisAmount = BigDecimal.ZERO;

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");
		model.addAttribute("submenu", "giftcards");

		if(old != null && old.getGiftcardid() > 0 && (cardid == null || cardid.isEmpty())) {
			List<City> cities = cityService.getAll();
			List<Status> statuss = statusService.getByReftbl("giftcards");

			List<Store> stores = storeService.getAll();

			giftcard = new Giftcard();
			giftcard.setCode(code);

			if(amount != null && !amount.isEmpty()) {
				giftcard.setAmount(new BigDecimal(amount));
				if(cardid == null || cardid.isEmpty()) {
					giftcard.setBalance(new BigDecimal(amount));
				}
			}

			if(balance != null && !balance.isEmpty()) {
				giftcard.setBalance(new BigDecimal(balance));
			}

			if(storeid != null && !storeid.isEmpty()) {
				giftcard.setStore(storeService.getById(Integer.parseInt(storeid)));
			}

			Status status = statusService.getById(Long.parseLong(statusid));
			giftcard.setStatus(status);

			Message msg = new Message();
			msg.setStatus(Message.ERROR);
			msg.setMessage("The gift card code exists already, please check.");

			model.addAttribute("message", msg);
			model.addAttribute("giftcard", giftcard);
			model.addAttribute("stores", stores);
			model.addAttribute("statuss", statuss);
			model.addAttribute("cities", cities);

			return "admin/giftcard";
		} else if((old != null && old.getGiftcardid() > 0) && (cardid != null && !cardid.isEmpty())) {
			giftcard = old;
			type = typeService.getByNameReftbl("Rewrite", "giftcards");
			hisAmount = new BigDecimal(balance).subtract(giftcard.getBalance());
		} else {
			giftcard = new Giftcard();
			hisAmount = new BigDecimal(amount);
		}

		Status status = statusService.getById(Long.parseLong(statusid));
		giftcard.setCode(code);
		giftcard.setComments(comments);
		giftcard.setStatus(status);
		giftcard.setLoaddate(Calendar.getInstance());
		giftcard.setLoadedby(loginUser);

		if(amount != null && !amount.isEmpty()) {
			giftcard.setAmount(new BigDecimal(amount));
			if(cardid == null || cardid.isEmpty()) {
				giftcard.setBalance(new BigDecimal(amount));
			}
		}

		if(balance != null && !balance.isEmpty()) {
			giftcard.setBalance(new BigDecimal(balance));
		}

		if(storeid != null && !storeid.isEmpty()) {
			giftcard.setStore(storeService.getById(Integer.parseInt(storeid)));
		}

		giftcardService.save(giftcard);

		//History
		GiftcardHistory gfHistory = new GiftcardHistory();

		gfHistory.setType(type);

		if(cardid == null || cardid.isEmpty()) {
			gfHistory.setAmount(giftcard.getAmount());
		} else {
			gfHistory.setAmount(hisAmount);
		}

		gfHistory.setBalance(giftcard.getBalance());
		gfHistory.setGiftcard(giftcard);
		gfHistory.setOperatedate(Calendar.getInstance());
		gfHistory.setOperatedby(loginUser);

		giftcardHistoryService.save(gfHistory);

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/giftcard/{giftcardid}"})
	public String deleteGiftcard(@PathVariable("giftcardid") Long giftcardid, ModelMap model, HttpSession httpSession) {
		String rtn = "/admin/giftcards";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			return "redirect:/admin/logout";
		}

		long cardid = giftcardid.longValue();
		giftcardService.delete(cardid);

		List<Store> stores = storeService.getAll();

		model.addAttribute("stores", stores);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");
		model.addAttribute("submenu", "giftcards");

		return "redirect:" + rtn;
	}


	@RequestMapping(method=RequestMethod.GET, value="/giftcards/search")
	public String dspGiftcardSearch(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "/admin/giftcards";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			return "redirect:/admin/logout";
		}

		String name = requestParams.get("name");

		System.out.println("search param: "+name);

		List<Giftcard> cards = giftcardService.getByCodeContain(name);

		model.addAttribute("giftcards", cards);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");
		model.addAttribute("submenu", "giftcards");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/promocodes"})
	public String dspPromocodes(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/promocodes";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			return "redirect:/admin/logout";
		}

		List<Promocode> mfs = promocodeService.getAll();
		model.addAttribute("promocodes", mfs);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");
		model.addAttribute("submenu", "promocodes");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/promocode"})
	public String dspPromocode(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/promocode";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			return "redirect:/admin/logout";
		}

		/*List<City> cities = cityService.getAll();

		model.addAttribute("cities", cities);*/
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");
		model.addAttribute("submenu", "promocodes");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/promocode/{promocodeid}"})
	public String updatePromocode(ModelMap model, @PathVariable("promocodeid") Long promocodeid, HttpSession httpSession) {
		String rtn = "admin/promocode";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			return "redirect:/admin/logout";
		}

		long mfid = promocodeid.longValue();
		Promocode promocode = promocodeService.getById(mfid);

		/*List<City> cities = cityService.getAll();
		model.addAttribute("cities", cities);*/
		model.addAttribute("promocode", promocode);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");
		model.addAttribute("submenu", "promocodes");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/promocode"})
	public String doPromocode(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession)
			throws Exception {
		String rtn = "promocodes";

		logger.info("doPromocode is call!!!!!");

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			return "redirect:/admin/logout";
		}

		String promocodeid = requestParams.get("promocodeid");
		String code = requestParams.get("code");
		String description = requestParams.get("description");
		String percentage = requestParams.get("percentage");
		String startdate = requestParams.get("startdate");
		String enddate = requestParams.get("enddate");

		Promocode promocode = new Promocode();
		if(promocodeid != null && !promocodeid.isEmpty()) {
			promocode.setPromocodeid(Long.parseLong(promocodeid));
		}

		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(formatter.parse(startdate));
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(formatter.parse(enddate));

		promocode.setCode(code);
		promocode.setDescription(description);
		promocode.setStartdate(startCal);
		promocode.setEnddate(endCal);
		promocode.setUpdatedby(loginUser);

		if(description != null && !description.isEmpty()) {
			promocode.setDescription(description);
		}
		if(percentage != null && !percentage.isEmpty()) {
			promocode.setPercentage(Long.parseLong(percentage));
		}

		promocodeService.save(promocode);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");
		model.addAttribute("submenu", "promocodes");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/promocode/{promocodeid}"})
	public String deletePromocode(@PathVariable("promocodeid") Long promocodeid, ModelMap model, HttpSession httpSession) {
		String rtn = "/admin/promocodes";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		long codeid = promocodeid.longValue();
		promocodeService.delete(codeid);

		List<Store> stores = storeService.getAll();

		model.addAttribute("stores", stores);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");
		model.addAttribute("submenu", "giftcards");

		return "redirect:" + rtn;
	}

}

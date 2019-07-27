package com.vhc.controller.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vhc.controller.BaseController;
import com.vhc.core.model.Address;
import com.vhc.core.model.City;
import com.vhc.core.model.Staff;
import com.vhc.core.model.Status;
import com.vhc.core.model.Giftcard;
import com.vhc.core.model.Promocode;
import com.vhc.core.model.User;
import com.vhc.security.LoginUser;
import com.vhc.service.util.ExcelProcessor;


@Controller
@RequestMapping({"/admin"})
public class AdminGiftcard extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(AdminGiftcard.class);

	@RequestMapping(method={RequestMethod.GET}, value={"/giftcards"})
	public String dspGiftcards(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/giftcards";

		User loginUser = getPrincipal();

		if(loginUser == null) {
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

		User loginUser = getPrincipal();

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


	@RequestMapping(method={RequestMethod.GET}, value={"/giftcardupload"})
	public String dspGiftcardUpload(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/giftcardupload";

		User loginUser = getPrincipal();

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
		User loginUser = getPrincipal();
System.out.println("No Gift card called");
		if(loginUser == null) {
			return "redirect:/admin/logout";
		}

		try {
		    InputStream in = file.getInputStream();
		    File currDir = new File("C:\\temp\\");
		    String path = currDir.getAbsolutePath();
		    String fileLocation = path + "\\" + file.getOriginalFilename(); //path.substring(0, path.length() - 1) + file.getOriginalFilename();
System.out.println("File Location: "+fileLocation+", path: "+path);
		    FileOutputStream f = new FileOutputStream(fileLocation);
		    int ch = 0;
		    while ((ch = in.read()) != -1) {
		        f.write(ch);
		    }
		    f.flush();
		    f.close();

		    ExcelProcessor processor = new ExcelProcessor();
			List<Giftcard> cards = processor.read(fileLocation, loginUser);

			for(Giftcard card : cards) {
				giftcardService.save(card);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		List<City> cities = cityService.getAll();
		List<Status> statuss = statusService.getByReftbl("giftcards");

		model.addAttribute("statuss", statuss);
		model.addAttribute("cities", cities);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");

		return rtn;
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/giftcard/{giftcardid}"})
	public String updateGiftcard(ModelMap model, @PathVariable("giftcardid") Long giftcardid, HttpSession httpSession) {
		String rtn = "admin/giftcard";

		User loginUser = getPrincipal();

		if(loginUser == null) {
			return "redirect:/admin/logout";
		}

		long mfid = giftcardid.longValue();
		Giftcard giftcard = giftcardService.getById(mfid);

		List<City> cities = cityService.getAll();
		List<Status> statuss = statusService.getByReftbl("giftcards");

		model.addAttribute("statuss", statuss);
		model.addAttribute("giftcard", giftcard);
		model.addAttribute("cities", cities);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");
		model.addAttribute("submenu", "giftcards");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/giftcard"})
	public String doGiftcard(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "giftcards";

		logger.info("doGiftcard is call!!!!!");

		User loginUser = getPrincipal();

		if(loginUser == null) {
			return "redirect:/admin/logout";
		}

		//Address ads = new Address();

		String cardid = requestParams.get("giftcardid");
		String code = requestParams.get("code");
		//String pin = requestParams.get("pin");
		String amount = requestParams.get("amount");
		String balance = requestParams.get("balance");
		String statusid = requestParams.get("statusid");

		Giftcard giftcard = new Giftcard();
		if(cardid != null && !cardid.isEmpty()) {
			giftcard.setGiftcardid(Long.parseLong(cardid));
		}

		Status status = statusService.getById(Long.parseLong(statusid));
		giftcard.setCode(code);
		//giftcard.setPin(pin);
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

		giftcardService.save(giftcard);

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");
		model.addAttribute("submenu", "giftcards");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/promocodes"})
	public String dspPromocodes(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/promocodes";

		User loginUser = getPrincipal();

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

		User loginUser = getPrincipal();

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

		User loginUser = getPrincipal();

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
	public String doPromocode(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) throws Exception {
		String rtn = "promocodes";

		logger.info("doPromocode is call!!!!!");

		User loginUser = getPrincipal();

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


	private User getPrincipal(){
    	User user = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean isSuper = false;
        for (GrantedAuthority grantedAuthority : authorities) {
        	isSuper = grantedAuthority.getAuthority().equals("SUPERADMIN");
        }

        if (principal instanceof LoginUser) {
        	LoginUser auth = (LoginUser)principal;
        	if(isSuper) {
        		user = auth.getUser();
        	}
        } else {
            user = userService.getByUsername("");
        }
        return user;
    }


}

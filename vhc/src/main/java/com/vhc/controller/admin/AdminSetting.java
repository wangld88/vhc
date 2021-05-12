package com.vhc.controller.admin;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.hibernate.engine.jdbc.LobCreator;
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
import com.vhc.core.model.Color;
import com.vhc.core.model.Country;
import com.vhc.core.model.Location;
import com.vhc.core.model.Page;
import com.vhc.core.model.Pageimage;
import com.vhc.core.model.Province;
import com.vhc.core.model.Region;
import com.vhc.core.model.Shippingmethod;
import com.vhc.core.model.Size;
import com.vhc.core.model.Store;
import com.vhc.core.model.Style;
import com.vhc.core.model.Type;
import com.vhc.core.model.User;
import com.vhc.util.ImageProcessor;


/**
 *
 * @author K & J Consulting
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminSetting extends AdminBase {

	private final Logger logger = LoggerFactory.getLogger(AdminSetting.class);


	@RequestMapping(method={RequestMethod.GET}, value={"/page"})
	public String dspHomepage(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/page";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		Page page = pageService.getById(1);
		logger.info("dspHomepage");

		model.addAttribute("page", page);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "pages");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/page"})
	public String saveHomepage(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "admin/page";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		logger.info("dspHomepage");
		String pageid = requestParams.get("pageid");

		Page page = new Page();

		if(pageid != null && !pageid.isEmpty()) {
			page = pageService.getById(Long.parseLong(pageid));
		}

		page.setName(requestParams.get("name"));
		page.setTitle(requestParams.get("title"));
		String h = requestParams.get("imgheight");

		if(h != null && !h.isEmpty()) {
			page.setImgheight(Long.parseLong(h));
		}
		String w = requestParams.get("imgwidth");
		if(w != null && !w.isEmpty()) {
			page.setImgwidth(Long.parseLong(w));
		}

		String link = requestParams.get("link");
		if(link != null && !link.isEmpty()) {
			page.setLink(link);
		}

		page = pageService.save(page);

		model.addAttribute("page", page);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "pages");

		return rtn;
	}

	@RequestMapping(value = "/page/uploadImage", method = RequestMethod.POST)
	public String uploadImage(@RequestParam("picture") MultipartFile picture,
			@RequestParam("pageid") Long pageid,
			@RequestParam("name") String name,
			@RequestParam("description") String description,
			@RequestParam("seqnum") Long seqnum,
			ModelMap model, HttpSession httpSession)
			throws IllegalStateException, IOException {

		logger.debug("Entering SettingController uploadImage");

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		Pageimage image = new Pageimage();

		String rtn = "/admin/page"; // /" + pageid;

		// Image processing
		Blob blob = null;

		Page page = pageService.getById(pageid);


		if (!picture.isEmpty()) {
			int x1 = (int) Double.parseDouble("0"); //loginUser.getX()
			int y1 = (int) Double.parseDouble("0"); //loginUser.getY()
			int w1 = (int) Double.parseDouble("240"); //loginUser.getW()
			int h1 = (int) Double.parseDouble("240"); //loginUser.getH()
			int width = (int) Double.parseDouble("240");  //loginUser.getImgWidth()
			int height = (int) Double.parseDouble("240");  //loginUser.getImgHeight()

			ImageProcessor processor = new ImageProcessor();
			processor.setSize(width, height);
			InputStream in = processor.process(picture, x1, y1, w1, h1,	(int) page.getImgwidth(), (int) page.getImgheight());
			LobCreator lc = imageService.getLobCreator();
			blob = lc.createBlob(in, picture.getSize());
		}
		// End of Image processing

		if(name != null && !name.trim().isEmpty()) {
			image.setName(name);
		}
		if(description != null && !description.trim().isEmpty()) {
			image.setDescription(description);
		}
		if(seqnum == null || seqnum == 0) {
			seqnum = Long.sum(page.getPageimages().size(), 1);
		}
		image.setSeqnum(seqnum);
		image.setPage(page);
		image.setImage(blob);
		pageimageService.save(image);

		logger.debug("Exiting SettingController.uploadImage");

		return "redirect:" + rtn;
	}


	@RequestMapping(value = "/page/removeImage", method = RequestMethod.POST)
	public String removeImage(@RequestParam("pageimageid") Long pageimageid,
			@RequestParam("pageid") Long pageid,
			ModelMap model, HttpSession httpSession) {

		String rtn = "/admin/page"; // /" + pageid;
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		Pageimage image = new Pageimage();
		image.setPageimageid(pageimageid);
		pageimageService.delete(image);

		Page page = pageService.getById(pageid);

		model.addAttribute("page", page);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "pages");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/types"})
	public String dspTypes(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/types";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		List<Type> types = typeService.getAll();

		model.addAttribute("types", types);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "types");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/types/search"})
	public String searchTypes(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "admin/types";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String name = "%" + requestParams.get("name") + "%";
		List<Type> types = typeService.getByName(name);

		model.addAttribute("types", types);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "types");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/type"})
	public String dspType(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/type";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "types");
		model.addAttribute("loginUser", loginUser);

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/type/{typeid}"})
	public String updateType(ModelMap model, @PathVariable("typeid") Long typeid, HttpSession httpSession) {
		String rtn = "admin/type";

		long mfid = typeid.longValue();
		Type type = typeService.getById(mfid);

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		model.addAttribute("type", type);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "types");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/type"})
	public String doType(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "types";

		logger.info("doType is call!!!!!");
		String name = requestParams.get("name");

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		Type type = new Type();
		type.setName(name);
		typeService.save(type);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "types");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/size"})
	public String dspSize(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/size";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		List<Type> types = typeService.getByReftbl("sizes");
		List<Region> regions = regionService.getAll();

		model.addAttribute("types", types);
		model.addAttribute("regions", regions);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "sizes");
		model.addAttribute("loginUser", loginUser);

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/size/{sizeid}"})
	public String updateSize(ModelMap model, @PathVariable("sizeid") Long sizeid, HttpSession httpSession) {
		String rtn = "admin/size";

		Size size = sizeService.getById(sizeid.longValue());

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		List<Type> types = typeService.getByReftbl("sizes");
		List<Region> regions = regionService.getAll();

		model.addAttribute("types", types);
		model.addAttribute("regions", regions);
		model.addAttribute("size", size);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "sizes");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/sizes"})
	public String dspSizes(ModelMap model, HttpSession httpSession) {

		logger.info("dspSizes is called");
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String rtn = "admin/sizes";

		List<Size> sizes = sizeService.getAll();

		model.addAttribute("sizes", sizes);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("subMenu", "sizes");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/size"})
	public String doSize(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "sizes";

		logger.info("doType is call!!!!!");
		String sizenum = requestParams.get("sizenum");
		String sizeid = requestParams.get("sizeid");
		String typeid = requestParams.get("typeid");
		String regionid = requestParams.get("regionid");

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		Size size = null;

		if(sizeid != null && !sizeid.isEmpty()) {
			size = sizeService.getById(Long.parseLong(sizeid));
		} else {
			size = new Size();
		}

		if(typeid != null && !typeid.isEmpty()) {
			Type type = typeService.getById(Long.parseLong(typeid));
			size.setType(type);
		}

		if(regionid != null && !regionid.isEmpty()) {
			Region region = regionService.getById(Long.parseLong(regionid));
			size.setRegion(region);
		}

		size.setSizenum(sizenum);

		sizeService.save(size);

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "sizes");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/countries"})
	public String dspCountries(ModelMap model, HttpSession httpSession) {

		logger.info("dspCountries is called");
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String rtn = "admin/countries";

		List<Country> countries = countryService.getAll();

		model.addAttribute("countries", countries);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "countries");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/countries/search"})
	public String searchCountries(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {

		logger.info("dspCountries is called");
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String rtn = "admin/countries";
		String name = "%" + requestParams.get("name") + "%";
		List<Country> countries = countryService.getByName(name);

		model.addAttribute("countries", countries);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "countries");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/country"})
	public String dspCountry(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/country";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "countries");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/country/{countryid}"})
	public String updateCountry(ModelMap model, @PathVariable("countryid") Long countryid, HttpSession httpSession) {
		String rtn = "admin/country";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		long mfid = countryid.longValue();
		Country country = countryService.getById(mfid);

		model.addAttribute("country", country);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "countries");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/country"})
	public String doCountry(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "countries";

		logger.info("doCountry is call!!!!!");

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String name = requestParams.get("name");
		String code = requestParams.get("code");
		String countryid = requestParams.get("countryid");

		Country country = new Country();

		if(countryid != null && !countryid.isEmpty()) {
			country.setCountryid(Long.parseLong(countryid));
		}

		country.setName(name);
		country.setCode(code);

		country = countryService.save(country);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "countries");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/provinces"})
	public String dspProvinces(ModelMap model, HttpSession httpSession) {

		logger.info("dspProvinces is called");
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String rtn = "admin/provinces";

		List<Province> provinces = provinceService.getAll();

		model.addAttribute("provinces", provinces);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "provinces");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/provinces/search"})
	public String searchProvinces(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {

		logger.info("dspProvinces is called");
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String rtn = "admin/provinces";

		String name = "%" + requestParams.get("name") + "%";
		List<Province> provinces = provinceService.getByName(name);

		model.addAttribute("provinces", provinces);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "provinces");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/province"})
	public String dspProvince(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/province";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		List<Country> countries = countryService.getAll();

		model.addAttribute("countries", countries);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "provinces");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/province/{provinceid}"})
	public String updateProvince(ModelMap model, @PathVariable("provinceid") Long provinceid, HttpSession httpSession) {
		String rtn = "admin/province";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		long mfid = provinceid.longValue();
		Province province = provinceService.getById(mfid);
		List<Country> countries = countryService.getAll();

		model.addAttribute("countries", countries);
		model.addAttribute("province", province);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "provinces");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/province"})
	public String doProvince(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "provinces";

		logger.info("doProvince is call!!!!!");

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String name = requestParams.get("name");
		String code = requestParams.get("code");
		String countryid = requestParams.get("countryid");
		String provinceid = requestParams.get("provinceid");

		Country country = countryService.getById(Long.parseLong(countryid));

		Province province = new Province();

		if(countryid != null && !countryid.isEmpty()) {
			province.setProvinceid(Long.parseLong(provinceid));
		}

		province.setName(name);
		province.setCode(code);
		province.setCountry(country);

		province = provinceService.save(province);

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "provinces");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/cities"})
	public String dspCities(ModelMap model, HttpSession httpSession) {

		logger.info("dspCities is called");
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String rtn = "admin/cities";

		List<City> cities = cityService.getAll();

		model.addAttribute("cities", cities);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "cities");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/cities/search"})
	public String searchCities(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {

		logger.info("dspCities is called");
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String rtn = "admin/cities";
		String name = "%" + requestParams.get("name") + "%";
		List<City> cities = cityService.getByName(name);

		model.addAttribute("cities", cities);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "cities");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/city"})
	public String dspCity(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/city";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		List<Province> provinces = provinceService.getAll();

		model.addAttribute("provinces", provinces);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "cities");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/city/{cityid}"})
	public String updateCity(ModelMap model, @PathVariable("cityid") Long cityid, HttpSession httpSession) {
		String rtn = "admin/city";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		long mfid = cityid.longValue();
		City city = cityService.getById(mfid);
		List<Province> provinces = provinceService.getAll();

		model.addAttribute("city", city);
		model.addAttribute("provinces", provinces);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "cities");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/city"})
	public String doCity(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "cities";

		logger.info("doCountry is call!!!!!");

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String name = requestParams.get("name");
		String cityid = requestParams.get("cityid");
		String provinceid = requestParams.get("provinceid");

		City city = new City();

		if(cityid != null && !cityid.isEmpty()) {
			city.setCityid(Long.parseLong(cityid));
		}

		Province province = provinceService.getById(Long.parseLong(provinceid));

		city.setName(name);
		city.setProvince(province);

		city = cityService.save(city);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "cities");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/colors"})
	public String dspColors(ModelMap model, HttpSession httpSession) {

		logger.info("dspColors is called");
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String rtn = "admin/colors";
		List<Color> colors = colorService.getAll();

		logger.info("dspColors is called: "+colors.size());

		model.addAttribute("colors", colors);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "colors");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/colors/search"})
	public String searchColors(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {

		logger.info("dspColors is called");
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String rtn = "admin/colors";
		String name = "%" + requestParams.get("name") + "%";
		List<Color> colors = colorService.getByName(name);

		logger.info("dspColors is called: "+colors.size());

		model.addAttribute("colors", colors);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "colors");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/color"})
	public String dspColor(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/color";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "colors");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/color/{colorid}"})
	public String updateColor(ModelMap model, @PathVariable("colorid") Long colorid, HttpSession httpSession) {
		String rtn = "admin/color";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		long mfid = colorid.longValue();
		Color color = colorService.getById(mfid);

		model.addAttribute("color", color);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "colors");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/color"})
	public String doColor(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "colors";

		logger.info("doColor is call!!!!!");

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String name = requestParams.get("name");
		String colorid = requestParams.get("colorid");

		Color color = new Color();

		if(colorid != null && !colorid.isEmpty()) {
			color.setColorid(Long.parseLong(colorid));
		}

		color.setName(name);

		color = colorService.save(color);

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "colors");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/styles"})
	public String dspStyles(ModelMap model, HttpSession httpSession) {

		logger.info("dspStyles is called");
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String rtn = "admin/styles";
		List<Style> styles = styleService.getAll();

		model.addAttribute("styles", styles);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "styles");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/styles/search"})
	public String searchStyles(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {

		logger.info("dspStyles is called");
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String rtn = "admin/styles";
		String name = "%" + requestParams.get("name") + "%";
		List<Style> styles = styleService.getByName(name);

		model.addAttribute("styles", styles);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "styles");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/style"})
	public String dspStyle(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/style";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "styles");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/style/{styleid}"})
	public String updateStyle(ModelMap model, @PathVariable("styleid") Long styleid, HttpSession httpSession) {
		String rtn = "admin/style";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		long mfid = styleid.longValue();
		Style style = styleService.getById(mfid);

		model.addAttribute("style", style);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "styles");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/style"})
	public String doStyle(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "styles";

		logger.info("doColor is call!!!!!");

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String name = requestParams.get("name");
		String styleid = requestParams.get("styleid");
		String seqnum = requestParams.get("seqnum");

		Style style = new Style();

		if(styleid != null && !styleid.isEmpty()) {
			style.setStyleid(Long.parseLong(styleid));
		}

		style.setName(name);

		if(seqnum != null && !seqnum.isEmpty()) {
			style.setSeqnum(seqnum);
		} else if (style.getSeqnum() != null) {
			style.setSeqnum(null);
		}

		style = styleService.save(style);

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "styles");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/locations"})
	public String dspLocations(ModelMap model, HttpSession httpSession) {

		logger.info("dspStyles is called");
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String rtn = "admin/locations";

		List<Location> locations = locationService.getAll();

		model.addAttribute("locations", locations);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "locations");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/locations/search"})
	public String searchLocations(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {

		logger.info("dspStyles is called");
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String rtn = "admin/locations";

		String name = "%" + requestParams.get("name") + "%";
		List<Location> locations = locationService.getByName(name);

		model.addAttribute("locations", locations);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "locations");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/location"})
	public String dspLocation(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/location";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		Store store = staffService.getByUser(loginUser).getStore();

		List<Store> stores = new ArrayList<>();//storeService.getAll();

		stores.add(store);

		model.addAttribute("stores", stores);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "locations");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/location/{locationid}"})
	public String updateLocation(ModelMap model, @PathVariable("locationid") Long locationid, HttpSession httpSession) {
		String rtn = "admin/location";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		long mfid = locationid.longValue();
		Location location = locationService.getById(mfid);

		//Store store = staffService.getByUser(loginUser).getStore();

		//List<Store> stores = new ArrayList<>();
		List<Store> stores = storeService.getAll();
		//stores.add(store);

		model.addAttribute("stores", stores);
		model.addAttribute("location", location);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "locations");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/location"})
	public String doLocation(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "locations";

		logger.info("doLocation is call!!!!!");

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String name = requestParams.get("name");
		String locationid = requestParams.get("locationid");
		String storeid = requestParams.get("storeid");

		Location location = new Location();

		if(locationid != null && !locationid.isEmpty()) {
			location.setLocationid(Long.parseLong(locationid));
		}

		if(storeid != null && !storeid.isEmpty()) {
			Store store = storeService.getById(Long.parseLong(storeid));
			location.setStore(store);
		}

		location.setName(name);

		location = locationService.save(location);

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "locations");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/shippingmethods"})
	public String dspShippingMethods(ModelMap model, HttpSession httpSession) {

		logger.info("dspShippingMethods is called");
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String rtn = "admin/shippingmethods";
		List<Shippingmethod> methods = shippingmethodService.getAll();

		model.addAttribute("methods", methods);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "shippingmethods");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/shippingmethods/search"})
	public String searchShippingMethods(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {

		logger.info("searchShippingMethod is called");
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String rtn = "admin/shippingmethods";
		String name = "%" + requestParams.get("name") + "%";
		List<Shippingmethod> methods = shippingmethodService.getByName(name);

		model.addAttribute("methods", methods);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "shippingmethods");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/shippingmethod"})
	public String dspShippingMethod(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/shippingmethod";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "shippingmethods");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/shippingmethod/{methodid}"})
	public String updateShippingMethod(ModelMap model, @PathVariable("methodid") Long methodid, HttpSession httpSession) {
		String rtn = "admin/shippingmethod";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		long mfid = methodid.longValue();
		Shippingmethod method = shippingmethodService.getById(mfid);

		model.addAttribute("method", method);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "shippingmethods");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/shippingmethod"})
	public String doShippingMethod(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "shippingmethods";

		logger.info("doShippingMethod is call!!!!!");

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String name = requestParams.get("name");
		String methodid = requestParams.get("shipmethodid");
		String description = requestParams.get("description");
		String cost = requestParams.get("cost");

		Shippingmethod method = new Shippingmethod();

		if(methodid != null && !methodid.isEmpty()) {
			method.setShipmethodid(Long.parseLong(methodid));
		}

		method.setName(name);
		method.setDescription(description);

		if(cost != null && !cost.isEmpty()) {
			method.setCost(new BigDecimal(cost));
		} else if (method.getCost() != null) {
			method.setCost(null);;
		}

		method = shippingmethodService.save(method);

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "shippingmethods");

		return "redirect:" + rtn;
	}

}

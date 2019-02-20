package com.vhc.controller.admin;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.hibernate.engine.jdbc.LobCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vhc.model.City;
import com.vhc.model.Color;
import com.vhc.model.Country;
import com.vhc.model.Image;
import com.vhc.model.Location;
import com.vhc.model.Page;
import com.vhc.model.Pageimage;
import com.vhc.model.Product;
import com.vhc.model.Province;
import com.vhc.model.Size;
import com.vhc.model.Store;
import com.vhc.model.Style;
import com.vhc.model.Type;
import com.vhc.model.User;
import com.vhc.security.LoginUser;
import com.vhc.util.ImageProcessor;


@Controller
@RequestMapping("/admin")
public class AdminSetting extends AdminBase {

	private final Logger logger = LoggerFactory.getLogger(AdminSetting.class);


	@RequestMapping(method={RequestMethod.GET}, value={"/page"})
	public String dspHomepage(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/page";

		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		User loginUser = getLoginUser(principal);

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

		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		User loginUser = getLoginUser(principal);

		logger.info("dspHomepage");
		String pageid = requestParams.get("pageid");

		Page page = new Page();

		if(pageid != null || !pageid.isEmpty()) {
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
		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		User loginUser = getLoginUser(principal);

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

		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		User loginUser = getLoginUser(principal);

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

		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		User loginUser = getLoginUser(principal);

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

		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		User loginUser = getLoginUser(principal);

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

		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		User loginUser = getLoginUser(principal);

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

		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		User loginUser = getLoginUser(principal);

		Type type = new Type();
		type.setName(name);
		typeService.save(type);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "types");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/sizes"})
	public String dspSizes(ModelMap model, HttpSession httpSession) {

		logger.info("dspSizes is called");
		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		User loginUser = getLoginUser(principal);

		String rtn = "admin/sizes";

		List<Size> sizes = sizeService.getAll();

		model.addAttribute("sizes", sizes);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("subMenu", "sizes");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/countries"})
	public String dspCountries(ModelMap model, HttpSession httpSession) {

		logger.info("dspCountries is called");
		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		String rtn = "admin/countries";
		User loginUser = getLoginUser(principal);
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
		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		String rtn = "admin/countries";
		User loginUser = getLoginUser(principal);
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

		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:admin/logout";
		}

		User loginUser = getLoginUser(principal);

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "countries");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/country/{countryid}"})
	public String updateCountry(ModelMap model, @PathVariable("countryid") Long countryid, HttpSession httpSession) {
		String rtn = "admin/country";

		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		User loginUser = getLoginUser(principal);

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

		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		User loginUser = getLoginUser(principal);

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
		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		String rtn = "admin/provinces";
		User loginUser = getLoginUser(principal);
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
		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		String rtn = "admin/provinces";
		User loginUser = getLoginUser(principal);
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

		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		User loginUser = getLoginUser(principal);

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

		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		User loginUser = getLoginUser(principal);

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

		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		User loginUser = getLoginUser(principal);

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
		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		String rtn = "admin/cities";
		User loginUser = getLoginUser(principal);
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
		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		String rtn = "admin/cities";
		User loginUser = getLoginUser(principal);
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

		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		User loginUser = getLoginUser(principal);

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "cities");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/city/{cityid}"})
	public String updateCity(ModelMap model, @PathVariable("cityid") Long cityid, HttpSession httpSession) {
		String rtn = "admin/city";

		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);

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

		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		User loginUser = getLoginUser(principal);

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
		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		String rtn = "admin/colors";
		User loginUser = getLoginUser(principal);
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
		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		String rtn = "admin/colors";
		User loginUser = getLoginUser(principal);
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

		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		User loginUser = getLoginUser(principal);

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "colors");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/color/{colorid}"})
	public String updateColor(ModelMap model, @PathVariable("colorid") Long colorid, HttpSession httpSession) {
		String rtn = "admin/color";

		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		User loginUser = getLoginUser(principal);

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

		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		User loginUser = getLoginUser(principal);

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
		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		String rtn = "admin/styles";
		User loginUser = getLoginUser(principal);
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
		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		String rtn = "admin/styles";
		User loginUser = getLoginUser(principal);
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

		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		User loginUser = getLoginUser(principal);

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "styles");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/style/{styleid}"})
	public String updateStyle(ModelMap model, @PathVariable("styleid") Long styleid, HttpSession httpSession) {
		String rtn = "admin/style";

		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		User loginUser = getLoginUser(principal);

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

		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		User loginUser = getLoginUser(principal);

		String name = requestParams.get("name");
		String styleid = requestParams.get("styleid");

		Style style = new Style();

		if(styleid != null && !styleid.isEmpty()) {
			style.setStyleid(Long.parseLong(styleid));
		}

		style.setName(name);

		style = styleService.save(style);

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Settings");
		model.addAttribute("submenu", "styles");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/locations"})
	public String dspLocations(ModelMap model, HttpSession httpSession) {

		logger.info("dspStyles is called");
		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		String rtn = "admin/locations";
		User loginUser = getLoginUser(principal);

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
		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		String rtn = "admin/locations";
		User loginUser = getLoginUser(principal);

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

		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		User loginUser = getLoginUser(principal);

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

		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		User loginUser = getLoginUser(principal);

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

		Object principal = getPrincipal();

		if(!isSuperAdmin(principal)) {
			return "redirect:/admin/logout";
		}

		User loginUser = getLoginUser(principal);

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


	private Object getPrincipal() {

		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	private User getLoginUser(Object principal) {

		User user = null;

        if (principal instanceof LoginUser) {
            user = ((LoginUser)principal).getUser();
        } else {
            user = userService.findByUsername("");
        }

        return user;
    }

	private boolean isSuperAdmin(Object principal) {

		if(principal instanceof LoginUser) {
			return ((LoginUser) principal).getAuthorities().contains(new SimpleGrantedAuthority("SUPERADMIN"));
		} else {
			return false;
		}
	}

}

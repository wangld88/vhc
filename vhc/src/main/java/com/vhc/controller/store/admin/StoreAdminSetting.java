package com.vhc.controller.store.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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

import com.vhc.core.model.Country;
import com.vhc.core.model.Location;
import com.vhc.core.model.Province;
import com.vhc.core.model.Size;
import com.vhc.core.model.Staff;
import com.vhc.core.model.Store;
import com.vhc.core.model.Style;
import com.vhc.core.model.Type;
import com.vhc.controller.store.StoreBase;
import com.vhc.core.model.City;
import com.vhc.core.model.Color;
import com.vhc.core.model.User;
import com.vhc.security.LoginUser;


@Controller
@RequestMapping("/store/admin")
public class StoreAdminSetting extends StoreBase {

	private final Logger logger = LoggerFactory.getLogger(StoreAdminSetting.class);

	@RequestMapping(method={RequestMethod.GET}, value={"/countries"})
	public String dspCountries(ModelMap model, HttpSession httpSession) {

		logger.info("dspCountries is called");
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		String rtn = "store/admin/countries";
		List<Country> countries = countryService.getAll();

		model.addAttribute("countries", countries);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "countries");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/country"})
	public String dspCountry(ModelMap model, HttpSession httpSession) {
		String rtn = "store/admin/country";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "countries");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/country/{countryid}"})
	public String updateCountry(ModelMap model, @PathVariable("countryid") Long countryid, HttpSession httpSession) {
		String rtn = "store/admin/country";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		long mfid = countryid.longValue();
		Country country = countryService.getById(mfid);

		model.addAttribute("country", country);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "countries");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/country"})
	public String doCountry(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "countries";

		logger.info("doCountry is call!!!!!");

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

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
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "countries");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/provinces"})
	public String dspProvinces(ModelMap model, HttpSession httpSession) {

		logger.info("dspProvinces is called");
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		String rtn = "store/admin/provinces";
		List<Province> provinces = provinceService.getAll();

		model.addAttribute("provinces", provinces);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "provinces");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/province"})
	public String dspProvince(ModelMap model, HttpSession httpSession) {
		String rtn = "store/admin/province";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		List<Country> countries = countryService.getAll();

		model.addAttribute("countries", countries);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "provinces");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/province/{provinceid}"})
	public String updateProvince(ModelMap model, @PathVariable("provinceid") Long provinceid, HttpSession httpSession) {
		String rtn = "store/admin/province";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		long mfid = provinceid.longValue();
		Province province = provinceService.getById(mfid);
		List<Country> countries = countryService.getAll();

		model.addAttribute("countries", countries);
		model.addAttribute("province", province);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "provinces");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/province"})
	public String doProvince(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "provinces";

		logger.info("doProvince is call!!!!!");

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

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
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "provinces");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/cities"})
	public String dspCities(ModelMap model, HttpSession httpSession) {

		logger.info("dspCities is called");
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		String rtn = "store/admin/cities";
		List<City> cities = cityService.getAll();

		model.addAttribute("cities", cities);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "cities");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/city"})
	public String dspCity(ModelMap model, HttpSession httpSession) {
		String rtn = "store/admin/city";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "cities");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/city/{cityid}"})
	public String updateCity(ModelMap model, @PathVariable("cityid") Long cityid, HttpSession httpSession) {
		String rtn = "store/admin/city";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		long mfid = cityid.longValue();
		City city = cityService.getById(mfid);
		List<Province> provinces = provinceService.getAll();

		model.addAttribute("city", city);
		model.addAttribute("provinces", provinces);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "cities");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/city"})
	public String doCity(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "cities";

		logger.info("doCountry is call!!!!!");

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

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
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "cities");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/types"})
	public String dspTypes(ModelMap model, HttpSession httpSession) {

		logger.info("dspTypes is called");
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		String rtn = "store/admin/types";
		List<Type> types = typeService.getAll();

		logger.info("dspTypes is called: "+types.size());

		model.addAttribute("types", types);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "types");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/type"})
	public String dspType(ModelMap model, HttpSession httpSession) {
		String rtn = "store/admin/type";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "types");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/type/{typeid}"})
	public String updateType(ModelMap model, @PathVariable("typeid") Long typeid, HttpSession httpSession) {
		String rtn = "store/admin/type";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		long mfid = typeid.longValue();
		Type type = typeService.getById(mfid);

		model.addAttribute("type", type);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "types");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/type"})
	public String doType(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "types";

		logger.info("doType is call!!!!!");

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		String name = requestParams.get("name");
		String typeid = requestParams.get("typeid");

		Type type = new Type();

		if(typeid != null && !typeid.isEmpty()) {
			type.setTypeid(Long.parseLong(typeid));
		}

		type.setName(name);

		type = typeService.save(type);

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "types");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/colors"})
	public String dspColors(ModelMap model, HttpSession httpSession) {

		logger.info("dspColors is called");
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		String rtn = "store/admin/colors";
		List<Color> colors = colorService.getAll();

		logger.info("dspColors is called: "+colors.size());

		model.addAttribute("colors", colors);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "colors");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/color"})
	public String dspColor(ModelMap model, HttpSession httpSession) {
		String rtn = "store/admin/color";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "colors");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/color/{colorid}"})
	public String updateColor(ModelMap model, @PathVariable("colorid") Long colorid, HttpSession httpSession) {
		String rtn = "store/admin/color";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		long mfid = colorid.longValue();
		Color color = colorService.getById(mfid);

		model.addAttribute("color", color);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "colors");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/color"})
	public String doColor(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "colors";

		logger.info("doColor is call!!!!!");

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		String name = requestParams.get("name");
		String colorid = requestParams.get("colorid");

		Color color = new Color();

		if(colorid != null && !colorid.isEmpty()) {
			color.setColorid(Long.parseLong(colorid));
		}

		color.setName(name);

		color = colorService.save(color);

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "colors");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/sizes"})
	public String dspSizes(ModelMap model, HttpSession httpSession) {

		logger.info("dspSizes is called");
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		String rtn = "store/admin/sizes";
		List<Size> sizes = sizeService.getAll();

		model.addAttribute("sizes", sizes);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "sizes");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/styles"})
	public String dspStyles(ModelMap model, HttpSession httpSession) {

		logger.info("dspStyles is called");
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		String rtn = "store/admin/styles";
		List<Style> styles = styleService.getAll();

		model.addAttribute("styles", styles);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "styles");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/style"})
	public String dspStyle(ModelMap model, HttpSession httpSession) {
		String rtn = "store/admin/style";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "styles");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/style/{styleid}"})
	public String updateStyle(ModelMap model, @PathVariable("styleid") Long styleid, HttpSession httpSession) {
		String rtn = "store/admin/style";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		long mfid = styleid.longValue();
		Style style = styleService.getById(mfid);

		model.addAttribute("style", style);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "styles");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/style"})
	public String doStyle(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "styles";

		logger.info("doColor is call!!!!!");

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

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
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "styles");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/locations"})
	public String dspLocations(ModelMap model, HttpSession httpSession) {

		logger.info("dspStyles is called");
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		String rtn = "store/admin/locations";

		List<Location> locations = locationService.getByStore(store);

		model.addAttribute("locations", locations);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "locations");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/location"})
	public String dspLocation(ModelMap model, HttpSession httpSession) {
		String rtn = "store/admin/location";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		List<Store> stores = new ArrayList<>();//storeService.getAll();

		stores.add(store);

		model.addAttribute("stores", stores);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "locations");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/location/{locationid}"})
	public String updateLocation(ModelMap model, @PathVariable("locationid") Long locationid, HttpSession httpSession) {
		String rtn = "store/admin/location";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);


		long mfid = locationid.longValue();
		Location location = locationService.getById(mfid);

		List<Store> stores = new ArrayList<>();
		//List<Store> stores = storeService.getAll();
		stores.add(store);

		model.addAttribute("stores", stores);
		model.addAttribute("location", location);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "locations");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/location"})
	public String doLocation(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "locations";

		logger.info("doLocation is call!!!!!");

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		String name = requestParams.get("name");
		String locationid = requestParams.get("locationid");
		String storeid = requestParams.get("storeid");

		Location location = new Location();

		if(locationid != null && !locationid.isEmpty()) {
			location.setLocationid(Long.parseLong(locationid));
		}

		if(storeid != null && !storeid.isEmpty()) {
			Store store1 = storeService.getById(Long.parseLong(storeid));
			location.setStore(store1);
		}

		location.setName(name);

		location = locationService.save(location);

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Settings");
		model.addAttribute("subMenu", "locations");

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
            user = userService.getByUsername("");
        }

        return user;
    }

	private boolean isStoreAdmin(Object principal) {

		if(principal instanceof LoginUser) {
			return ((LoginUser) principal).getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
		} else {
			return false;
		}
	}

}

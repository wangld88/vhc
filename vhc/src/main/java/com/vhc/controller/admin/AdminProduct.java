package com.vhc.controller.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vhc.controller.BaseController;
import com.vhc.model.Address;
import com.vhc.model.Brand;
import com.vhc.model.City;
import com.vhc.model.Manufacture;
import com.vhc.model.Product;
import com.vhc.service.AddressService;
import com.vhc.service.BrandService;
import com.vhc.service.CityService;
import com.vhc.service.ManufactureService;
import com.vhc.service.ProductService;

@Controller
@RequestMapping({"/admin"})
public class AdminProduct extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(AdminProduct.class);
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/manufactures"})
	public String dspManufactures(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/manufactures";
		
		List<Manufacture> mfs = manufactureService.getAll();
		model.addAttribute("manufactures", mfs);
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/manufacture"})
	public String dspManufacture(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/manufacture";
		
		List<City> cities = cityService.getAll();
		
		model.addAttribute("cities", cities);
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/manufacture/{manufactureid}"})
	public String updateManufacture(ModelMap model, @PathVariable("manufactureid") Long manufactureid, HttpSession httpSession) {
		String rtn = "admin/manufacture";
		
		long mfid = manufactureid.longValue();
		Manufacture mf = manufactureService.getById(mfid);
		
		List<City> cities = cityService.getAll();
		model.addAttribute("mf", mf);
		model.addAttribute("cities", cities);
		
		return rtn;
	}

	
	@RequestMapping(method={RequestMethod.POST}, value={"/manufacture"})
	public String doManufacture(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "manufactures";
		
		logger.info("doManufacture is call!!!!!");
		
		Address ads = new Address();
		
		String mfid = requestParams.get("manufactureid");
		String street = (String) requestParams.get("street");
		String cityid = requestParams.get("cityid");
		String postalcode = (String) requestParams.get("postalcode");
		String name = (String) requestParams.get("name");
		String contact = (String) requestParams.get("contact");
		String phone = (String) requestParams.get("phone");
		String email = (String) requestParams.get("email");
		String website = (String) requestParams.get("website");
		String comments = (String) requestParams.get("comments");
		
		logger.info("cityid: " + cityid);
		City city = cityService.getById(Long.parseLong(cityid));
		ads.setCity(city);
		ads.setStreet(street);
		ads.setPostalcode(postalcode);
		
		addressService.save(ads);
		
		Manufacture mf = new Manufacture();
		if(mfid != null && !mfid.isEmpty()) {
			mf.setManufactureid(Long.parseLong(mfid));
		}
		mf.setAddress(ads);
		mf.setContact(contact);
		mf.setEmail(email);
		mf.setName(name);
		mf.setPhone(phone);
		mf.setWebsite(website);
		mf.setComments(comments);
		manufactureService.save(mf);
		
		return "redirect: " + rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/brands"})
	public String dspBrands(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/brands";
		
		List<Brand> brands = brandService.getAll();
		model.addAttribute("brands", brands);
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/brand"})
	public String dspBrand(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/brand";
		
		List<Manufacture> mfs = manufactureService.getAll();
		model.addAttribute("manufactures", mfs);
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/brand/{brandid}"})
	public String updateBrand(ModelMap model, @PathVariable("brandid") Long brandid, HttpSession httpSession) {
		String rtn = "admin/brand";
		
		long mfid = brandid.longValue();
		Brand brand = brandService.getById(mfid);
		
		List<Manufacture> mfs = manufactureService.getAll();
		model.addAttribute("manufactures", mfs);
		model.addAttribute("brand", brand);
		
		return rtn;
	}

	
	@RequestMapping(method={RequestMethod.POST}, value={"/brand"})
	public String doBrand(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "brands";
		
		logger.info("doBrand is call!!!!!");
		
		String brandid = requestParams.get("brandid");
		String name = requestParams.get("name");
		String manufactureid = requestParams.get("manufactureid");
		
		Brand brand = new Brand();
		
		if(brandid != null && !brandid.isEmpty()) {
			brand.setBrandid(Long.parseLong(brandid));
		}
		
		brand.setName(name);
		Manufacture mf = manufactureService.getById(Long.parseLong(manufactureid));
		brand.setManufacture(mf);
		brand = brandService.save(brand);
		
		return "redirect: " + rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/products"})
	public String dspProducts(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/products";
		
		List<Product> products = productService.getAll();
		model.addAttribute("products", products);
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/product"})
	public String dspProduct(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/product";
		
		List<Brand> brands = brandService.getAll();
		model.addAttribute("brands", brands);

		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/product/{productid}"})
	public String updateProduct(ModelMap model, @PathVariable("productid") Long productid, HttpSession httpSession) {
		String rtn = "admin/brand";
		
		long prodid = productid.longValue();
		Product product = productService.getById(prodid);
		
		List<Brand> brands = brandService.getAll();
		model.addAttribute("brands", brands);
		model.addAttribute("product", product);
		
		return rtn;
	}

	
	@RequestMapping(method={RequestMethod.POST}, value={"/product"})
	public String doProduct(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "products";
		
		logger.info("doProduct is call!!!!!");
		
		String productid = requestParams.get("productid");
		String name = requestParams.get("name");
		String code = requestParams.get("code");
		String brandid = requestParams.get("brandid");
		String description = requestParams.get("description");
		String comments = requestParams.get("comments");
		
		Brand brand = brandService.getById(Long.parseLong(brandid));
		
		Product product = new Product();
		if(productid != null && !productid.isEmpty()) {
			product.setProductid(Long.parseLong(productid));
		}
		product.setName(name);
		product.setBrand(brand);
		product.setCode(code);
		product.setDescription(description);
		product.setComments(comments);
		
		product = productService.save(product);
		
		return "redirect: " + rtn;
	}
	
	
}

package com.vhc.controller.admin;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Blob;

import javax.servlet.http.HttpSession;

import org.hibernate.engine.jdbc.LobCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vhc.controller.BaseController;
import com.vhc.dto.ImageForm;
import com.vhc.model.Address;
import com.vhc.model.Brand;
import com.vhc.model.City;
import com.vhc.model.Color;
import com.vhc.model.Image;
import com.vhc.model.Type;
import com.vhc.model.User;
import com.vhc.security.LoginUser;
import com.vhc.util.ImageProcessor;
import com.vhc.model.Product;


/**
 * 
 * 
 * @author Jerry
 *
 */
@Controller
@RequestMapping({"/admin"})
public class AdminProduct extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(AdminProduct.class);
	
	private final int img_fixed_width = 180;
	private final int img_fixed_height = 200;
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/types"})
	public String dspTypes(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/types";
		
		List<Type> types = typeService.getAll();
		model.addAttribute("types", types);
		model.addAttribute("loginUser", getPrincipal());
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/type"})
	public String dspType(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/type";
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/type/{typeid}"})
	public String updateType(ModelMap model, @PathVariable("typeid") Long typeid, HttpSession httpSession) {
		String rtn = "admin/type";
		
		long mfid = typeid.longValue();
		Type type = typeService.getById(mfid);
		
		model.addAttribute("type", type);
		model.addAttribute("loginUser", getPrincipal());
		
		return rtn;
	}

	
	@RequestMapping(method={RequestMethod.POST}, value={"/type"})
	public String doType(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "types";
		
		logger.info("doType is call!!!!!");
		String name = requestParams.get("name");
		
		Type type = new Type();
		type.setName(name);
		typeService.save(type);
		model.addAttribute("loginUser", getPrincipal());
		
		return "redirect:" + rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/brands"})
	public String dspBrands(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/brands";
		
		List<Brand> brands = brandService.getAll();
		model.addAttribute("brands", brands);
		model.addAttribute("loginUser", getPrincipal());
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/brand"})
	public String dspBrand(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/brand";
		
		List<City> cities = cityService.getAll();
		
		model.addAttribute("cities", cities);
		model.addAttribute("loginUser", getPrincipal());
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/brand/{brandid}"})
	public String updateBrand(ModelMap model, @PathVariable("brandid") Long brandid, HttpSession httpSession) {
		String rtn = "admin/brand";
		
		long mfid = brandid.longValue();
		Brand brand = brandService.getById(mfid);
		
		List<City> cities = cityService.getAll();
		
		model.addAttribute("cities", cities);
		model.addAttribute("brand", brand);
		model.addAttribute("loginUser", getPrincipal());
		
		return rtn;
	}

	
	@RequestMapping(method={RequestMethod.POST}, value={"/brand"})
	public String doBrand(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "brands";
		
		logger.info("doBrand is call!!!!!");
		
		String street = requestParams.get("street");
		String cityid = requestParams.get("cityid");
		String postalcode = requestParams.get("postalcode");
		String name = requestParams.get("name");
		String description = requestParams.get("description");
		String contact = requestParams.get("contact");
		String phone = requestParams.get("phone");
		String email = requestParams.get("email");
		String website = requestParams.get("website");
		String comments = requestParams.get("comments");
		String brandid = requestParams.get("brandid");
		
		Address ads = new Address();
		
		logger.info("cityid: " + cityid);
		City city = cityService.getById(Long.parseLong(cityid));
		ads.setCity(city);
		ads.setStreet(street);
		ads.setPostalcode(postalcode);
		
		ads = addressService.save(ads);
		
		Brand brand = new Brand();
		
		if(brandid != null && !brandid.isEmpty()) {
			brand.setBrandid(Long.parseLong(brandid));
		}
		
		brand.setName(name);
		brand.setDescription(description);
		brand.setPhone(phone);
		brand.setAddress(ads);
		brand.setContact(contact);
		brand.setEmail(email);
		brand.setWebsite(website);
		brand.setComments(comments);
		
		brand = brandService.save(brand);
		model.addAttribute("loginUser", getPrincipal());
		
		return "redirect:" + rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/products"})
	public String dspProducts(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/products";
		
		List<Product> products = productService.getAll();
		model.addAttribute("products", products);
		model.addAttribute("loginUser", getPrincipal());
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/product"})
	public String dspProduct(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/product";
		
		List<Type> types = typeService.getAll();
		List<Brand> brands = brandService.getAll();
		List<Color> colors = colorService.getAll();
		
		model.addAttribute("types", types);
		model.addAttribute("brands", brands);
		model.addAttribute("colors", colors);
		model.addAttribute("loginUser", getPrincipal());

		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/product/{productid}"})
	public String updateProduct(ModelMap model, @PathVariable("productid") Long productid, HttpSession httpSession) {
		String rtn = "admin/product";
		
		long prodid = productid.longValue();
		Product product = productService.getById(prodid);
		
		List<Type> types = typeService.getAll();
		List<Brand> brands = brandService.getAll();
		List<Color> colors = colorService.getAll();

		List<Image> images = imageService.getByProduct(productid);
		List<ImageForm> imageForms = new ArrayList<>();
		
		images.forEach(image->imageForms.add(new ImageForm(image)));
		
		model.addAttribute("types", types);
		model.addAttribute("brands", brands);
		model.addAttribute("colors", colors);
		model.addAttribute("product", product);
		model.addAttribute("images", imageForms);
		model.addAttribute("loginUser", getPrincipal());
		
		return rtn;
	}

	
	@RequestMapping(method={RequestMethod.POST}, value={"/product"})
	public String doProduct(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "products";
		
		logger.info("doProduct is call!!!!!");
		
		String productid = requestParams.get("productid");
		String name = requestParams.get("name");
		String modelnum = requestParams.get("modelnum");
		String upc = requestParams.get("upc");
		String brandid = requestParams.get("brandid");
		String colorid = requestParams.get("colorid");
		String typeid = requestParams.get("typeid");
		String description = requestParams.get("description");
		String style = requestParams.get("style");
		String material = requestParams.get("material");
		String comments = requestParams.get("comments");
		
		Brand brand = brandService.getById(Long.parseLong(brandid));
		Color color = colorService.getById(Long.parseLong(colorid));
		Type type = typeService.getById(Long.parseLong(typeid));
		
		Product product = new Product();
		if(productid != null && !productid.isEmpty()) {
			product.setProductid(Long.parseLong(productid));
		}
		
		product.setName(name);
		product.setModelnum(modelnum);
		product.setUpc(upc);
		product.setBrand(brand);
		product.setUpc(upc);
		product.setDescription(description);
		product.setColor(color);
		product.setType(type);
		product.setStyle(style);
		product.setMaterial(material);
		product.setComments(comments);
		
		product = productService.save(product);
		model.addAttribute("loginUser", getPrincipal());
		
		return "redirect:" + rtn;
	}
	
	
	//Upload images
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	public String uploadImage(@RequestParam("picture") MultipartFile picture,
			@RequestParam("productid") Long productid,
			@RequestParam("name") String name,
			ModelMap model, HttpSession httpSession)
			throws IllegalStateException, IOException {
		
		logger.debug("Entering ProductController uploadImage");
		model.addAttribute("loginUser", getPrincipal());
		
		Image image = new Image();

		String rtn = "product/" + productid;

		// Image processing
		Blob blob = null;
		
		if (!picture.isEmpty()) {
			int x1 = (int) Double.parseDouble("0"); //loginUser.getX()
			int y1 = (int) Double.parseDouble("0"); //loginUser.getY()
			int w1 = (int) Double.parseDouble("240"); //loginUser.getW()
			int h1 = (int) Double.parseDouble("240"); //loginUser.getH()
			int width = (int) Double.parseDouble("240");  //loginUser.getImgWidth()
			int height = (int) Double.parseDouble("240");  //loginUser.getImgHeight()

			ImageProcessor processor = new ImageProcessor();
			processor.setSize(width, height);
			InputStream in = processor.process(picture, x1, y1, w1, h1,	img_fixed_width, img_fixed_height);
			LobCreator lc = imageService.getLobCreator();
			blob = lc.createBlob(in, picture.getSize());
		}
		// End of Image processing
		
		Product product = productService.getById(productid);
		
		if(name != null && !name.trim().isEmpty()) {
			image.setName(name);
		}
		image.setProduct(product);
		image.setImage(blob);
		imageService.save(image);
		
		logger.debug("Exiting ProductController.uploadImage");
		
		return "redirect:" + rtn;
	}

	
	@RequestMapping(value = "/removeImage", method = RequestMethod.POST)
	public String uploadImage(@RequestParam("imageid") Long imageid,
			@RequestParam("productid") Long productid,
			ModelMap model, HttpSession httpSession) {

		String rtn = "product/" + productid;
		model.addAttribute("loginUser", getPrincipal());
		Image image = new Image();
		image.setImageid(imageid);
		imageService.delete(image);
		
		return "redirect:" + rtn;
	}
	
	private User getPrincipal(){
    	User user = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //System.out.println("Role is : "+((LoginStudent)principal).toString());
        if (principal instanceof LoginUser) {
            user = ((LoginUser)principal).getUser();
        } else {
            user = userService.findByUsername("");
        }
        return user;
    }
	
}

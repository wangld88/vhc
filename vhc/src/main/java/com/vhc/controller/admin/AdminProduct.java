package com.vhc.controller.admin;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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
import com.vhc.dto.BrandForm;
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
import com.vhc.model.Style;


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


	@RequestMapping(method={RequestMethod.GET}, value={"/brands"})
	public String dspBrands(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/brands";

		List<Brand> brands = brandService.getAll();
		model.addAttribute("brands", brands);
		model.addAttribute("loginUser", getPrincipal());
		model.addAttribute("adminmenu", "Products");
		model.addAttribute("submenu", "brands");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/brands/search"})
	public String searchBrands(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "admin/brands";

		String name = "%" + requestParams.get("name") + "%";
		List<Brand> brands = brandService.getByName(name);

		model.addAttribute("brands", brands);
		model.addAttribute("loginUser", getPrincipal());
		model.addAttribute("adminmenu", "Products");
		model.addAttribute("submenu", "brands");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/brand"})
	public String dspBrand(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/brand";

		List<City> cities = cityService.getAll();

		model.addAttribute("cities", cities);
		model.addAttribute("loginUser", getPrincipal());
		model.addAttribute("adminmenu", "Products");
		model.addAttribute("submenu", "brands");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/brand/{brandid}"})
	public String updateBrand(ModelMap model, @PathVariable("brandid") Long brandid, HttpSession httpSession) {
		String rtn = "admin/brand";

		long mfid = brandid.longValue();
		Brand brand = brandService.getById(mfid);

		BrandForm brandForm = new BrandForm(brand);

		List<City> cities = cityService.getAll();

		model.addAttribute("cities", cities);
		model.addAttribute("brand", brandForm);
		model.addAttribute("loginUser", getPrincipal());
		model.addAttribute("adminmenu", "Products");
		model.addAttribute("submenu", "brands");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/brand"})
	public String doBrand(@RequestParam("picture") MultipartFile picture,
		@RequestParam Map<String,String> requestParams,
		ModelMap model,
		HttpSession httpSession) {

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
		logger.info("[AdminProduct] doBrand - cityid: {}", cityid);

		Brand brand = new Brand();

		Image image = new Image();

		// Image processing
		Blob blob = null;

		Address ads = new Address();

		if(brandid != null && !brandid.isEmpty()) {
			brand = brandService.getById(Long.parseLong(brandid));
			ads = brand.getAddress();
		}

		City city = cityService.getById(Long.parseLong(cityid));
		ads.setCity(city);
		ads.setStreet(street);
		ads.setPostalcode(postalcode);

		ads = addressService.save(ads);

		if (!picture.isEmpty()) {
			int x1 = (int) Double.parseDouble("0"); //loginUser.getX()
			int y1 = (int) Double.parseDouble("0"); //loginUser.getY()
			int w1 = (int) Double.parseDouble("188"); //loginUser.getW()
			int h1 = (int) Double.parseDouble("90"); //loginUser.getH()
			int width = (int) Double.parseDouble("188");  //loginUser.getImgWidth()
			int height = (int) Double.parseDouble("60");  //loginUser.getImgHeight()

			ImageProcessor processor = new ImageProcessor();
			processor.setSize(width, height);
			InputStream in = processor.process(picture, x1, y1, w1, h1,	188, 120);  //img_fixed_width, img_fixed_height
			LobCreator lc = imageService.getLobCreator();
			blob = lc.createBlob(in, picture.getSize());
		}
		// End of Image processing


		if(name != null && !name.trim().isEmpty()) {
			image.setName(name);
		}
		brand.setImage(blob);

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
		model.addAttribute("adminmenu", "Products");
		model.addAttribute("submenu", "brands");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/products"})
	public String dspProducts(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/products";

		List<Product> products = productService.getAll();
		model.addAttribute("products", products);
		model.addAttribute("loginUser", getPrincipal());
		model.addAttribute("adminmenu", "Products");
		model.addAttribute("submenu", "products");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/products/search"})
	public String searchProducts(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "admin/products";

		String name = "%" + requestParams.get("name") + "%";
		List<Product> products = productService.getByName(name);

		model.addAttribute("products", products);
		model.addAttribute("loginUser", getPrincipal());
		model.addAttribute("adminmenu", "Products");
		model.addAttribute("submenu", "products");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/product"})
	public String dspProduct(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/product";

		List<Type> types = typeService.getAll();
		List<Brand> brands = brandService.getAll();
		List<Color> colors = colorService.getAll();
		List<Style> styles = styleService.getAll();

		model.addAttribute("styles", styles);

		model.addAttribute("types", types);
		model.addAttribute("brands", brands);
		model.addAttribute("colors", colors);
		model.addAttribute("loginUser", getPrincipal());
		model.addAttribute("adminmenu", "Products");
		model.addAttribute("submenu", "products");

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

		List<Style> styles = styleService.getAll();

		model.addAttribute("styles", styles);
		model.addAttribute("types", types);
		model.addAttribute("brands", brands);
		model.addAttribute("colors", colors);
		model.addAttribute("product", product);
		model.addAttribute("images", imageForms);
		model.addAttribute("loginUser", getPrincipal());
		model.addAttribute("adminmenu", "Products");
		model.addAttribute("submenu", "products");

		return rtn;
	}

	@RequestMapping(method={RequestMethod.POST}, value={"/product/{productid}"})
	public String removeProduct(ModelMap model, @PathVariable("productid") Long productid, HttpSession httpSession) {
		String rtn = "/admin/products";
		logger.info("removeProduct is call!!!!!"+productid);
		long prodid = productid.longValue();
		productService.delete(prodid);
		model.addAttribute("loginUser", getPrincipal());
		model.addAttribute("adminmenu", "Products");
		model.addAttribute("submenu", "products");

		return "redirect:" + rtn;
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
		String styleid = requestParams.get("styleid");
		String material = requestParams.get("material");
		String wholesale = requestParams.get("wholesale");
		String retail = requestParams.get("retail");
		String clinic = requestParams.get("clinic");
		String onsale = requestParams.get("onsale");
		String points = requestParams.get("points");
		String comments = requestParams.get("comments");
		String storefront = requestParams.get("storefront");

		Brand brand = brandService.getById(Long.parseLong(brandid));
		Color color = colorService.getById(Long.parseLong(colorid));
		Type type = typeService.getById(Long.parseLong(typeid));

		Product product = new Product();
		if(productid != null && !productid.isEmpty()) {
			product.setProductid(Long.parseLong(productid));
		}
		if(styleid != null && !styleid.isEmpty()) {
			product.setStyle(styleService.getById(Long.parseLong(styleid)));
		}
		if(wholesale != null && !wholesale.isEmpty()) {
			product.setWholesale(new BigDecimal(wholesale));
		}
		if(retail != null && !retail.isEmpty()) {
			product.setRetail(new BigDecimal(retail));
		}
		if(clinic != null && !clinic.isEmpty()) {
			product.setClinic(new BigDecimal(clinic));
		}
		if(onsale != null && !onsale.isEmpty()) {
			product.setOnsale(new BigDecimal(onsale));
		}
		product.setName(name);
		product.setModelnum(modelnum);
		product.setUpc(upc);
		product.setBrand(brand);
		product.setDescription(description);
		product.setColor(color);
		product.setType(type);
		product.setMaterial(material);
		if(points != null && !points.isEmpty()) {
			product.setPoints(Long.parseLong(points));
		}

		product.setComments(comments);

		if(storefront == null || storefront.isEmpty()) {
			storefront = "0";
		}
		product.setStorefront(storefront);

		product = productService.save(product);
		model.addAttribute("loginUser", getPrincipal());
		model.addAttribute("adminmenu", "Products");
		model.addAttribute("submenu", "products");

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
	public String removeImage(@RequestParam("imageid") Long imageid,
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

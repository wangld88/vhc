package com.vhc.controller.admin;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.sql.Blob;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.engine.jdbc.LobCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import com.vhc.dto.CategoryForm;
import com.vhc.dto.ImageForm;
import com.vhc.core.model.Address;
import com.vhc.core.model.Brand;
import com.vhc.core.model.Category;
import com.vhc.core.model.Categoryproduct;
import com.vhc.core.model.City;
import com.vhc.core.model.Color;
import com.vhc.core.model.Image;
import com.vhc.core.model.Type;
import com.vhc.core.model.User;
import com.vhc.security.LoginUser;
import com.vhc.util.ImageProcessor;
import com.vhc.core.model.Product;
import com.vhc.core.model.Store;
import com.vhc.core.model.Style;


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

		Object principal = getPrincipal();
		User loginUser = getLoginUser(principal);

		if(!isSuperAdmin(principal)) {
			logger.error("The login user {} is not a super admin.", loginUser.getUserid());
			return "redirect:/admin/logout";
		}

		List<Brand> brands = brandService.getAll();

		model.addAttribute("brands", brands);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Products");
		model.addAttribute("submenu", "brands");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/brands/search"})
	public String searchBrands(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "admin/brands";

		Object principal = getPrincipal();
		User loginUser = getLoginUser(principal);

		if(!isSuperAdmin(principal)) {
			logger.error("The login user {} is not a super admin.", loginUser.getUserid());
			return "redirect:/admin/logout";
		}

		String name = "%" + requestParams.get("name") + "%";
		List<Brand> brands = brandService.getByName(name);

		model.addAttribute("brands", brands);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Products");
		model.addAttribute("submenu", "brands");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/brand"})
	public String dspBrand(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/brand";

		Object principal = getPrincipal();
		User loginUser = getLoginUser(principal);

		if(!isSuperAdmin(principal)) {
			logger.error("The login user {} is not a super admin.", loginUser.getUserid());
			return "redirect:/admin/logout";
		}

		List<City> cities = cityService.getAll();

		model.addAttribute("cities", cities);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Products");
		model.addAttribute("submenu", "brands");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/brand/{brandid}"})
	public String updateBrand(ModelMap model, @PathVariable("brandid") Long brandid, HttpSession httpSession) {
		String rtn = "admin/brand";

		Object principal = getPrincipal();
		User loginUser = getLoginUser(principal);

		if(!isSuperAdmin(principal)) {
			logger.error("The login user {} is not a super admin.", loginUser.getUserid());
			return "redirect:/admin/logout";
		}

		long mfid = brandid.longValue();
		Brand brand = brandService.getById(mfid);

		BrandForm brandForm = new BrandForm(brand);

		List<City> cities = cityService.getAll();

		model.addAttribute("cities", cities);
		model.addAttribute("brand", brandForm);
		model.addAttribute("loginUser", loginUser);
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

		Object principal = getPrincipal();
		User loginUser = getLoginUser(principal);

		if(!isSuperAdmin(principal)) {
			logger.error("The login user {} is not a super admin.", loginUser.getUserid());
			return "redirect:/admin/logout";
		}

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
		String display = requestParams.get("display");
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
		brand.setDisplay(display);

		brand = brandService.save(brand);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Products");
		model.addAttribute("submenu", "brands");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/products"})
	public String dspProducts(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/products";

		Object principal = getPrincipal();
		User loginUser = getLoginUser(principal);

		if(!isSuperAdmin(principal)) {
			logger.error("The login user {} is not a super admin.", loginUser.getUserid());
			return "redirect:/admin/logout";
		}

		List<Product> products = productService.getAll();
		model.addAttribute("products", products);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Products");
		model.addAttribute("submenu", "products");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/products/search"})
	public String searchProducts(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "admin/products";

		Object principal = getPrincipal();
		User loginUser = getLoginUser(principal);

		if(!isSuperAdmin(principal)) {
			logger.error("The login user {} is not a super admin.", loginUser.getUserid());
			return "redirect:/admin/logout";
		}

		String name = "%" + requestParams.get("name") + "%";
		List<Product> products = productService.getByName(name);

		model.addAttribute("products", products);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Products");
		model.addAttribute("submenu", "products");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/product"})
	public String dspProduct(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/product";

		Object principal = getPrincipal();
		User loginUser = getLoginUser(principal);

		if(!isSuperAdmin(principal)) {
			logger.error("The login user {} is not a super admin.", loginUser.getUserid());
			return "redirect:/admin/logout";
		}

		List<Type> types = typeService.getAll();
		List<Brand> brands = brandService.getAll();
		List<Color> colors = colorService.getAll();
		List<Style> styles = styleService.getAll();

		model.addAttribute("styles", styles);
		model.addAttribute("types", types);
		model.addAttribute("brands", brands);
		model.addAttribute("colors", colors);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Products");
		model.addAttribute("submenu", "products");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/product/{productid}"})
	public String updateProduct(ModelMap model, @PathVariable("productid") Long productid, HttpSession httpSession) {
		String rtn = "admin/product";

		Object principal = getPrincipal();
		User loginUser = getLoginUser(principal);

		if(!isSuperAdmin(principal)) {
			logger.error("The login user {} is not a super admin.", loginUser.getUserid());
			return "redirect:/admin/logout";
		}

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
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Products");
		model.addAttribute("submenu", "products");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/product/{productid}"})
	public String removeProduct(ModelMap model, @PathVariable("productid") Long productid, HttpSession httpSession) {
		String rtn = "/admin/products";
		logger.info("removeProduct is call!!!!!"+productid);
		Object principal = getPrincipal();
		User loginUser = getLoginUser(principal);

		if(!isSuperAdmin(principal)) {
			logger.error("The login user {} is not a super admin.", loginUser.getUserid());
			return "redirect:/admin/logout";
		}

		long prodid = productid.longValue();
		productService.delete(prodid);

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Products");
		model.addAttribute("submenu", "products");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/product"})
	public String doProduct(@RequestParam Map<String,String> requestParams,
			@RequestParam(name="categoryid", required = false) Long[] categoryids,
			ModelMap model, HttpSession httpSession) {

		String rtn = "products";

		logger.info("doProduct is call!!!!!");

		Object principal = getPrincipal();
		User loginUser = getLoginUser(principal);

		if(!isSuperAdmin(principal)) {
			logger.error("The login user {} is not a super admin.", loginUser.getUserid());
			return "redirect:/admin/logout";
		}

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
		String display = requestParams.get("display");
		String tax = requestParams.get("tax");
		String seqnum = requestParams.get("seqnum");

		Brand brand = brandService.getById(Long.parseLong(brandid));
		Color color = colorService.getById(Long.parseLong(colorid));
		Type type = typeService.getById(Long.parseLong(typeid));

		Product product = new Product();
		List<Category> categories = new ArrayList<>();

		if(productid != null && !productid.isEmpty()) {
			product = productService.getById(Long.parseLong(productid));
			categories = product.getCategories();
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
		} else if (product.getOnsale() != null) {
			product.setOnsale(null);
		}
		if(tax != null && !tax.isEmpty()) {
			product.setTax(Long.parseLong(tax));
		} else if (product.getTax() != null) {
			product.setTax(null);
		}

		if(seqnum != null && !seqnum.isEmpty()) {
			product.setSeqnum(seqnum);
		} else if (product.getSeqnum() != null) {
			product.setSeqnum(null);
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

		if(display == null || display.isEmpty()) {
			display = "0";
		}
		product.setDisplay(display);

		product = productService.save(product);

		if(categoryids != null && categoryids.length != 0) {
			for(Long categoryid : categoryids) {
				//System.out.println("categories: "+categories.size()+", categoryid: "+categoryid);
				Category ctgy = categories.stream().filter(c -> c.getCategoryid() == categoryid.longValue()).findFirst().orElse(null);

				if(ctgy != null) {
					categories.remove(ctgy);
				} else {
					Categoryproduct cp = new Categoryproduct();
					Category category = categoryService.getById(categoryid);
					cp.setCategory(category);
					cp.setProduct(product);
					cp = categoryproductService.save(cp);
				}
			}
		}

		if(categories != null && !categories.isEmpty()) {
			for(Category c : categories) {
				Categoryproduct cp = categoryproductService.getByCategoryidProductid(c.getCategoryid(), product.getProductid());
				categoryproductService.delete(cp);
			}
		}

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Products");
		model.addAttribute("submenu", "products");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/categories"})
	public String dspCategories(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/categories";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);

		if(!isSuperAdmin(principal)) {
			logger.error("The login user {} is not a super admin.", loginUser.getUserid());
			return "redirect:/admin/logout";
		}

		List<Category> categories = categoryService.getAll();
		model.addAttribute("categories", categories);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Products");
		model.addAttribute("subMenu", "categories");

		return rtn;
	}


	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method={RequestMethod.GET}, value={"/category"})
	public String dspCategory(ModelMap model, HttpSession httpSession, HttpServletRequest request) {
		String rtn = "admin/category";

		Object principal = getPrincipal();
		User loginUser = getLoginUser(principal);

		if(!isSuperAdmin(principal)) {
			logger.error("The login user {} is not a super admin.", loginUser.getUserid());
			return "redirect:/admin/logout";
		}

		List<Type> types = typeService.getAll();
		List<Brand> brands = brandService.getAll();
		List<Color> colors = colorService.getAll();
		List<Style> styles = styleService.getAll();

		model.addAttribute("styles", styles);
		model.addAttribute("types", types);
		model.addAttribute("brands", brands);
		model.addAttribute("colors", colors);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Products");
		model.addAttribute("subMenu", "categories");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/category/{categoryid}"})
	public String updateCategory(ModelMap model, @PathVariable("categoryid") Long categoryid, HttpSession httpSession) {
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);

		if(!isSuperAdmin(principal)) {
			logger.error("The login user {} is not a super admin.", loginUser.getUserid());
			return "redirect:/admin/logout";
		}

		String rtn = "admin/category";

		Category category = categoryService.getById(categoryid);

		CategoryForm form = new CategoryForm(category);

		List<Long> cateproductids = category.getProducts().stream().map(product -> product.getProductid()).collect(Collectors.toList());

		logger.info("Category product IDs: {}", cateproductids.size());

		List<Product> products = productService.getAll();

		model.addAttribute("products", products);
		model.addAttribute("category", form);
		model.addAttribute("cateproductids", cateproductids);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Products");
		model.addAttribute("subMenu", "categories");

		return rtn;
	}


	@RequestMapping(method=RequestMethod.POST, value="/category")
	public String doCategory(@RequestParam(name="picture", required=false) MultipartFile picture,
							 @RequestParam(name="name", required=false) String name,
							 @RequestParam(name="title", required=false) String title,
							 @RequestParam("categoryid") Long categoryid,
							 ModelMap model,
							 HttpSession httpSession) {

		String rtn = "categories";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);

		if(!isSuperAdmin(principal)) {
			logger.error("The login user {} is not a super admin.", loginUser.getUserid());
			return "redirect:/admin/logout";
		}

		logger.info("doProduct is call!!!!!");

		Category category = new Category();

		if(categoryid != null && categoryid != 0) {
			category = categoryService.getById(categoryid);
		}

		if(name != null && !name.isEmpty()) {
			category.setName(name);
		}

		if(title != null && !title.isEmpty()) {
			category.setTitle(title);
		}

		if(picture != null && !picture.isEmpty()) {
			logger.info("$$$$$ picture: "+picture );
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
			Blob blob = lc.createBlob(in, picture.getSize());
			category.setImage(blob);
		}


		category = categoryService.save(category);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Products");
		model.addAttribute("subMenu", "categories");

		return "redirect:" + rtn;
	}


	@RequestMapping(value = "/category/removeImage", method = RequestMethod.POST)
	public String removeCateImage(@RequestParam("categoryid") Long categoryid,
			ModelMap model, HttpSession httpSession) {

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);

		if(!isSuperAdmin(principal)) {
			logger.error("The login user {} is not a super admin.", loginUser.getUserid());
			return "redirect:/admin/logout";
		}

		String rtn = "/admin/category/" + categoryid;

		Category category = categoryService.getById(categoryid);
		category.setImage(null);

		categoryService.save(category);
		model.addAttribute("loginUser", loginUser);

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/category/product"})
	public String addCategoryproduct(@RequestParam("categoryid") Long categoryid,
			@RequestParam("productid") List<Long> productids,
			ModelMap model,
			HttpSession httpSession) {

		logger.info("add category product");

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);

		if(!isSuperAdmin(principal)) {
			logger.error("The login user {} is not a super admin.", loginUser.getUserid());
			return "redirect:/admin/logout";
		}

		String rtn = "redirect:/admin/category/" + categoryid;

		/*@RequestParam Map<String,String> requestParams,
		 * for (Map.Entry<String, String> entry : requestParams.entrySet()) {
		    System.out.println(entry.getKey() + " = " + entry.getValue());
		}*/

		logger.info("Product IDs: {}", productids);
		Category category = categoryService.getById(categoryid);
		List<Product> products = category.getProducts();
		List<Long> cateproductids = products.stream().map(product -> product.getProductid()).collect(Collectors.toList());

		for(Long productid: productids) {
			int index = cateproductids.indexOf(productid);
			if(index < 0) {
				Categoryproduct cp = new Categoryproduct();
				cp.setCategory(category);
				Product product = productService.getById(productid);
				cp.setProduct(product);
				cp = categoryproductService.save(cp);
			}
		}

		for(Long productid: cateproductids) {
			int index = productids.indexOf(productid);
			if(index < 0) {
				Categoryproduct cp = categoryproductService.getByCategoryidProductid(categoryid, productid);
				categoryproductService.delete(cp);
			}
		}

		return rtn;
	}


	//Upload images
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	public String uploadImage(@RequestParam("picture") MultipartFile picture,
			@RequestParam("productid") Long productid,
			@RequestParam("name") String name,
			ModelMap model, HttpSession httpSession)
			throws IllegalStateException, IOException {

		logger.debug("Entering ProductController uploadImage");
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);

		if(!isSuperAdmin(principal)) {
			logger.error("The login user {} is not a super admin.", loginUser.getUserid());
			return "redirect:/admin/logout";
		}
		model.addAttribute("loginUser", loginUser);

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
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);

		if(!isSuperAdmin(principal)) {
			logger.error("The login user {} is not a super admin.", loginUser.getUserid());
			return "redirect:/admin/logout";
		}
		model.addAttribute("loginUser", loginUser);
		Image image = new Image();
		image.setImageid(imageid);
		imageService.delete(image);

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

	private boolean isSuperAdmin(Object principal) {

		if(principal instanceof LoginUser) {
			return ((LoginUser) principal).getAuthorities().contains(new SimpleGrantedAuthority("SUPERADMIN"));
		} else {
			return false;
		}
	}
}

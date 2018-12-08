package com.vhc.controller.store.admin;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.google.common.collect.Lists;
import com.vhc.controller.BaseController;
import com.vhc.dto.BrandForm;
import com.vhc.dto.CategoryForm;
import com.vhc.dto.ImageForm;
import com.vhc.dto.ProductForm;
import com.vhc.model.Address;
import com.vhc.model.Brand;
import com.vhc.model.Category;
import com.vhc.model.Categoryproduct;
import com.vhc.model.City;
import com.vhc.model.Color;
import com.vhc.model.Image;
import com.vhc.model.Inventory;
import com.vhc.model.Product;
import com.vhc.model.Style;
import com.vhc.model.Type;
import com.vhc.model.User;
import com.vhc.security.LoginUser;
import com.vhc.util.ImageProcessor;


@Controller
@RequestMapping(value="/store/admin")
public class StoreAdminProduct extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(StoreAdminProduct.class);

	private final int img_fixed_width = 200;
	private final int img_fixed_height = 200;


	@RequestMapping(value="/home")
	public String dspHome(ModelMap model) {
		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);

		List<Inventory> inventories = inventoryService.getByStoreid(1);

		List<ProductForm> products = new ArrayList<>();

		for(Inventory i: inventories) {
			ProductForm prodForm = new ProductForm(i.getItem().getProduct());

			//prodForm.setQuantity(i.getQuantity());
			//prodForm.setInventoryid(i.getInventoryid());
			//prodForm.setItem(i.getItem());

			long productid = i.getItem().getProduct().getProductid();
			List<Image> images = imageService.getByProduct(productid);
			List<ImageForm> imageForms = new ArrayList<>();

			images.forEach(image->imageForms.add(new ImageForm(image)));

			prodForm.setImages(imageForms);

			products.add(prodForm);
		}

		model.addAttribute("menu", "Products");
		model.addAttribute("products", products);
		model.addAttribute("loginUser", loginUser);

		return "store/index";
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/brands"})
	public String dspBrands(ModelMap model, HttpSession httpSession) {
		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		String rtn = "store/admin/brands";

		List<Brand> brands = brandService.getAll();
		model.addAttribute("brands", brands);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Products");
		model.addAttribute("subMenu", "brands");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/brand"})
	public String dspBrand(ModelMap model, HttpSession httpSession) {
		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		String rtn = "store/admin/brand";

		List<City> cities = cityService.getAll();

		model.addAttribute("cities", cities);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Products");
		model.addAttribute("subMenu", "brands");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/brand/{brandid}"})
	public String updateBrand(ModelMap model, @PathVariable("brandid") Long brandid, HttpSession httpSession) {
		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		String rtn = "store/admin/brand";

		long mfid = brandid.longValue();
		Brand brand = brandService.getById(mfid);

		BrandForm bform = new BrandForm(brand);

		List<City> cities = cityService.getAll();

		model.addAttribute("menu", "Products");
		model.addAttribute("subMenu", "brands");
		model.addAttribute("cities", cities);
		model.addAttribute("brand", bform);
		model.addAttribute("loginUser", loginUser);

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/brand"})
	public String doBrand(@RequestParam("picture") MultipartFile picture, @RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "brands";

		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
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
			brand.setImage(blob);
		}

		brand = brandService.save(brand);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Products");
		model.addAttribute("subMenu", "brands");

		return "redirect:" + rtn;
	}


	@RequestMapping(value = "/brand/removeImage", method = RequestMethod.POST)
	public String removeImage(@RequestParam("brandid") Long brandid,
			ModelMap model, HttpSession httpSession) {

		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		String rtn = "/store/admin/brand/" + brandid;

		Brand brand = brandService.getById(brandid);
		brand.setImage(null);

		brandService.save(brand);
		model.addAttribute("loginUser", loginUser);

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/products"})
	public String dspProducts(ModelMap model, HttpSession httpSession) {
		String rtn = "store/admin/products";

		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		List<Product> products = productService.getAll();
		model.addAttribute("products", products);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Products");
		model.addAttribute("subMenu", "products");

		return rtn;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method={RequestMethod.GET}, value={"/product"})
	public String dspProduct(ModelMap model, HttpSession httpSession, HttpServletRequest request) {
		String rtn = "store/admin/product";

		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);

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
		model.addAttribute("subMenu", "products");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/product/{productid}"})
	public String updateProduct(ModelMap model, @PathVariable("productid") Long productid, HttpSession httpSession) {
		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		String rtn = "store/admin/product";

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
		model.addAttribute("menu", "Products");
		model.addAttribute("subMenu", "products");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/product"})
	public String doProduct(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "products";

		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
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
		//String wholesale = requestParams.get("wholesale");
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
		//product.setWholesale(new BigDecimal(wholesale));
		if(points != null && !points.isEmpty()) {
			product.setPoints(Long.parseLong(points));
		}
		product.setComments(comments);
		if(storefront == null || storefront.isEmpty()) {
			storefront = "0";
		}
		product.setStorefront(storefront);

		product = productService.save(product);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Products");
		model.addAttribute("subMenu", "products");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/categories"})
	public String dspCategories(ModelMap model, HttpSession httpSession) {
		String rtn = "store/admin/categories";

		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
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
		String rtn = "store/admin/category";

		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);

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

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		String rtn = "store/admin/category";

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


	@RequestMapping(method={RequestMethod.POST}, value={"/category"})
	public String doCategory(@RequestParam("picture") MultipartFile picture,
							 @RequestParam("name") String name,
							 @RequestParam("title") String title,
							 @RequestParam("categoryid") Long categoryid,
							 ModelMap model,
							 HttpSession httpSession) {

		String rtn = "categories";

		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
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

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		String rtn = "/store/admin/category/" + categoryid;

		Category category = categoryService.getById(categoryid);
		category.setImage(null);

		categoryService.save(category);
		model.addAttribute("loginUser", loginUser);

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/category/product"})
	public String addCategoryproduct(@RequestParam("categoryid") Long categoryid,
			@RequestParam("productid") List<Long> productids, ModelMap model, HttpSession httpSession) {

		logger.info("add category product");
		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		String rtn = "redirect:/store/admin/category/" + categoryid;

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
		//Product product = productService.getByFullname(productname);
		//CategoryForm form = new CategoryForm(category);
		//category = categoryService.getById(categoryid);
		//List<Categoryproduct> cateproducts = categoryproductService.getByCategoryid(categoryid);

		//List<Product> products = category.getProducts();

		//model.addAttribute("products", products);
		//model.addAttribute("category", form);
		//model.addAttribute("loginUser", loginUser);
		//model.addAttribute("menu", "Products");

		return rtn;
	}

	//Upload images
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	public String uploadImage(@RequestParam("picture") MultipartFile picture,
			@RequestParam("productid") Long productid,
			@RequestParam("name") String name,
			ModelMap model, HttpSession httpSession)
			throws IllegalStateException, IOException {

		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);

		logger.debug("Entering ProductController uploadImage");
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
	public String uploadImage(@RequestParam("imageid") Long imageid,
			@RequestParam("productid") Long productid,
			ModelMap model, HttpSession httpSession) {

		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		String rtn = "product/" + productid;

		Image image = new Image();
		image.setImageid(imageid);
		imageService.delete(image);
		model.addAttribute("loginUser", loginUser);

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

	private boolean isStoreAdmin(Object principal) {

		if(principal instanceof LoginUser) {
			return ((LoginUser) principal).getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
		} else {
			return false;
		}
	}

}

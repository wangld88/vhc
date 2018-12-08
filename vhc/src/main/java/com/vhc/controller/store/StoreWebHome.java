package com.vhc.controller.store;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vhc.dto.BrandForm;
import com.vhc.dto.CategoryForm;
import com.vhc.dto.ImageForm;
import com.vhc.dto.ProductForm;
import com.vhc.model.Brand;
import com.vhc.model.Category;
import com.vhc.model.Categoryproduct;
import com.vhc.model.Color;
import com.vhc.model.Giftcard;
import com.vhc.model.Image;
import com.vhc.model.Inventory;
import com.vhc.model.Item;
import com.vhc.model.Page;
import com.vhc.model.Product;
import com.vhc.model.Producttag;
import com.vhc.model.Size;
import com.vhc.model.Style;


@Controller
@RequestMapping("/store")
public class StoreWebHome extends StoreBase {

	private final Logger logger = LoggerFactory.getLogger(StoreWebHome.class);

	@RequestMapping(method={RequestMethod.GET}, value={"/","/home"})
	public String dspHome(ModelMap model, HttpSession httpSession) {
		String rtn = "store/home";

		List<Item> items = itemService.getAll();

		List<ProductForm> prodForms = new ArrayList<>();

		List<Product> products = productService.getByStroefront("1");

		List<Category> categories = categoryService.getAll();

		List<CategoryForm> cforms = new ArrayList<>();

		for(Product i: products) {
			ProductForm prodForm = new ProductForm(i);

			List<Producttag> tags = producttagService.getByProduct(i);

			if(tags != null && !tags.isEmpty()) {
				prodForm.setTags(tags);
			}

			List<Image> images = imageService.getByProduct(i.getProductid());
			List<ImageForm> imageForms = new ArrayList<>();

			images.forEach(image->imageForms.add(new ImageForm(image)));

			prodForm.setImages(imageForms);

			prodForms.add(prodForm);
		}

		//List<Inventory> inventories = inventoryService.getByStoreid(1);
		//List<Inventory> inventories = inventoryService.getAll();
		/*for(Inventory i: inventories) {
			ProductForm prodForm = new ProductForm();

			prodForm.setQuantity(i.getQuantity());
			prodForm.setInventoryid(i.getInventoryid());
			prodForm.setItem(i.getItem());

			long productid = i.getItem().getProduct().getProductid();
			List<Image> images = imageService.getByProduct(productid);
			List<ImageForm> imageForms = new ArrayList<>();

			images.forEach(image->imageForms.add(new ImageForm(image)));

			prodForm.setImages(imageForms);

			products.add(prodForm);
		}*/

		for(Category c: categories) {
			CategoryForm cf = new CategoryForm(c);
			logger.info("Image: " + cf.getImageData());
			cforms.add(cf);
		}

		logger.info("Number of products: " + products.size());

		Page page = pageService.getById(1);

		model.addAttribute("page", page);
		model.addAttribute("cforms", cforms);
		model.addAttribute("productforms", prodForms);
		model.addAttribute("items", items);

		return rtn;
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/aboutus/"})
	public String dspAboutus(ModelMap model) {
		String rtn = "store/aboutus";

		Page page = pageService.getById(1);

		model.addAttribute("page", page);

		return rtn;
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/contact"})
	public String dspContact(ModelMap model) {
		String rtn = "store/contact";

		Page page = pageService.getById(1);

		model.addAttribute("page", page);

		return rtn;
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/brands/"})
	public String dspBrands(ModelMap model, HttpSession httpSession) {
		String rtn = "store/brands";

		List<Brand> brands = brandService.getAll();

		List<BrandForm> brandForms = new ArrayList<>();

		logger.info("Brands size: {}", brands.size());

		for(Brand brand: brands) {
			brandForms.add(new BrandForm(brand));
		}

		logger.info("BrandForms size: {}", brandForms.size());

		Page page = pageService.getById(1);

		model.addAttribute("page", page);
		model.addAttribute("brands", brands);
		model.addAttribute("brandForms", brandForms);

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/brand/{brandid}"})
	public String dspBrand(@PathVariable("brandid") Long brandid, ModelMap model, HttpSession httpSession) {
		String rtn = "store/brand";

		Brand brand = brandService.getById(brandid);

		List<Product> products = productService.getByBrand(brand);

		logger.info("Products size: {}", products.size());

		List<ProductForm> productForms = getProductForms(products);

		/*for(Product i: products) {
			ProductForm prodForm = new ProductForm(i);

			List<Producttag> tags = producttagService.getByProduct(i);

			if(tags != null && !tags.isEmpty()) {
				prodForm.setTags(tags);
			}

			List<Image> images = imageService.getByProduct(i.getProductid());
			List<ImageForm> imageForms = new ArrayList<>();

			images.forEach(image->imageForms.add(new ImageForm(image)));

			prodForm.setImages(imageForms);

			productForms.add(prodForm);
		}*/

		logger.info("productForms size: {}", productForms.size());

		Page page = pageService.getById(1);

		model.addAttribute("page", page);
		model.addAttribute("brand", brand);
		model.addAttribute("productForms", productForms);

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/mens/"})
	public String getMens(ModelMap model, HttpSession httpSession) {
		String rtn = "store/mens";

		//String storefront = "1";

		String name = "mens/";

		Category category = categoryService.getByTitle(name);

		Sort sort = new Sort(new Sort.Order(Direction.ASC, "Name"));
		int pagenum = 0;
		int limit = 20;
		Pageable pageable = new PageRequest(pagenum, limit, sort);

		List<Category> categories = new ArrayList<>();
		categories.add(category);

		List<Product> products = productService.getByCategoryAndPaging(categories, pageable);
		//List<Product> products = category.getProducts(); // new ArrayList<>();

		List<Brand> brands = brandService.getAll();

		List<Style> styles = styleService.getAll();

		logger.info("num of styles: "+styles.size());

		Page page = pageService.getById(1);

		model.addAttribute("page", page);
		model.addAttribute("brands", brands);
		model.addAttribute("styles", styles);
		model.addAttribute("products", products);
		model.addAttribute("productforms", getProductForms(products));

		return rtn;
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/womens/"})
	public String getWomens(ModelMap model, HttpSession httpSession) {
		String rtn = "store/womens";

		//String storefront = "1";

		String name = "womens/";

		Sort sort = new Sort(new Sort.Order(Direction.ASC, "Name"));
		int pagenum = 0;
		int limit = 20;
		Pageable pageable = new PageRequest(pagenum, limit, sort);
		Category category = categoryService.getByTitle(name);
		List<Category> categories = new ArrayList<>();
		categories.add(category);

		List<Product> products = productService.getByCategoryAndPaging(categories, pageable);
		/*List<Product> products = category.getProducts().subList(0, 5);*/

		logger.info("Number of products: "+products.size());

		List<Brand> brands = brandService.getAll();

		List<Style> styles = styleService.getAll();

		logger.info("num of styles: "+styles.size());

		Page page = pageService.getById(1);

		model.addAttribute("page", page);
		model.addAttribute("brands", brands);
		model.addAttribute("styles", styles);
		model.addAttribute("products", products);
		model.addAttribute("productforms", getProductForms(products));

		return rtn;
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/kids/"})
	public String getKids(ModelMap model, HttpSession httpSession) {
		String rtn = "store/kids";

		//String storefront = "1";

		String name = "Kid's";

		Category category = categoryService.getByName(name);

		Sort sort = new Sort(new Sort.Order(Direction.ASC, "Name"));
		int pagenum = 0;
		int limit = 20;
		Pageable pageable = new PageRequest(pagenum, limit, sort);

		List<Category> categories = new ArrayList<>();
		categories.add(category);

		List<Product> products = productService.getByCategoryAndPaging(categories, pageable);
		//List<Product> products = category.getProducts();

		List<Brand> brands = brandService.getAll();

		List<Style> styles = styleService.getAll();

		logger.info("num of styles: "+styles.size());

		Page page = pageService.getById(1);

		model.addAttribute("page", page);
		model.addAttribute("brands", brands);
		model.addAttribute("styles", styles);
		model.addAttribute("products", products);
		model.addAttribute("productforms", getProductForms(products));

		return rtn;
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/accessories"})
	public String getAccessories(ModelMap model, HttpSession httpSession) {
		String rtn = "store/accessories";

		String name = "accessories/";

		Category category = categoryService.getByTitle(name);

		Sort sort = new Sort(new Sort.Order(Direction.ASC, "Name"));
		int pagenum = 0;
		int limit = 20;
		Pageable pageable = new PageRequest(pagenum, limit, sort);

		List<Category> categories = new ArrayList<>();
		categories.add(category);

		List<Product> products = productService.getByCategoryAndPaging(categories, pageable);
		//List<Product> products = category.getProducts();

		List<Brand> brands = brandService.getAll();

		List<Style> styles = styleService.getAll();

		logger.info("num of styles: "+styles.size());

		Page page = pageService.getById(1);

		model.addAttribute("page", page);
		model.addAttribute("brands", brands);
		model.addAttribute("styles", styles);
		model.addAttribute("products", products);
		model.addAttribute("productforms", getProductForms(products));

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/search"})
	public String doSearch(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "store/";

		String site = requestParams.get("site");
		String search = requestParams.get("q");
		String name = "%" + search + "%";

		List<Product> products = new ArrayList<>();

		logger.info("search site: "+site + ", q: "+name);

		if(site.equals("home")) {
			products = productService.getByName(name);
			//products = productService.getByStroefrontAndName("1", name);

			List<Category> categories = categoryService.getAll();

			List<CategoryForm> cforms = new ArrayList<>();
			for(Category c: categories) {
				CategoryForm cf = new CategoryForm(c);
				logger.info("Image: " + cf.getImageData());
				cforms.add(cf);
			}

			model.addAttribute("cforms", cforms);
			site = "search";
		} else {
			String title = site + "/";
			Category category = categoryService.getByTitle(title);

			logger.info("category: " + category.getName());
			List<Category> categories = new ArrayList<>();
			categories.add(category);

			products = productService.getByCategoryAndName(categories, name);

		}

		List<Brand> brands = brandService.getAll();

		List<Style> styles = styleService.getAll();

		Page page = pageService.getById(1);

		model.addAttribute("page", page);
		model.addAttribute("brands", brands);
		model.addAttribute("styles", styles);
		model.addAttribute("search", search);
		model.addAttribute("products", products);
		model.addAttribute("productforms", getProductForms(products));

		return rtn + site;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/filter"})
	public String doFilter( @RequestParam(value="brand", required = false) String[] brandids,
							@RequestParam(value="style", required = false) String[] styleids,
							@RequestParam(value="price", required = false) String[] priceids,
							@RequestParam Map<String,String> requestParams,
							ModelMap model, HttpSession httpSession) {
		String rtn = "store/";

		String site = requestParams.get("site");
		String style = requestParams.get("style");
		String price = requestParams.get("price");
		String search = requestParams.get("search");

		String title = site + "/";
		Category category = categoryService.getByTitle(title);
		List<Category> categories = new ArrayList<>();
		categories.add(category);

		List<Product> products = new ArrayList<>();
		List<Product> brandProd = new ArrayList<>();
		List<Product> styleProd = new ArrayList<>();
		List<Product> priceProd = new ArrayList<>();

		logger.info("brand: "+brandids+", style: "+style+", price: "+price);

		if(search != null && !search.isEmpty()) {
			String name = "%" + search + "%";
			products = productService.getByCategoryAndName(categories, name);
		}

		if(brandids != null && brandids.length > 0) {
			//List<String> result = Arrays.asList(brand.split("\\s*,\\s*"));
			List<Long> list = Stream.of(brandids)
			        .map(Long::parseLong)
			        .collect(Collectors.toList());

			logger.info("Brand IDs:" + list.toString());
			brandProd = productService.getByBrands(categories, list);
			if(products.isEmpty()) {
				products = brandProd;
			} else {
				logger.info("Brand before: "+products.size());
				products.retainAll(brandProd);
				logger.info("Brand after: "+products.size());
			}
		}

		if(styleids != null && styleids.length > 0) {
			List<Long> list = Stream.of(styleids)
			        .map(Long::parseLong)
			        .collect(Collectors.toList());
			styleProd = productService.getByStyles(categories, list);
			if(products.isEmpty()) {
				products = styleProd;
			} else {
				logger.info("Style before: "+products.size());
				products.retainAll(styleProd);
				logger.info("Style after: "+products.size());
				//products = Stream.concat(products.stream(), styleProd.stream()).distinct().collect(Collectors.toList());
			}
		}

		if(priceids != null && priceids.length > 0) {

			logger.info("priceids.length: "+priceids.length);
			priceProd = productService.getByPrices(categories, priceids);

			if(products.isEmpty()) {
				products = priceProd;
			} else {
				logger.info("Price before: "+products.size());
				products.retainAll(priceProd);
				logger.info("Price after: "+products.size());
				//products = Stream.concat(products.stream(), priceProd.stream()).distinct().collect(Collectors.toList());
			}
		}

		if(brandids == null && styleids == null && priceids == null) {
			if(products.isEmpty()) {
				products = category.getProducts();
			}
		}
		//merge the final result
		/*List<String> noDup = Stream.concat(list1.stream(), list2.stream())
                .distinct()
                .collect(Collectors.toList());*/

		List<Brand> brands = brandService.getAll();

		List<Style> styles = styleService.getAll();

		Page page = pageService.getById(1);

		model.addAttribute("page", page);
		model.addAttribute("brands", brands);
		model.addAttribute("styles", styles);
		model.addAttribute("brandids", Arrays.toString(brandids));
		model.addAttribute("styleids", Arrays.toString(styleids));
		model.addAttribute("priceids", Arrays.toString(priceids));
		model.addAttribute("search", search);
		model.addAttribute("products", products);
		model.addAttribute("productforms", getProductForms(products));

		return rtn + site;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/clearance"})
	public String getClearance(ModelMap model) {
		String rtn = "store/clearance";

		Page page = pageService.getById(1);

		model.addAttribute("page", page);

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/shopping/product/{productid}"})
	public String updateInventory(@RequestParam Map<String,String> requestParams, ModelMap model, @PathVariable("productid") Long productid, HttpSession httpSession) {
		/*Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);*/

		String rtn = "store/product";

		//String itemid = requestParams.get("itemid");
		//Item item = itemService.getById(Long.parseLong(itemid));

		Product product = productService.getById(productid);
		//inventoryService.delete(inventoryid);

		ProductForm prodForm = new ProductForm(product);

		//prodForm.setQuantity(inv.getQuantity());
		//prodForm.setInventoryid(inv.getInventoryid());
		//prodForm.setItem(inv.getItem());


		List<Image> images = imageService.getByProduct(product.getProductid());
		List<ImageForm> imageForms = new ArrayList<>();

		images.forEach(image->imageForms.add(new ImageForm(image)));

		prodForm.setImages(imageForms);

		List<Size> sizes = new ArrayList<>();

		List<Inventory> inventories = inventoryService.getByProduct(product);
		long sum = 0;
		for(Inventory i: inventories) {
			sum += i.getQuantity();
			Size size = i.getItem().getSize();
			if(!sizes.contains(size)) {
				sizes.add(size);
			}
		}

		Page page = pageService.getById(1);

		model.addAttribute("page", page);
		model.addAttribute("sum", sum);
		model.addAttribute("inventories", inventories);
		model.addAttribute("sizes", sizes);
		//model.addAttribute("inventory", inv);
		model.addAttribute("product", prodForm);

		return rtn;
	}


	private List<Product> getProductByCategory(String categoryname) {

		Category category = categoryService.getByName(categoryname);

		List<Product> products = new ArrayList<>();

		/*for(Categoryproduct cp: category.getCategoryproducts()) {
			products.add(cp.getProduct());
		}*/

		return products;
	}


	private List<ProductForm> getProductForms(List<Product> products) {
		List<ProductForm> prodForms = new ArrayList<>();

		for(Product i: products) {
			ProductForm prodForm = new ProductForm(i);

			List<Producttag> tags = producttagService.getByProduct(i);

			if(tags != null && !tags.isEmpty()) {
				prodForm.setTags(tags);
			}

			List<Image> images = imageService.getByProduct(i.getProductid());
			List<ImageForm> imageForms = new ArrayList<>();

			images.forEach(image->imageForms.add(new ImageForm(image)));

			prodForm.setImages(imageForms);

			prodForms.add(prodForm);
		}

		return prodForms;
	}
}

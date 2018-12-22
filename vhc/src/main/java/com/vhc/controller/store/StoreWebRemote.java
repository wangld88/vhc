package com.vhc.controller.store;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.vhc.dto.ImageForm;
import com.vhc.dto.ItemForm;
import com.vhc.dto.NewProductForm;
import com.vhc.dto.ShopItem;
import com.vhc.dto.ShoppingCart;
import com.vhc.model.Category;
import com.vhc.model.Giftcard;
import com.vhc.model.Image;
import com.vhc.model.Inventory;
import com.vhc.model.Item;
import com.vhc.model.Product;
import com.vhc.model.Producttag;
import com.vhc.model.Status;
import com.vhc.model.Store;


@RestController
@RequestMapping("/store")
public class StoreWebRemote extends StoreBase {

	Logger logger = LoggerFactory.getLogger(StoreWebRemote.class);

	@RequestMapping(method={RequestMethod.POST}, value={"/giftcard"})
	public BigDecimal checkBalance(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {

		//String rtn = "store/";
		String cardnum = requestParams.get("cardnum");
		logger.info("checkBalance {}", cardnum);

		Giftcard giftcard = giftcardService.getByCode(cardnum);

		model.addAttribute("giftcard", giftcard);

		if(giftcard == null) {
			return BigDecimal.ZERO;
		} else {
			return giftcard.getBalance();
		}

	}


	@RequestMapping(method=RequestMethod.POST, value={"/products"})
	public List<NewProductForm> geHomeProducts(ModelMap model,
			@RequestParam("page") int page,
			@RequestParam("limit") int limit,
			HttpSession httpSession) {

		logger.info("Womens product scrolling is called");

		Sort sort = new Sort(new Sort.Order(Direction.ASC, "Name"));
		Pageable pageable = new PageRequest(page, limit, sort);

		List<Product> products = productService.getByStroefrontAndPaging("1", pageable);
		List<Product> allProducts = productService.getByStroefront("1");

		List<NewProductForm> prodForms = new ArrayList<>();

		int total = allProducts.size()/5;
		if(allProducts.size()%5 > 0) {
			total += 1;
		}

		for(Product p : products) {
			NewProductForm prodForm = new NewProductForm(p);

			List<Producttag> tags = producttagService.getByProduct(p);

			if(tags != null && !tags.isEmpty()) {
				prodForm.setTags(tags);
			}

			List<Image> images = imageService.getByProduct(p.getProductid());
			List<ImageForm> imageForms = new ArrayList<>();

			images.forEach(image->imageForms.add(new ImageForm(image)));

			prodForm.setImages(imageForms);

			prodForm.setTotal(total);

			prodForms.add(prodForm);
		}

		return prodForms;
	}


	@RequestMapping(method=RequestMethod.POST, value={"/womens/products"})
	public List<NewProductForm> geWomenProducts(ModelMap model,
			@RequestParam("page") int page,
			@RequestParam("limit") int limit,
			HttpSession httpSession) {

		logger.info("Womens product scrolling is called");

		Sort sort = new Sort(new Sort.Order(Direction.ASC, "Name"));
		Pageable pageable = new PageRequest(page, limit, sort);

		Category category = categoryService.getByTitle("womens/");

		logger.info("category: " + category.getName());
		List<Category> categories = new ArrayList<>();
		categories.add(category);

		List<Product> products = productService.getByCategoryAndPaging(categories, pageable);
		List<Product> allProducts = category.getProducts();

		logger.info("Product Search Result: " + products.size());
		List<NewProductForm> prodForms = new ArrayList<>();

		int total = allProducts.size()/5;
		if(allProducts.size()%5 > 0) {
			total += 1;
		}

		for(Product p : products) {
			NewProductForm prodForm = new NewProductForm(p);

			List<Producttag> tags = producttagService.getByProduct(p);

			if(tags != null && !tags.isEmpty()) {
				prodForm.setTags(tags);
			}

			List<Image> images = imageService.getByProduct(p.getProductid());
			List<ImageForm> imageForms = new ArrayList<>();

			images.forEach(image->imageForms.add(new ImageForm(image)));

			prodForm.setImages(imageForms);

			prodForm.setTotal(total);

			prodForms.add(prodForm);
		}

		return prodForms;
	}


	@RequestMapping(method=RequestMethod.POST, value={"/mens/products"})
	public List<NewProductForm> getMenProducts(ModelMap model,
			@RequestParam("page") int page,
			@RequestParam("limit") int limit,
			HttpSession httpSession) {

		logger.info("Mens product scrolling is called");

		Sort sort = new Sort(new Sort.Order(Direction.ASC, "Name"));
		Pageable pageable = new PageRequest(page, limit, sort);

		Category category = categoryService.getByTitle("mens/");

		return getNewProducts(category, pageable);
	}


	@RequestMapping(method=RequestMethod.POST, value={"/kids/products"})
	public List<NewProductForm> getKidProducts(ModelMap model,
			@RequestParam("page") int page,
			@RequestParam("limit") int limit,
			HttpSession httpSession) {

		logger.info("Kids product scrolling is called");

		Sort sort = new Sort(new Sort.Order(Direction.ASC, "Name"));
		Pageable pageable = new PageRequest(page, limit, sort);

		Category category = categoryService.getByTitle("kids/");

		return getNewProducts(category, pageable);
	}


	@RequestMapping(method=RequestMethod.POST, value={"/accessories/products"})
	public List<NewProductForm> getAcsProducts(ModelMap model,
			@RequestParam("page") int page,
			@RequestParam("limit") int limit,
			HttpSession httpSession) {

		logger.info("Accessories product scrolling is called");

		Sort sort = new Sort(new Sort.Order(Direction.ASC, "Name"));
		Pageable pageable = new PageRequest(page, limit, sort);

		Category category = categoryService.getByTitle("accessories/");

		return getNewProducts(category, pageable);
	}


	private List<NewProductForm> getNewProducts(Category category, Pageable pageable) {

		List<Category> categories = new ArrayList<>();
		categories.add(category);

		List<Product> products = productService.getByCategoryAndPaging(categories, pageable);
		List<Product> allProducts = category.getProducts();

		List<NewProductForm> prodForms = new ArrayList<>();

		int total = allProducts.size()/5;
		if(allProducts.size()%5 > 0) {
			total += 1;
		}

		for(Product p : products) {
			NewProductForm prodForm = new NewProductForm(p);

			List<Producttag> tags = producttagService.getByProduct(p);

			if(tags != null && !tags.isEmpty()) {
				prodForm.setTags(tags);
			}

			List<Image> images = imageService.getByProduct(p.getProductid());
			List<ImageForm> imageForms = new ArrayList<>();

			images.forEach(image->imageForms.add(new ImageForm(image)));

			prodForm.setImages(imageForms);

			prodForm.setTotal(total);

			prodForms.add(prodForm);
		}

		return prodForms;
	}


	@RequestMapping(value = "/cart/list", method = RequestMethod.GET)
	public List<ShopItem> listCart(HttpServletRequest request, ModelMap model, HttpSession session) {
		String cartKey = getCartKey(request);
		logger.info("listCart is called");
		ShoppingCart cart = new ShoppingCart();
		List<ShopItem> items = new ArrayList<ShopItem>();
		//long storeid = 1;
		//ShoppingCart cart = (ShoppingCart) session.getAttribute(cartKey);

		if (session.getAttribute(cartKey) != null) {
			cart = (ShoppingCart) session.getAttribute(cartKey);
			items = cart.getItems();
			logger.info("listCart item: "+items.size());
		} else {
			logger.info("listCart no cart key");
			cart.setItems(items);
			session.setAttribute(cartKey, cart);
		}

		model.addAttribute("cart", cart);

		return items;
	}


	@RequestMapping(method = RequestMethod.POST, value="/cart/add")
	public List<ShopItem> addCart(@RequestBody JsonNode body, ModelMap model, HttpServletRequest request, HttpSession session) {
		String cartKey = getCartKey(request);
		String pid = body.path("productid").asText();
		String sid = body.path("sizeid").asText();
		logger.info("addCart sku: {}, size ID: {}, body: {}", pid, sid, body.toString());
		//Customer customer = customerService.getByUser(getLoginUser(getPrincipal()));
		ShoppingCart cart = new ShoppingCart();
		List<ShopItem> items = new ArrayList<ShopItem>();

		Store store = storeService.getByName("My Pairs");

		if(pid != null && !pid.isEmpty() && sid != null && !sid.isEmpty()) {
			Status received = statusService.getByName("Received");

			if (session.getAttribute(cartKey) != null) {
				cart = (ShoppingCart) session.getAttribute(cartKey);
				items = cart.getItems();
			}

			long productid = Long.parseLong(pid);
			long sizeid = Long.parseLong(sid);
			logger.info("current cart: " + items.size());
			List<Inventory> invs = inventoryService.getByProductidSizeidAvailable(productid, sizeid, store.getStoreid(), received);
			if(invs != null && !invs.isEmpty()) {
				items.add(new ShopItem(invs.get(0).getItem()));
			} else {
				logger.info("Inventory on product {} is not available", productid);
			}
			cart.setItems(items);
			logger.info("after cart: " + items.size());
			System.out.println("after cart: " + items.size());
			session.setAttribute(cartKey, cart);
		}

		return items;
	}


	private String getCartKey(HttpServletRequest request) {
		String ip = getUserIP(request);
		String cartKey = hash(ip + "-cartkey");

		return cartKey;
	}

	private String getUserIP(HttpServletRequest request) {
		String ip = "";

		if(request != null) {
			ip = request.getHeader("X-FORWARDED-FOR");
			if(ip == null || ip.isEmpty()) {
				ip = request.getRemoteAddr();
			}
		}

		return ip;
	}

	private String hash(String source) {
		StringBuilder sb = new StringBuilder();

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashInBytes = md.digest(source.getBytes(StandardCharsets.UTF_8));

			for (byte b : hashInBytes) {
	            sb.append(String.format("%02x", b));
	        }
		} catch(Exception e) {
			logger.error("Exception is caught on generating shopping cart key: {}", e.getMessage());
		}

        return sb.toString();
	}

}

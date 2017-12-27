package com.vhc.controller.store;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vhc.controller.BaseController;
import com.vhc.dto.ImageForm;
import com.vhc.dto.ProductForm;
import com.vhc.model.Image;
import com.vhc.model.Inventory;


@Controller
@RequestMapping(value="/store")
public class StoreProducts extends BaseController {

	@RequestMapping(value="/home")
	public String dspHome(ModelMap model) {
		List<Inventory> inventories = inventoryService.getByStoreid(1);
		
		List<ProductForm> products = new ArrayList<>();

		for(Inventory i: inventories) {
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
		}
		
		model.addAttribute("products", products);
		
		return "store/index";
	}
}

package com.vhc.controller.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vhc.core.model.City;
import com.vhc.core.model.Item;
import com.vhc.core.model.Product;
import com.vhc.core.model.Status;
import com.vhc.core.model.User;
import com.vhc.service.util.ExcelProcessor;


@RestController
@RequestMapping("/admin")
public class AdminRemote extends AdminBase {

	Logger logger = LoggerFactory.getLogger(AdminRemote.class);

	@RequestMapping(method=RequestMethod.POST, value={"/products","/item/products","/category/products"})
	public List<Product> getProducts(ModelMap model, @RequestParam("name") String name, HttpSession httpSession) {
		logger.info("product search is called");

		List<Product> products = productService.getByName("%" + name + "%");

		logger.info("Product Search Result: " + products.size());

		return products;
	}


	/*@RequestMapping(method=RequestMethod.POST, value = {"/api/items1"})
	public List<Item> getItems(ModelMap model, @RequestParam Map<String,String> requestParams, HttpSession httpSession) {
		System.out.println("DataTablesOutput");
		return itemService.getAll();
	}*/

	@RequestMapping(method=RequestMethod.POST, value = "/api/items")
	public @ResponseBody DataTablesOutput<Item> getItems(@Valid @RequestBody DataTablesInput input) {
		System.out.println("DataTablesOutput");
		logger.info("Load item table is called");
		return itemService.getAll(input);

	}

	/*@RequestMapping(method=RequestMethod.POST, value="/giftcardupload")
	public String doGiftcardUpload(@RequestParam("file") MultipartFile file, ModelMap model, HttpSession httpSession) {
		String rtn = "1";

		logger.info("No Gift card called");

		try {
		    InputStream in = file.getInputStream();
		    File currDir = new File("C:\temp");
		    String path = currDir.getAbsolutePath();
		    String fileLocation = path.substring(0, path.length() - 1) + file.getOriginalFilename();
		    FileOutputStream f = new FileOutputStream(fileLocation);
		    int ch = 0;
		    while ((ch = in.read()) != -1) {
		        f.write(ch);
		    }
		    f.flush();
		    f.close();

		    ExcelProcessor processor = new ExcelProcessor();
			processor.read(fileLocation);

		} catch(Exception e) {
			e.printStackTrace();
		}

		List<City> cities = cityService.getAll();
		List<Status> statuss = statusService.getByReftbl("giftcards");

		model.addAttribute("statuss", statuss);
		model.addAttribute("cities", cities);
		model.addAttribute("adminmenu", "Business");

		return rtn;
	}*/
}

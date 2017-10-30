package com.vhc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vhc.service.ProductService;

@Controller
@RequestMapping({"/admin"})
public class Product {

	@Autowired
	ProductService productService;
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/manufacture"})
	public String dspManufacture() {
		String rtn = "admin/manufacture";
		
		
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.POST}, value={"/manufacture"})
	public String doManufacture() {
		String rtn = "admin/manufacture";
		
		
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/brand"})
	public String dspBrand() {
		String rtn = "admin/brand";
		
		
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.POST}, value={"/brand"})
	public String doBrand() {
		String rtn = "admin/brand";
		
		
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/product"})
	public String dspProduct() {
		String rtn = "admin/product";
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.POST}, value={"/product"})
	public String doProduct() {
		String rtn = "admin/product";
		
		
		
		return rtn;
	}
	
	
}

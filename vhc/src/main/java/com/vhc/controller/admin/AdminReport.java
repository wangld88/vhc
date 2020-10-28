package com.vhc.controller.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vhc.core.model.Orderitem;
import com.vhc.core.model.Store;
import com.vhc.core.model.User;
import com.vhc.security.LoginUser;


@Controller
@RequestMapping({"/admin"})
public class AdminReport extends AdminBase {

	private final Logger logger = LoggerFactory.getLogger(AdminReport.class);


	@RequestMapping(method={RequestMethod.GET}, value={"/report/sales"})
	public String dspSales(HttpServletRequest request, ModelMap model, HttpSession httpSession) {
		String rtn = "admin/reportsales";

		logger.info("Generate sales report!");

		User loginUser = this.getPrincipal();

		String id = request.getParameter("storeid");
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		List<Orderitem> items = new ArrayList<>();
		SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT);

		try {

			if((id != null && !id.isEmpty()) || (from != null && !from.isEmpty()) || (to != null && !to.isEmpty())) {
				long storeid = 0;
				//Calendar cal = Calendar.getInstance();
				Calendar fDate = Calendar.getInstance();
				Calendar tDate = Calendar.getInstance();

				if(id != null && !id.isEmpty()) {
					storeid = Long.parseLong(id);
				}

				if(from != null && !from.isEmpty()) {
					fDate.setTime(sf.parse(from));
				} else {
					fDate.setTime(sf.parse("01/01/1900"));
				}

				if(to != null && !to.isEmpty()) {
					tDate.setTime(sf.parse(to));
				} else {
					tDate.setTime(sf.parse("01/01/2900"));
				}

				items = orderitemService.getByStoreDate(storeid, fDate, tDate);

			} else {
				items = orderitemService.getAllInOrder();
			}

		} catch(Exception e) {
			logger.error("[Super Rpt] Exception on retrieving order items: {}", e.getMessage());
		}

		List<Store> stores = storeService.getAll();

		model.addAttribute("items", items);
		model.addAttribute("stores", stores);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Reports");
		model.addAttribute("submenu", "reports");

		return rtn;
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/reports/search"})
	public String dspSearch(HttpServletRequest request, ModelMap model, HttpSession httpSession) {
		String rtn = "admin/reportsales";

		User loginUser = this.getPrincipal();

		String name = request.getParameter("name");

		List<Orderitem> items = new ArrayList<>();

		if(name == null || name.isEmpty()) {
			items = orderitemService.getAllInOrder();
		} else {
			name = "%" + name + "%";
			items = orderitemService.getBySearchInOrder(name);
		}


		model.addAttribute("items", items);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Reports");
		model.addAttribute("submenu", "reports");

		return rtn;
	}

	private User getPrincipal(){
    	User user = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //System.out.println("Role is : "+((LoginStudent)principal).toString());
        if (principal instanceof LoginUser) {
            user = ((LoginUser)principal).getUser();
        } else {
            user = userService.getByUsername("");
        }
        return user;
    }
}

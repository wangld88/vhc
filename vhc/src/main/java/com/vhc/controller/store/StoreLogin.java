package com.vhc.controller.store;

import com.vhc.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/store"})
public class StoreLogin
  extends BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(StoreLogin.class);
  
  @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.GET}, value={"/errorpage/{errorid}"})
  public String globalerrorpage(ModelMap model, @PathVariable("errorid") Long errorid)
  {
    model.addAttribute("errorid", errorid);
    model.addAttribute("title", "Error");
    
    String toRet = "errorpage";
    return toRet;
  }
  
  @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.GET}, value={"/login"})
  public String dspLogin(ModelMap model)
  {
    return "store/login/login";
  }
}

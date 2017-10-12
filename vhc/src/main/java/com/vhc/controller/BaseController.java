package com.vhc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController
{
  @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.GET}, value={"/"})
  public String dspHome(ModelMap model)
  {
    return "index";
  }
}

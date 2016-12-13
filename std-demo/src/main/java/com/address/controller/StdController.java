package com.address.controller;

import com.address.model.StdModel;
import com.address.service.StdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Cuill on 2016/12/12.
 */
@Controller
@RequestMapping(value = "std")
public class StdController {

    @Autowired
    private StdService stdService;

    public StdModel analysis(@RequestParam(value = "address") String address) {
        StdModel stdModel = stdService.analysis(address);
        return null;
    }
}

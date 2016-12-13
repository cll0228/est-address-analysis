package com.address.service;

import com.address.model.StdModel;
import com.address.util.AddressExtractor;
import org.springframework.stereotype.Service;

/**
 * Created by Cuill on 2016/12/12.
 */
@Service
public class StdService {

    public StdModel analysis(String address) {
        StdModel stdModel = AddressExtractor.parseAll("大康路859弄");
        System.out.println(stdModel);
        return null;
    }

    public static void main(String[] args) {
        StdModel stdModel = AddressExtractor.parseAll("武东路198文化花园14号6层604室");
        System.out.println(stdModel.toString());
    }
}

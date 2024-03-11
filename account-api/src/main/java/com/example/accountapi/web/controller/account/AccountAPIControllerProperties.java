package com.example.accountapi.web.controller.account;

public interface AccountAPIControllerProperties {

    String ACCOUNT_COMMON_URL = "/accounts";

    //CUSTOMER
    String CUSTOMER = "/customer";
    String CUSTOMER_LOGIN = CUSTOMER+"/login";
    String CUSTOMER_VERIFY = CUSTOMER+"/verify";

    String CUSTOMER_SIGNUP = CUSTOMER+"/signup";
    String CUSTOMER_UPDATE = CUSTOMER+"/update";

    //SELLER
    String SELLER = "/seller";
    String SELLER_LOGIN = SELLER+"/login";
    String SELLER_VERIFY = SELLER + "/verify";

    String SELLER_SIGNUP = SELLER+"/signup";
    String SELLER_UPDATE = SELLER+"/update";

}

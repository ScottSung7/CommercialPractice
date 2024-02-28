
package com.example.account_api.application.service.signIn.seller;

import com.example.account_api.domain.model.Customer;
import com.example.account_api.domain.model.Seller;
import com.example.account_api.web.validation.form.customer.SignUpCustomerForm;
import com.example.account_api.web.validation.form.seller.SignUpSellerForm;

public interface SignUpSellerService {

    public Seller signUp(SignUpSellerForm form);

    public boolean isEmailExist(String email);

    public Seller changeSellerValidateEmail(Seller SignUpSeller);

    public void sellerVerify(String email);
}

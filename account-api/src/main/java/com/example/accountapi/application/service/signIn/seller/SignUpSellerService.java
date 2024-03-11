
package com.example.accountapi.application.service.signIn.seller;

import com.example.accountapi.domain.model.Seller;
import com.example.accountapi.web.validation.form.seller.SignUpSellerForm;

public interface SignUpSellerService {

    public Seller signUp(SignUpSellerForm form);

    public boolean isEmailExist(String email);

    public Seller changeSellerValidateEmail(Seller SignUpSeller);

    public void sellerVerify(String email);
}

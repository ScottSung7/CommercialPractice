
package com.example.accountapi.application.service.signUp.seller;

import com.example.accountapi.domain.model.Seller;
import com.example.accountapi.web.validation.form.seller.SellerSignUpForm;
import com.example.accountapi.web.validation.form.seller.SellerUpdateForm;

public interface SignUpSellerService {

    public Seller signUp(SellerSignUpForm form);

    public Seller update(SellerUpdateForm sellerUpdateForm);

    public boolean isEmailExist(String email);

    public Seller addExpirationDate(Seller SignUpSeller, int num);

    public boolean sellerVerify(String email);
}

package com.example.accountapi.config.SpringSecurity.id.seller;


import com.example.accountapi.domain.model.Seller;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class SellerPrincipalDetails implements UserDetails {

    private Seller seller;

    public SellerPrincipalDetails(Seller seller) {
        this.seller = seller;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "SELLER";
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return seller.getPassword();
    }

    @Override
    public String getUsername() {
        return seller.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getEmail() {return seller.getEmail();}
    public String getType() {return "SELLER";}
    public Long getId() {return seller.getId();}
}

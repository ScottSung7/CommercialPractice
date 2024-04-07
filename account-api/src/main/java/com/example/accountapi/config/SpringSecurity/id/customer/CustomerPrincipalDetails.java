package com.example.accountapi.config.SpringSecurity.id.customer;


import com.example.accountapi.domain.model.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomerPrincipalDetails implements UserDetails {

    private Customer customer;

    public CustomerPrincipalDetails(Customer customer){
        this.customer = customer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      Collection<GrantedAuthority> collect = new ArrayList<>();
      collect.add(new GrantedAuthority() {
          @Override
          public String getAuthority() {
              return customer.getRole();
          }
      });
      return collect;
    }

    @Override
    public String getPassword() {
        return customer.getPassword();
    }

    @Override
    public String getUsername() {
        return customer.getName();
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

    public String getEmail() {return customer.getEmail();}

    public String getType() {return "CUSTOMER";}

    public Long getId(){return customer.getId();}
}

package com.example.chatapi.config.common.SpringSecurity.id;


import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
public class UserPrincipalDetails implements UserDetails {

    private User user;

    public UserPrincipalDetails(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      Collection<GrantedAuthority> collect = new ArrayList<>();
      collect.add(new GrantedAuthority() {

          @Override
          public String getAuthority() {
              return null;
          }
      });
      return collect;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
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

    public String getEmail() {return user.getEmail();}

    public String getType() {return user.getType();}

    public Long getId() {return user.getId();}
}

package com.warehouse.demo.demospringapp.auth;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.warehouse.demo.demospringapp.domains.Supplier;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SupplierPrincipal implements UserDetails {

private static final long serialVersionUID = -23755486683336993L;
private Supplier supplier;
private List<AuthGroup> authGroups;

public SupplierPrincipal (Supplier supplier, List<AuthGroup> authGroups) {
    super();
    this.supplier = supplier;
    this.authGroups = authGroups;
}

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authGroups == null) {
            return Collections.emptySet();
        }
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        authGroups.forEach(group->{
            grantedAuthorities.add(new SimpleGrantedAuthority(group.getAuthrole()));
        });
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.supplier.getPassword();
    }

    @Override
    public String getUsername() {
        return this.supplier.getUsername();
    }

    public Long getId() {
        return this.supplier.getId();
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
}
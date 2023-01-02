package com.cms.user.application;

import org.springframework.stereotype.Service;

import com.cms.user.domain.SignInForm;
import com.cms.user.domain.model.Customer;
import com.cms.user.domain.model.Seller;
import com.cms.user.exception.CustomException;
import com.cms.user.exception.ErrorCode;
import com.cms.user.service.customer.CustomerService;
import com.cms.user.service.seller.SellerService;
import com.domain.common.UserType;
import com.domain.config.JwtAuthenticationProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignInApplication {

    private final CustomerService customerService;

    private final SellerService sellerService;

    private final JwtAuthenticationProvider provider;

    public String customerLoginToken(SignInForm form){
        // 1. 로그인 가능 여부
        Customer c = customerService.findValidCustomer(form.getEmail(), form.getPassword())
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_CHECK_FAIL));

        // 2. 토큰을 발행하고

        // 3. 토큰 response한다.
        return provider.createToken(c.getEmail(), c.getId(), UserType.CUSTOMER);
    }

    public String sellerLoginToken(SignInForm form){
        // 1. 로그인 가능 여부
        Seller s = sellerService.findValidSeller(form.getEmail(), form.getPassword())
            .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_CHECK_FAIL));
        // 2. 토큰 발행
        // 3. 토큰 response
        return provider.createToken(s.getEmail(), s.getId(), UserType.SELLER);
    }

}

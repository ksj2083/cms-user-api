package com.cms.user.service.seller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cms.user.domain.SignUpForm;
import com.cms.user.domain.model.Seller;
import com.cms.user.domain.repository.SellerRepository;
import com.cms.user.exception.CustomException;
import com.cms.user.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final SellerRepository sellerRepository;

    public Optional<Seller> findByIdAndEmail(Long id, String email){
        return sellerRepository.findByIdAndEmail(id, email);
    }

    public Optional<Seller> findValidSeller(String email, String password){
        return sellerRepository.findByEmailAndPasswordAndVerifyIsTrue(email, password);
    }

    public Seller signUp(SignUpForm form){
        return sellerRepository.save(Seller.from(form));
    }

    public boolean isEmailExist(String email){
        return sellerRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public void verifyEmail(String email, String code){

        Seller seller = sellerRepository.findByEmail(email)
                .orElseThrow( () -> new CustomException(ErrorCode.NOT_FOUND_USER));

        // 이미 인증된 회원일 경우
        if(seller.isVerify()){
            throw new CustomException(ErrorCode.ALREADY_VERIFY);
        }
        else if(!seller.getVerificationCode().equals(code)){
            throw new CustomException(ErrorCode.WRONG_VERIFICATION);
        }
        else if(seller.getVerifyExpiredAt().isBefore(LocalDateTime.now())){
            throw new CustomException(ErrorCode.EXPIRE_CODE);
        }

        seller.setVerify(true);

        sellerRepository.save(seller);
    }

    @Transactional
    public LocalDateTime changeSellerValidateEmail(Long customerId, String verificationCode){

        Optional<Seller> sellerOptional = sellerRepository.findById(customerId);

        if(sellerOptional.isPresent()){
            Seller seller = sellerOptional.get();
            seller.setVerificationCode(verificationCode);
            seller.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
            return seller.getVerifyExpiredAt();
        }
        else{
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
    }
}

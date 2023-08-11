package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.common.error.MemberNotFoundException;
import com.sklookiesmu.wisefee.common.error.ValidateMemberException;
import com.sklookiesmu.wisefee.domain.Address;
import com.sklookiesmu.wisefee.domain.Member;
import com.sklookiesmu.wisefee.repository.AddressRepository;
import com.sklookiesmu.wisefee.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    @Transactional
    public Long insert(Address address){
        addressRepository.create(address);
        return address.getAddrId();
    }

    @Transactional
    public Address selectById(Long id){
        Address address = addressRepository.find(id);
        return address;
    }

}
package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.domain.Address;
import com.sklookiesmu.wisefee.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

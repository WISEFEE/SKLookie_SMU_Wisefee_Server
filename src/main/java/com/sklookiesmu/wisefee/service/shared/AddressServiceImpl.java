package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.common.exception.global.NoSuchElementFoundException;
import com.sklookiesmu.wisefee.domain.Address;
import com.sklookiesmu.wisefee.repository.AddressRepository;
import com.sklookiesmu.wisefee.service.shared.interfaces.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    @Transactional
    public Long addAddress(Address address){
        addressRepository.create(address);
        return address.getAddrId();
    }

    @Transactional
    public Address getAddressById(Long id){
        Address address = addressRepository.find(id);
        if(address == null){
            throw new NoSuchElementFoundException("주소 정보를 찾을 수 없습니다.");
        }
        return address;
    }

}

package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.domain.SubTicketType;
import com.sklookiesmu.wisefee.repository.SubTicketTypeRepository;
import com.sklookiesmu.wisefee.service.shared.interfaces.SubTicketTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubTicketTypeImpl implements SubTicketTypeService {
    private final SubTicketTypeRepository subTicketTypeRepository;

    @Transactional
    public Long addTicketType(SubTicketType ticket){
        subTicketTypeRepository.create(ticket);
        return ticket.getSubTicketId();
    }

    public SubTicketType selectById(Long id){
        SubTicketType ticket = subTicketTypeRepository.find(id);
        return ticket;
    }

    public List<SubTicketType> selectList(){
        List<SubTicketType> tickets = subTicketTypeRepository.findList();
        return tickets;
    }



}

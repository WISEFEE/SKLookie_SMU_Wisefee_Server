package com.sklookiesmu.wisefee.repository;
import com.sklookiesmu.wisefee.domain.Address;
import com.sklookiesmu.wisefee.domain.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class FileRepository {
    private final EntityManager em;

    /**
     * [File 엔티티 추가]
     * 주소 추가
     * @param [File File 엔티티(pk=null)]
     */
    public void insertFile(File file){
        em.persist(file);
    }


}

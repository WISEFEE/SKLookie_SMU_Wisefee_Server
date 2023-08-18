package com.sklookiesmu.wisefee.repository;
import com.sklookiesmu.wisefee.domain.Address;
import com.sklookiesmu.wisefee.domain.File;
import com.sklookiesmu.wisefee.dto.shared.file.FileInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class FileRepository {
    private final EntityManager em;

    /**
     * [File 엔티티 추가]
     * 파일 추가
     * @param [File File 엔티티(pk=null)]
     */
    public void insertFile(File file){
        em.persist(file);
    }


    /**
     * [ID로 File 엔티티의 Info 얻어오기 ]
     * 파일 경로 검색
     * @param [Long id : PK]
     */
    public FileInfoDto getFilePathById(Long id){
        File result = em.createQuery("select f from File f where f.id = :id", File.class)
                .setParameter("id", id)
                .getSingleResult();

        return new FileInfoDto(
                result.getFileType(),
                result.getName(),
                result.getFilePath(),
                result.getFileCapacity());
    }


    /**
     * [ID로 File 엔티티 얻어오기 ]
     * 파일 검색
     * @param [Long id : PK]
     */
    public File findById(Long id){
        File result = em.createQuery("select f from File f where f.id = :id", File.class)
                .setParameter("id", id)
                .getSingleResult();
        return result;
    }


}

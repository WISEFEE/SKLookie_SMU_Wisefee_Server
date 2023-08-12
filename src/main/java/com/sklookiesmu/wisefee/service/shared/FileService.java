package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.domain.Address;
import com.sklookiesmu.wisefee.dto.shared.file.FileInfoDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    /**
     * [파일 업로드]
     * Multipart 파일을 입력받아 서버 내부 스토리지에 저장.
     * @param [MultipartFile 파일]
     * @return [FileinfoDto 파일 정보]
     */
    public abstract FileInfoDto uploadFile(MultipartFile file);


    /**
     * [파일정보 저장]
     * 업로드된 파일 정보를 데이터베이스에 저장
     * @param [FileInfoDto 파일정보]
     * @return [Long 파일 PK]
     */
    public abstract Long insertFileInfo(FileInfoDto fileinfo);





}
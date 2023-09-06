package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.domain.Address;
import com.sklookiesmu.wisefee.dto.shared.file.FileInfoDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

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
    public abstract Long addFileInfo(FileInfoDto fileinfo, Long memberPK);


    /**
     * [해당 ID의 이미지 Info 얻어오기]
     * 업로드된 파일의 ID를 통해 경로 얻어오기
     * @param [Long 파일 PK]
     * @return [FileInfoDto 이미지 Info]
     */
    public abstract FileInfoDto getImageInfoById(Long id);


    /**
     * [경로를 기반으로 이미지 바이트 스트림 반환]
     * 해당 경로의 이미지의 바이트 스트림 형태를 얻음
     * @param [String 파일 경로]
     * @return [byte[] 이미지 바이트 배열]
     */
    public abstract byte[] getImageFile(Path path);





}
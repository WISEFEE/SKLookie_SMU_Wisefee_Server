package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.common.constant.FileConstant;
import com.sklookiesmu.wisefee.common.error.FileUploadException;
import com.sklookiesmu.wisefee.common.file.FileUtil;
import com.sklookiesmu.wisefee.dto.shared.file.FileInfoDto;
import com.sklookiesmu.wisefee.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    @Value("${upload.directory}")
    private String uploadDirectory;


    /**
     * [파일 업로드]
     * Multipart 파일을 입력받아 서버 내부 스토리지에 저장.
     * @param [MultipartFile 파일]
     * @return [FileinfoDto 파일 정보]
     */
    public FileInfoDto uploadFile(MultipartFile file){

        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));   //확장자

        //최대용량 체크
        if (file.getSize() > FileConstant.MAX_FILE_SIZE) {
            throw new FileUploadException("10MB 이하 파일만 업로드 할 수 있습니다.");
        }

        //MIMETYPE 체크
        if (!FileUtil.isImageFile(fileExtension)) {
            throw new FileUploadException("이미지 파일만 업로드할 수 있습니다.");
        }

        //저장 파일명을 중복방지 고유명으로 변경
        String newFileName = generateUniqueFileName(fileExtension);
        Path filePath = Paths.get(uploadDirectory + File.separator + newFileName);


        //서버 내부 스토리지에 업로드
        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            throw new FileUploadException("File upload exception." + e.getMessage());
        }

        return new FileInfoDto(file.getContentType(),
                file.getOriginalFilename(),
                filePath.toString(),
                Long.toString(file.getSize()));
    }


    /**
     * [파일정보 저장]
     * 업로드된 파일 정보를 데이터베이스에 저장
     * @param [FileInfoDto 파일정보]
     * @return [Long 파일 PK]
     */
    @Transactional
    public Long insertFileInfo(FileInfoDto fileinfo){
        com.sklookiesmu.wisefee.domain.File file = new com.sklookiesmu.wisefee.domain.File();
        file.setFileType(fileinfo.getFileType()); //MIMETYPE(~확장자)
        file.setFileCapacity(fileinfo.getFileCapacity()); //용량
        file.setName(fileinfo.getName()); //이름
        file.setFilePath(fileinfo.getFilePath()); //경로
        file.setFileInfo(""); //설명
        file.setDeleted(false);
        this.fileRepository.insertFile(file);
        return file.getFileId();
    }


    /**
     * [중복방지를 위한 파일 고유명 생성]
     * @param fileExtension 확장자
     * @return String 파일 고유이름
     */
    private String generateUniqueFileName(String fileExtension) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeStamp = dateFormat.format(new Date());
        return timeStamp + fileExtension;
    }

}

package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.common.constant.FileConstant;
import com.sklookiesmu.wisefee.common.error.FileDownloadException;
import com.sklookiesmu.wisefee.common.error.FileUploadException;
import com.sklookiesmu.wisefee.common.file.FileUtil;
import com.sklookiesmu.wisefee.domain.Member;
import com.sklookiesmu.wisefee.dto.shared.file.FileInfoDto;
import com.sklookiesmu.wisefee.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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
import java.util.Random;

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
    @Transactional()
    public FileInfoDto uploadFile(MultipartFile file){

        String originalFileName = file.getOriginalFilename();
        String mimeType = file.getContentType();

        //최대용량 체크
        if (file.getSize() > FileConstant.MAX_FILE_SIZE) {
            throw new FileUploadException("10MB 이하 파일만 업로드 할 수 있습니다.");
        }


        //MIMETYPE 체크
        if (!FileUtil.isImageFile(mimeType)) {
            throw new FileUploadException("이미지 파일만 업로드할 수 있습니다.");
        }

        //저장 파일명을 중복방지 고유명으로 변경
        String newFileName = generateUniqueFileName(originalFileName);
        Path filePath = Paths.get(uploadDirectory + File.separator + newFileName);


        //서버 내부 스토리지에 업로드
        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            throw new FileUploadException("File upload exception. " + e.getStackTrace());
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
    public Long addFileInfo(FileInfoDto fileinfo, Long memberPK){
        com.sklookiesmu.wisefee.domain.File file = new com.sklookiesmu.wisefee.domain.File();
        file.setFileType(fileinfo.getFileType()); //MIMETYPE(~확장자)
        file.setFileCapacity(fileinfo.getFileCapacity()); //용량
        file.setName(fileinfo.getName()); //이름
        file.setFilePath(fileinfo.getFilePath()); //경로
        file.setFileInfo(FileConstant.FILE_INFO_NO_USE); //정보
        file.setDeleted(false);

        Member member = new Member();
        member.setMemberId(memberPK);
        file.setMember(member);

        this.fileRepository.create(file);
        return file.getFileId();
    }

    /**
     * [해당 ID의 이미지 Info 얻어오기]
     * 업로드된 파일의 ID를 통해 경로 얻어오기
     * @param [Long 파일 PK]
     * @return [FileInfoDto 이미지 Info]
     */
    public FileInfoDto getImageInfoById(Long id){
        FileInfoDto info = this.fileRepository.getFilePathById(id);
        return info;
    }

    /**
     * [경로를 기반으로 이미지 바이트 스트림 반환]
     * 해당 경로의 이미지의 바이트 스트림 형태를 얻음
     * @param [Path 파일 경로]
     * @return [byte[] 이미지 바이트 배열]
     */
    public byte[] getImageFile(Path path){
        try {
            byte[] imageBytes = Files.readAllBytes(path);
            return imageBytes;
        } catch (IOException e) {
            throw new FileDownloadException("File download fail." + e.getMessage());
        }
    }




    /**
     * [중복방지를 위한 파일 고유명 생성]
     * @param fileExtension 확장자
     * @return String 파일 고유이름
     */
    private String generateUniqueFileName(String originalFileName) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        // Random 객체 생성
        Random random = new Random();
        // 0 이상 100 미만의 랜덤한 정수 반환
        String randomNumber = Integer.toString(random.nextInt(Integer.MAX_VALUE));
        String timeStamp = dateFormat.format(new Date());
        return timeStamp + randomNumber + originalFileName;
    }

}

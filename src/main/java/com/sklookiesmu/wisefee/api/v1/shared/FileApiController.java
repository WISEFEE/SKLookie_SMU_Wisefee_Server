package com.sklookiesmu.wisefee.api.v1.shared;

import com.sklookiesmu.wisefee.common.auth.SecurityUtil;
import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.dto.shared.file.FileInfoDto;
import com.sklookiesmu.wisefee.service.shared.FileService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;


@Api(tags = "COMM-E :: 파일 API")
@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class FileApiController {
    private final FileService fileService;

    @ApiOperation(
            value = "COMM-E-01 :: 이미지 파일 업로드",
            notes = "이미지 파일을 업로드 할 때 사용되는 API입니다. 매장 사진, 메뉴 사진 등을 등록하기 이전 해당 API를 호출하여 사용합니다.<br>" +
                    "파일 업로드 성공 시, 파일 ID(PK)가 반환되며, 반환된 ID를 매장 사진 등록 API 등에서 사용하면 됩니다.<br>" +
                    "최대 용량은 10MB로 제한되며, 파일 타입 또한 이미지 타입으로 제한합니다."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "파일 업로드 성공 시 업로드된 파일의 ID(PK)를 반환합니다."),
            @ApiResponse(code = 500, message = "파일 업로드 실패 시 다음 코드를 반환합니다. 최대 용량 초과(10MB), 이미지 파일이 아닌 파일을 업로드, 내부 서버 오류의 사유가 있을 수 있습니다.")
    })
    @PreAuthorize(AuthConstant.AUTH_ROLE_COMMON_USER)
    @PostMapping(value = "",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> addFile(
            @ApiParam(value = "multipart/form-data 형식의 이미지")
            @RequestPart("multipartFile")
            MultipartFile file) {
        Long memberPK = SecurityUtil.getCurrentMemberPk();
        FileInfoDto fileinfo = fileService.uploadFile(file);
        Long success = fileService.insertFileInfo(fileinfo, memberPK);

        return ResponseEntity.status(HttpStatus.OK).body(success);
    }


    @ApiOperation(
            value = "COMM-E-02 :: 이미지 파일 다운로드",
            notes = "파일 ID(PK)를 기반으로 이미지를 다운로드 할 때 사용되는 API입니다." +
                    "요청 시, 이미지 파일(바이트 배열)이 반환됩니다."
    )
    @PreAuthorize(AuthConstant.AUTH_ROLE_COMMON_USER)
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id){
        // ID를 통해 이미지 파일의 경로를 얻어옴
        FileInfoDto info = this.fileService.getImageInfoById(id);
        Path imagePath = Paths.get(info.getFilePath());
        String mimeType = info.getFileType();

        // 이미지 파일을 바이트 배열로 읽어옴
        byte[] imageBytes = this.fileService.getImageFile(imagePath);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(mimeType))  // 이미지 타입에 맞게 설정
                .body(imageBytes);

    }



}
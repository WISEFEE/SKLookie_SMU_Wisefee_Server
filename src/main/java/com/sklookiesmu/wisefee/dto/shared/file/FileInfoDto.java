package com.sklookiesmu.wisefee.dto.shared.file;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "파일 정보 DTO")
public class FileInfoDto {

    /**
     * 파일 확장자
     */
    @NotNull
    private String fileType;


    /**
     * 파일명
     */
    @NotNull
    private String name;


    /**
     * 파일 경로
     */
    @NotNull
    private String filePath;


    /**
     * 파일용량
     */
    @NotNull
    private String fileCapacity;

}
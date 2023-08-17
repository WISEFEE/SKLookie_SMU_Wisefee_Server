package com.sklookiesmu.wisefee.dto.seller;

import com.sklookiesmu.wisefee.domain.Cafe;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "카페와 카페 이미지의 개수 DTO")
public class CafeAndImageCountDto {
    private Cafe cafe;
    private int imageCount;

}
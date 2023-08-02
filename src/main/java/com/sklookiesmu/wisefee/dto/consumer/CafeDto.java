package com.sklookiesmu.wisefee.dto.consumer;

import com.sklookiesmu.wisefee.domain.Cafe;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

public class CafeDto {

    /**
     *  TODO: 매장 위치 정보 필요 !!
     */
    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CafeRequest{

        @ApiModelProperty(value = "매장명", required = true)
        private String title;

        @ApiModelProperty(value = "매장 설명", required = true)
        private String content;

        @ApiModelProperty(value = "매장 연락처", required = true)
        private String cafePhone;
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CafeResponseDto{

        @ApiModelProperty(value = "매장 PK", required = true)
        private Long cafeId;

        @ApiModelProperty(value = "매장명", required = true)
        private String title;

        @ApiModelProperty(value = "매장 설명", required = true)
        private String content;

        @ApiModelProperty(value = "매장 연락처", required = true)
        private String cafePhone;
        // private String location;

        public static CafeResponseDto from(Cafe cafe){
            return new CafeResponseDto(
                    cafe.getCafeId(),
                    cafe.getTitle(),
                    cafe.getContent(),
                    cafe.getCafePhone());
        }
    }

    /**
     * 카페 매장 리스트
     */
    @Getter
    @AllArgsConstructor
    public static class CafeListResponseDto{
        private List<CafeResponseDto> cafes;

        public static CafeListResponseDto from(Slice<Cafe> cafeList){
            List<CafeDto.CafeResponseDto> cafeResponses = cafeList.getContent()
                    .stream()
                    .map(CafeDto.CafeResponseDto::from)
                    .collect(Collectors.toList());

            return new CafeListResponseDto(cafeResponses);
        }
    }
}

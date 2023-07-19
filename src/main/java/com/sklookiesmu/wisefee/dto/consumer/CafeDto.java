package com.sklookiesmu.wisefee.dto.consumer;

import com.sklookiesmu.wisefee.domain.Cafe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

public class CafeDto {


    /**
     *  매장 위치 정보 필요 !!
     */
    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CafeRequest{

        private String title;
        private String content;
        private String cafePhone;

    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CafeResponseDto{
        private String title;
        private String content;
        private String cafePhone;
        // private String location;

        public static CafeResponseDto from(Cafe cafe){
            return new CafeResponseDto(
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

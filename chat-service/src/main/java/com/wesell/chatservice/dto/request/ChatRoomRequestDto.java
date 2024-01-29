package com.wesell.chatservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomRequestDto {

    @NotBlank(message = "NullPointer Error - 구매자")
    private String consumer; // 구매자 uuid

    @NotNull(message = "NullPointer Error - 판매품")
    private Long productId; // 판매품 id

    @NotBlank(message = "NullPointer Error - 판매자")
    private String seller; // 판매자 nickname

}

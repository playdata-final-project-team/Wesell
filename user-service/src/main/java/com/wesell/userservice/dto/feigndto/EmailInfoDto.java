package com.wesell.userservice.dto.feigndto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailInfoDto {

    private String email;
    private String uuid;

}

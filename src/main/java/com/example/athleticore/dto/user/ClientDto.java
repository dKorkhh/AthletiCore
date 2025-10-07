package com.example.athleticore.dto.user;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto extends UserDto {
    private String phoneNumber;
}

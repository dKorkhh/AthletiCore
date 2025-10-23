package com.example.athleticore.dto.user;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto extends UserDto {
    private String phoneNumber;
}

package com.example.athleticore.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDto extends User{
    private FullName fullName;
    private String email;
    private String password;
}

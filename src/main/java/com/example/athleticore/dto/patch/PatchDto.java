package com.example.athleticore.dto.patch;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatchDto {
    private Long idUser;
    private String email;
}

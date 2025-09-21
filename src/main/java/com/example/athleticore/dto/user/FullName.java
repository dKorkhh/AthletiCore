package com.example.athleticore.dto.user;

import lombok.Data;
import lombok.NonNull;

@Data
public class FullName {
    @NonNull
    private String firstname;
    @NonNull
    private String lastname;
    private String middleName;
}

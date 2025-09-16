package com.example.athleticore.Entity;

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

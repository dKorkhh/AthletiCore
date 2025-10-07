package com.example.athleticore.dto.user;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullName {
    private String firstName;
    private String lastName;
    private String middleName;
}

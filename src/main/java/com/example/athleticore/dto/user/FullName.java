package com.example.athleticore.dto.user;

import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FullName {
    private String firstName;
    private String lastName;
    private String middleName;
}

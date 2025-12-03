package com.example.athleticore.dto.user;

import jakarta.persistence.Embeddable;
import lombok.*;
import org.apache.logging.log4j.util.Strings;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FullName {
    private String firstName;
    private String lastName;
    private String middleName;

    @Override
    public String toString() {
        return firstName + ' ' + lastName + ' ' + ((!Strings.isEmpty(middleName)) ?  middleName : "");
    }
}

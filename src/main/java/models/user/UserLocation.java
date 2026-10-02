package models.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLocation {

    private String street;
    private String city;
    private String state;
    private String country;
}

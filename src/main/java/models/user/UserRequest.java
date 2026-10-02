package models.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class UserRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserLocation location;
}

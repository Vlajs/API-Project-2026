package models.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

//@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserLocation location;
    private String registerDate;
    private String updatedDate;
    private String title;
    private String picture;

}

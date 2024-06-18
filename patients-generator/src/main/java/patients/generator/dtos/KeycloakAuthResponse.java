package patients.generator.dtos;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KeycloakAuthResponse {

    @SerializedName("access_token")
    private String accessToken;
}

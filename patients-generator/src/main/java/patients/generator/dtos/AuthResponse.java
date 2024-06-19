package patients.generator.dtos;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto to store access token from auth response.
 */
@Data
@NoArgsConstructor
public class AuthResponse {

    @SerializedName("access_token")
    private String accessToken;
}

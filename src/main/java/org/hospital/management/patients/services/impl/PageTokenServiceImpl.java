package org.hospital.management.patients.services.impl;

import com.google.gson.Gson;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.hospital.management.patients.dtos.PageTokenDto;
import org.hospital.management.patients.exceptions.IncorrectNextPageTokenException;
import org.hospital.management.patients.services.PageTokenService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PageTokenServiceImpl implements PageTokenService {

    private final Gson gson;

    @Override
    public String encode(PageTokenDto pageTokenDto) {
        var pageTokenToEncode = gson.toJson(pageTokenDto);

        return Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(pageTokenToEncode.getBytes());
    }

    @Override
    public PageTokenDto decode(String nextPageToken) {
        try {
            var decodedToken = Base64.getUrlDecoder().decode(nextPageToken);

            return gson.fromJson(new String(decodedToken), PageTokenDto.class);
        } catch (IllegalArgumentException ex) {
            throw new IncorrectNextPageTokenException(
                String.format("Next page token = %s is incorrect", nextPageToken),
                ex
            );
        }
    }
}

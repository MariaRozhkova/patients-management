package org.hospital.management.patients.services.impl;

import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.hospital.management.patients.dtos.PaginationTokenDto;
import org.hospital.management.patients.exceptions.IncorrectPaginationTokenException;
import org.hospital.management.patients.services.PageTokenService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaginationTokenServiceImpl implements PageTokenService {

    private final Gson gson;

    @Override
    public String encode(PaginationTokenDto paginationTokenDto) {
        var pageTokenToEncode = gson.toJson(paginationTokenDto);

        return Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(pageTokenToEncode.getBytes());
    }

    @Override
    public PaginationTokenDto decode(String nextPageToken) {
        try {
            var decodedToken = Base64.getUrlDecoder().decode(nextPageToken);

            return gson.fromJson(
                new String(decodedToken),
                PaginationTokenDto.class
            );
        } catch (IllegalArgumentException ex) {
            throw new IncorrectPaginationTokenException(
                String.format("Next page token = %s is incorrect", nextPageToken),
                ex
            );
        }
    }
}

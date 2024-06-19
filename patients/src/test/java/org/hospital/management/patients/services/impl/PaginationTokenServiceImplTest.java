package org.hospital.management.patients.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hospital.management.patients.dtos.PaginationTokenDto;
import org.hospital.management.patients.exceptions.IncorrectPaginationTokenException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaginationTokenServiceImplTest {

    private static final String PAGINATION_TOKEN = "token";
    private static final String ENCODED_TOKEN = "dG9rZW4";
    private static final UUID ID = UUID.randomUUID();
    private static final LocalDateTime CURRENT_DATE = LocalDateTime.now();

    @Mock
    private Gson gson;

    @InjectMocks
    private PaginationTokenServiceImpl pageTokenService;

    @Test
    void shouldEncodePaginationToken() {
        var paginationToken = new PaginationTokenDto(ID, CURRENT_DATE);

        when(gson.toJson(paginationToken)).thenReturn(PAGINATION_TOKEN);

        var actualEncodedToken = pageTokenService.encode(paginationToken);

        assertEquals(ENCODED_TOKEN, actualEncodedToken);
    }

    @Test
    void shouldDecodePaginationToken() {
        var paginationToken = new PaginationTokenDto(ID, CURRENT_DATE);

        when(gson.fromJson(PAGINATION_TOKEN, PaginationTokenDto.class))
            .thenReturn(paginationToken);

        var actualDecodedToken = pageTokenService.decode(ENCODED_TOKEN);

        assertEquals(paginationToken, actualDecodedToken);
    }

    @Test
    void shouldThrowExceptionWhenPaginationTokenIsInvalid() {
        var invalidToken = "invalidToken";
        when(gson.fromJson(anyString(), eq(PaginationTokenDto.class)))
            .thenThrow(new IllegalArgumentException("error"));

        var actualResult = assertThrows(
            IncorrectPaginationTokenException.class,
            () -> pageTokenService.decode(invalidToken)
        );

        var expectedMessage = String.format(
            "Next page token = %s is incorrect",
            invalidToken
        );
        assertEquals(expectedMessage, actualResult.getMessage());
    }
}

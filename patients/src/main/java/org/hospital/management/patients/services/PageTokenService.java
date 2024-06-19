package org.hospital.management.patients.services;

import org.hospital.management.patients.dtos.PaginationTokenDto;

/**
 * Service for encoding and decoding pagination token.
 */
public interface PageTokenService {

    /**
     * Encodes pagination token.
     */
    String encode(PaginationTokenDto paginationTokenDto);

    /**
     * Decodes pagination token.
     */
    PaginationTokenDto decode(String nextPageToken);
}

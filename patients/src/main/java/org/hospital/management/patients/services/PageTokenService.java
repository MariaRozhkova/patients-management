package org.hospital.management.patients.services;

import org.hospital.management.patients.dtos.PaginationTokenDto;

public interface PageTokenService {

    String encode(PaginationTokenDto paginationTokenDto);

    PaginationTokenDto decode(String nextPageToken);
}

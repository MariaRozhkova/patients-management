package org.hospital.management.patients.services;

import org.hospital.management.patients.dtos.PageTokenDto;

public interface PageTokenService {

    String encode(PageTokenDto pageTokenDto);

    PageTokenDto decode(String nextPageToken);
}

package org.hospital.management.patients.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Dto to store pagination response.
 */
@Data
@AllArgsConstructor
public class PaginationResponse<T> {

    private List<T> items;
    private String nextPageToken;
}

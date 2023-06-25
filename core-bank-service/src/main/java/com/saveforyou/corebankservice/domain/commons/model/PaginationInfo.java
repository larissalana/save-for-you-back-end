package com.saveforyou.corebankservice.domain.commons.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaginationInfo {

    private long currentPage;
    private long totalPages;
    private long totalItems;
}
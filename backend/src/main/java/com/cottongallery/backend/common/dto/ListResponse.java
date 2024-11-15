package com.cottongallery.backend.common.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListResponse<T> {
    private PageInfo pageInfo;
    private T contents;
}



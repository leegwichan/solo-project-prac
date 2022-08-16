package com.example.projectPrac.v1.dto;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

public class MultiResponseDto<T> {

    private List<T> data;
    private PageInfo pageInfo;

    public MultiResponseDto(List<T> data, Page page){
        this.data = data;
        this.pageInfo = new PageInfo(page.getNumber()+1, page.getSize(),
                page.getTotalElements(), page.getTotalPages());
    }




    private class PageInfo{
        private int page;
        private int size;
        private long totalElements;
        private int totalPages;

        PageInfo(int page, int size, long totalElements, int totalPages){
            this.page = page;
            this.size = size;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
        }
    }
}

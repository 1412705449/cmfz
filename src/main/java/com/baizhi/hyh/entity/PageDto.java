package com.baizhi.hyh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageDto implements Serializable {
    private Integer page;
    private Integer total;
    private Integer records;
    private List<Banner> rows;
}

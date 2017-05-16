package com.cly.crystal.datasource.page;

import java.util.List;

import lombok.Data;

@Data
public class Page<T> {

    /** 当前页 */
    private int     currentPage;

    /** 当前页显示记录条数 */
    private int     pageSize;

    /** 取得查询总记录数 */
    private long    count;

    /** 总页数 */
    private long    pageCount;

    /** 数据集合 */
    private List<T> list;

}

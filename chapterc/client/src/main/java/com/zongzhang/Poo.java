package com.zongzhang;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zongzhang
 * @since 1.0
 * <p>
 * create on 2018/6/18
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class Poo {
    private Integer id;
    private String name;
    private Integer sex;
}

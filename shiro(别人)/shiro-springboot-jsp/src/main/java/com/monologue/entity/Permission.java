package com.monologue.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by Monologue_zsj on 2021/3/7 15:52
 * Author：小脸儿红扑扑
 * Description：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Permission implements Serializable {

    private Integer id;
    private String name;
    private String url;
}

package com.bc.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhankun
 * @date 2021-09-28 16:03:11
 */
@Data
@Builder
public class UserDto implements Serializable {

    private static final long serialVersionUID = -1921212077748569015L;

    private Long userId;

    private String name;
}

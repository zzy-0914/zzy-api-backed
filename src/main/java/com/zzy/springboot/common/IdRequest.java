package com.zzy.springboot.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class IdRequest implements Serializable {

    private  Long id  ;
    private static final long serialVersionUID = 6956465256439222621L;
}

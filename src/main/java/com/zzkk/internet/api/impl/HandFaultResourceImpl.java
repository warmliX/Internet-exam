package com.zzkk.internet.api.impl;

import com.zzkk.internet.api.HandFaultResource;
import com.zzkk.internet.common.annotation.RestResource;

/**
 * @author warmli
 */
@RestResource
public class HandFaultResourceImpl implements HandFaultResource {

    @Override
    public String handFault(String token) {
        System.out.println(token);

        return null;
    }
}

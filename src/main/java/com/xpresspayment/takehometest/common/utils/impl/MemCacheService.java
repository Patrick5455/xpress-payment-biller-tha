/*
 * Copyright (c) 2022. Fintellics Technologies Inc and its subsidiaries - All Rights Reserved.
 *  Unauthorized copying of this file and other files within the project, via any medium is strictly prohibited Proprietary and
 *    confidential  Written by Patrick Ojunde <p@revnorth.io>
 */

package com.xpresspayment.takehometest.commons.utils.impl;

import com.xpresspayment.takehometest.commons.utils.i.CachingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Qualifier(value = "memCache")
public class MemCacheService extends CachingService<String, Object> {

    @Override
    public Object getOrDefault(String s, Object defaultValue) {
        return super.getOrDefault(s, defaultValue);
    }

    @Override
    public void saveOrUpdate(String key, Object value) {
        super.saveOrUpdate(key, value);
    }

    @Override
    public void remove(String key) {
        super.remove(key);
    }

    @Override
    public long expireKey (String key, int seconds) {
        return super.expireKey(key, seconds);
    }

    @Override
    public boolean contains(String key) {
        return super.contains(key);
    }
}

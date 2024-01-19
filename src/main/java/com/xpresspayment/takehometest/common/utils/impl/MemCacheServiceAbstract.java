
package com.xpresspayment.takehometest.common.utils.impl;

import com.xpresspayment.takehometest.common.utils.i.AbstractCachingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Qualifier(value = "memCache")
public class MemCacheServiceAbstract extends AbstractCachingService<String, Object> {

}

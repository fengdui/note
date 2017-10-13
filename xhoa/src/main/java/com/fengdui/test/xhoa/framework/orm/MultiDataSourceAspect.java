package com.fengdui.test.xhoa.framework.orm;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @author Wander.Zeng
 * @data 2015-6-16 下午5:49:21
 * @desc MultiDataSourceAspectAdvice.java
 */
@Component
@Aspect
public class MultiDataSourceAspect implements Ordered {

	@Around("execution(* com.xh.market.business.*.service..*.*(..))")
	public Object doAround(ProceedingJoinPoint jp) throws Throwable {
		Object obj = jp.getTarget();
		MultiDataSourceAnnotation annotation = obj.getClass().getAnnotation(MultiDataSourceAnnotation.class);
		if (null != annotation) {
			String key = annotation.key();
			if (StringUtils.isBlank(key)) {
				MultiDataSource.setDataSourceKey(null);
			} else {
				MultiDataSource.setDataSourceKey(key);
			}
		} else {
			MultiDataSource.setDataSourceKey(null);
		}
		return jp.proceed();
	}

	@Override
	public int getOrder() {
		return 1;
	}

}

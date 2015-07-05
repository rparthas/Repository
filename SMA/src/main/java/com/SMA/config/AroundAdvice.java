package com.SMA.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.SMA.Constants;
import com.SMA.entity.Response;

@Aspect
@Component
public class AroundAdvice {

	final static Logger logger = LoggerFactory.getLogger(AroundAdvice.class);

	@ResponseBody
	@Around("execution(* com.SMA..*.*(..))")
	public Object logging(ProceedingJoinPoint pjp) throws Throwable {
		Object retval = null;
		try {
			retval = pjp.proceed();
		} catch (Exception e) {
			logger.error("Exception occured",e);
			retval = new Response(Constants.EXCEPTION_OCCURED);
		}
		return retval;
	}

}

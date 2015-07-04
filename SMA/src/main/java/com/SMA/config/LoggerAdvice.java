package com.SMA.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.SMA.entity.Response;

@Aspect
@Component
public class LoggerAdvice {

	final static Logger logger = LoggerFactory.getLogger(LoggerAdvice.class);

	@ResponseBody
	@Around("execution(* com.SMA..*.*(..))")
	public Object logging(ProceedingJoinPoint pjp) throws Throwable {
		Object retval = null;
		try {
			retval = pjp.proceed();
		} catch (Exception e) {
			retval = new Response(
					"Ha ocurrido un error. Por favor, intentelo de nuevo mas tarde");
		}
		return retval;
	}

}

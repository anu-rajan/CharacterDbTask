package com.hibernate.colour.task.annotations;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogParams {
	
	Logger logger = Logger.getLogger(LogParams.class);
	
	@Before("@annotation(logMethodParams)")
	public void logExecutionTime(JoinPoint joinPoint, LogMethodParams logMethodParams) throws Throwable {
		String logString = getLogParamText(logMethodParams.paramNames(), joinPoint.getArgs());
		
		//To print in console
		System.out.println("Entering method ::: " + joinPoint.getSignature() + " with params" + logString);
		
		//To write to log file
		logger.log(Level.INFO, "Entering method ::: " + joinPoint.getSignature() + " with params" + logString);
	}
	
	
	private String getLogParamText(String[] paramNames, Object[] params) {
		StringBuilder logString = new StringBuilder("\n");
		for(int i=0;i<paramNames.length;i++) {
			logString.append(paramNames[i] +":"+ params[i]+"\n");
		}
		return logString.toString();
	}
}

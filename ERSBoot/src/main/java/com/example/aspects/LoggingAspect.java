package com.example.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.example.models.Ticket;

//Mark this class as an aspect, and as a component
@Component
@Aspect
public class LoggingAspect {
	
	//All our methods will be advice to be taken at "very specific" point in the excution of our application
	
	//Imagine we have imported/created a logging object to log to a file
	
    /*
     * PointCut expressions are used to select joinpoints, or in other works, pointcuts target a subset of joinpoints
     * PointCut expression symbols
     * "*" is a wildcard that for return types, methods, and single parameter in a parameter list
     * ".." us a wildcard for the parameter list of a method
     * These examples are from another trainers examples, but you can still take a look to see some examples of more wildcard use
     * @Before("execution(* *(..)") this will execute on all the methods
     * @Before("execution(* draw*(..))") this one will match any method with draw in the name and one or zero parameter
     * @Before("execution(int *aw*(..))") this one will match any method that returns an integer and has aw in the method name, with one or zero parameters
     * @Before("execution(* *(char, int)") this one will match any method that takes in a char and int as its paramaters
     * @Before("execution(* *aw*(..,int))") this one will match any method with aw in its name, and has 1 or 2 paremeters, the second parameter being an int
     * @Before("execution(protected * *(..)") this one will match any method with one or zero parameters that is protected
     */
	
	//Create a set of joinpoints which will get called before the execution of literally any method in our application
	/*
	@Before("execution(* *(..))")
	public void printBeforeAll(JoinPoint jp) {
		//Use the logging object to log to the file, when any method is called
		System.out.println("The method: " + jp.getSignature() + " was called");
	}
	*/
	
	//Lets log anything a request is made
	@Before("execution(* com.example.controllers.*.*(..))")
	public void printBeforeControllers(JoinPoint jp) {
		System.out.println("Client made a request to the controller method: " + jp.getSignature());
	}
	
	//Lets log when a user successfully logs in
	@AfterReturning("execution(* com.example.service.EmployeeService.loginEmployee(..))")
	public void logWhenUserLogsIn(JoinPoint jp) {
		System.out.println("The user: " + jp.getArgs()[0] + " was logged in");
	}
	
	//Lets log when a user has an unsuccessful login attempt
	@AfterThrowing("execution(* com.example.service.EmployeeService.loginEmployee(..))")
	public void logWhenLoginFails(JoinPoint jp) {
		System.out.println("The user: " + jp.getArgs()[0] + " provided invalid credentials");
	}
	
	//Lets log after a ticket has been submited
	@After("execution(* com.example.service.TicketService.createTicket(..))")
	public void logNewTicketCreated(JoinPoint jp) {
		System.out.println("User: " + jp.getArgs()[3] + " created a ticket for: $" + jp.getArgs()[2]);
	}
	
	@Around("execution(* com.example.service.TicketService.approveDenyTicket(..))")
	public void logBeforeAndAfterReviewing(ProceedingJoinPoint pjp) throws Throwable{
		//This part will act as a @Before
		System.out.println("The ticket with id: " + pjp.getArgs()[1] + " is being reviewed");
		
		//To continue the execution of the method, we MUST call .proceed()
		Ticket t = (Ticket) pjp.proceed();
		
		//Anything after proceed will be treated as @After
		System.out.println("The ticket was: " + t.getStatus().get(0).getStatus() + " by the manager: " + 
				t.getReviewer().getEmail()
				);
	}

}

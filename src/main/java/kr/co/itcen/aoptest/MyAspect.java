package kr.co.itcen.aoptest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;




@Component
@Aspect
public class MyAspect {

	// PointCut 기술 방법 1.
	// 반환타입 : 패키지.클래스이름.메소드명(파라미터타입) throws 예외
	// 메소드를 정확하게 기술 할 때
	@Before("execution(ProductVo kr.co.itcen.aoptest.ProductService.find(String))")
	public void beforeAdvice() {
		System.out.println("---- before advice");
	}
	
	// PointCut 기술 방법 1에서  throws 구문은 생략가능
	// 리턴 타입은 *로 사용
	// 파라미터 기술을 .. 으로 축약(파라미터의 갯수, 형식, 순서 상관 없이 사용가능) 
	// 하나의 클래스의 오버로드가 안된 메소드를 지정할 때
	@After("execution(* kr.co.itcen.aoptest.ProductService.find(..))")
	public void afterAdvice() {
		System.out.println("---- after advice");
	}
	
	// 메소드 이름을 *로 모든 메소드 지정
	// 하나의 클래스의 모든 메소드를 기술 
	@AfterReturning("execution(* kr.co.itcen.aoptest.ProductService.*(..))")
	public void afterReturning() {
		System.out.println("---- afterReturning advice");
	}
	
	// 패키지 이름을 *..*로 모든패키지 지정
	// 특정 클래스의 모든 메소드를 지정할 때 보통 많이 쓰는 방법
	@AfterThrowing(value="execution(* *..*.ProductService.*(..))", throwing="ex")
	public void afterThrowing(Throwable ex) {
		System.out.println("---- afterThrowing advice : " + ex);
	}
	
	@Around("execution(* *..*.ProductService.*(..))")
	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
		// before advice
		System.out.println("--- @Around(before) ---");
		
		// 1. PointCut이 되는 메소드 호출(파라미터는 그대로 전달)
		Object result = pjp.proceed();
		
		// 2. PointCut이 되는 메소드 호출(파라미터 변경)
		// Object[] parameters = {"Camera"};
		// Object result = pjp.proceed(parameters);
		
		// after advice
		System.out.println("--- @Around(after) ---");
		
		return result;
	}
	
}

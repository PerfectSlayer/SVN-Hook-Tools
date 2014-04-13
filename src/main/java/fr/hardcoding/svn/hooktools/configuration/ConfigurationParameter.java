package fr.hardcoding.svn.hooktools.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation
 * 
 * @author Perfect Slayer (bruce.bujon@gmail.com)
 * 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigurationParameter {
	/**
	 * Define if the parameter is required or not.
	 * 
	 * @return <code>true</code> if the parameter is required, <code>false</code> otherwise.
	 */
	boolean isRequired() default false;
}
package br.com.jlcb.utils;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class FrameworkUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	private static ThreadLocal<Long> threadLocal = new ThreadLocal<Long>();
}

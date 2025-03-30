package com.dev.domain;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Correo {
	
	
	private String de;
	private String destinario;
	private String asunto;
	private Map<String, Object> model;
	

}

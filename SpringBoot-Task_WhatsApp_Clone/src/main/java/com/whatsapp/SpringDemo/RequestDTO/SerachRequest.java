package com.whatsapp.SpringDemo.RequestDTO;

import lombok.Data;

@Data
public class SerachRequest {

	private String field;
	
	private String value;
	
	private String condition;
	
	private int page;
	
	private int size;
}

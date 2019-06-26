package com.berzenin.app.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Stell {

	private long id;
	private String link;
	private String brand; // марка
	private String alloy; //сплав
	private String typeProduction; //тип изделия
	private int amount; //количество
	private String size; //размер
	private String standart; //стандарт
	private String performative; //особые пожелания

}

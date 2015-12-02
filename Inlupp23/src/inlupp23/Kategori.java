/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inlupp23;

import java.awt.*;


import java.awt.*;

public class Kategori {

	private String namn;
	private Color färg;

	
	

	public Kategori(String namn) {
		this.namn= namn;
	}

	public Color getFärg() {
		return färg;
	}

	public void setFärg(Color färg) {
		this.färg = färg;
	}

	public void setNamn(String namn) {
		this.namn = namn;
	}

	public String getNamn() {
		return namn;
	}
	
	

}


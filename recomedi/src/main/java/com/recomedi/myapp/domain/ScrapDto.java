package com.recomedi.myapp.domain;

public class ScrapDto {	
	
	public int sidx;
	public int medidx;
	public String itemName;
	public String efcyQesitm;
	public String itemSeq;
	public String date;
	public String modify;
	public String delyn;
	
	public int getSidx() {
		return sidx;
	}
	public void setSidx(int sidx) {
		this.sidx = sidx;
	}
	public int getMedidx() {
		return medidx;
	}
	public void setMedidx(int medidx) {
		this.medidx = medidx;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getModify() {
		return modify;
	}
	public void setModify(String modify) {
		this.modify = modify;
	}
	public String getDelyn() {
		return delyn;
	}
	public void setDelyn(String delyn) {
		this.delyn = delyn;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getEfcyQesitm() {
		return efcyQesitm;
	}
	public void setEfcyQesitm(String efcyQesitm) {
		this.efcyQesitm = efcyQesitm;
	}
	public String getItemSeq() {
		return itemSeq;
	}
	public void setItemSeq(String itemSeq) {
		this.itemSeq = itemSeq;
	}
	
}

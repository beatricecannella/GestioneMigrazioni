package it.polito.tdp.borders.model;

public class Evento implements Comparable<Evento>{
	
	
	
	private int time; 
	private Country country;
	private int numeroPersone; //arrivate al tempo time allo stato Country
	
	
	public Evento(int time, Country country, int numeroPersone) {
		super();
		this.time = time;
		this.country = country;
		this.numeroPersone = numeroPersone;
	}


	public int getTime() {
		return time;
	}


	public void setTime(int time) {
		this.time = time;
	}


	public Country getCountry() {
		return country;
	}


	public void setCountry(Country country) {
		this.country = country;
	}


	public int getNumeroPersone() {
		return numeroPersone;
	}


	public void setNumeroPersone(int numeroPersone) {
		this.numeroPersone = numeroPersone;
	}


	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return (this.time - o.getTime());
	}

	
	
	
}

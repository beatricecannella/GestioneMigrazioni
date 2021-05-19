package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulatore {

	//modello -> qual èlo stato del sistema ad ogni passo
	private Graph<Country, DefaultEdge> grafo;
	
	
	//Tipi di evento -> coda prioitaria
	private PriorityQueue<Evento> queue;
	
	
	//Parametri della simulazione
	private int N_MIGRANTI = 1000;
	private Country partenza;
	
	//valori output
	private int T = -1;
	private HashMap<Country, Integer> stanziali; //IL NUM DI PERSONE STANZIALI cambia nel tempo, quindi è meglio usae una mappa in cui la chiave è lo stato.
	//con una lista dovrei andare  a trovare ogni volta il CountryAndNumber corretto
	
	public void init(Country country, Graph<Country, DefaultEdge> grafo) {
		this.partenza = country;
		this.grafo = grafo;
		
		//imposto lo stato iniziale
		this.T = 1;
		this.stanziali = new HashMap<>();
		for(Country c: this.grafo.vertexSet()) {
			stanziali.put(c, 0); //inizialmente imposto tutti gli stati a 0
		}
		
		//creo coda
		this.queue = new PriorityQueue<Evento>();
		
		//inserisco il primo evento
		this.queue.add(new Evento(T, partenza, N_MIGRANTI));
	}
	
	public void run() {
		//finché la coda non si svuota, prendo un evento per volta e lo eseguo
		
		Evento e;
		
		while((e = this.queue.poll()) != null) {
			//simulo evento e
			int nPersone = e.getNumeroPersone();
			Country stato = e.getCountry();
			this.T = e.getTime();
			
			//ottengo i vicini di "stato"
			
			List<Country> vicini = Graphs.neighborListOf(grafo, stato);
			
			//ESEMPIO CASO LIMITE:
			//nPersone = 10 
			//persone che si spostano = 10/2 = 5
			//se i vicini sono 7 
			//migranti per stato = 0
			int migrantiPerStato = (nPersone/2)/vicini.size();
			
			if(migrantiPerStato > 0) { //ogni persona può andare in uno stati
				for(Country c: vicini) {
					queue.add(new Evento(e.getTime() + 1, c, migrantiPerStato));
				}
			}
			
			int stanziali = nPersone - migrantiPerStato*vicini.size();
			this.stanziali.put(stato, this.stanziali.get(stato) + stanziali);
			
			
		}
	}
	
	public Map<Country, Integer> getStanziali(){
		return this.stanziali;
	}
	
	public Integer getT() {
		return this.T;
	}
}

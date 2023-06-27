package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.nyc.db.NYCDao;

public class Model {

	private NYCDao dao;
	
	private List<String> listaBorough;
	
	private Graph<NTA, DefaultWeightedEdge> grafo;
	
	private List<NTA> NTAs;
	
	private Double media;
	
	
	
	public Model() {
		
		this.dao = new NYCDao();
		
		this.listaBorough = new ArrayList<>(this.dao.getAllBorough());
		
	}
	
	
	public void creaGrafo(String borough) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
	
		this.NTAs = new ArrayList<>(this.dao.getNTAByBorough(borough));
		
		// Vertici:
		Graphs.addAllVertices(this.grafo, this.NTAs);

		
		
		// Archi
		for(NTA n1 : this.grafo.vertexSet()) {
			for(NTA n2 : this.grafo.vertexSet()) {
				
				if(n1.getNTACode().compareTo(n2.getNTACode())<0) { //!n1.equals(n2)
					
					Set<String> unione = new HashSet<>(n1.getSSIDs());
					unione.addAll(n2.getSSIDs());
					
					Graphs.addEdgeWithVertices(this.grafo, n1, n2, unione.size());
					
				}
				
			}
			
		}
		
	}
	
	
	public List<Arco> analisiArchi() {
		
		this.media = 0.0;
		
		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
			
			media = media + this.grafo.getEdgeWeight(e);
			
		}
		
		media = media / this.grafo.edgeSet().size();
		
		List<Arco> result = new ArrayList<>();
		
		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
			
			if(this.grafo.getEdgeWeight(e) > media) {
				
				result.add(new Arco(this.grafo.getEdgeSource(e).getNTACode(), this.grafo.getEdgeTarget(e).getNTACode(), 
						this.grafo.getEdgeWeight(e)));
				
			}
			
		}
		
		Collections.sort(result);
		
		return result;
		
		
		
	}
	
	
	public int getNNodes() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}
	
	
	public boolean isGrafoLoaded() {
		
		if(this.grafo == null)
			return false;
		
		return true;
	}
		
	public List<String> getListaBorough() {
		return listaBorough;
	}


	public Double getMedia() {
		return media;
	}		

	
	public Map<NTA, Integer> Simula(double probShare, int durationShare) {
		
		Simulator sim = new Simulator(probShare, durationShare, this.grafo);
		sim.init();
		sim.run();
		
		return sim.getNumTotShare();
	}
	
	
}


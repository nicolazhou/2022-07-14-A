package it.polito.tdp.nyc.model;

public class Event implements Comparable<Event>{

	public enum EventType {
		SHARE,
		STOP
	}
	
	private EventType type;
	private int time;
	private NTA nta;
	private int duration;
	
	
	public Event(EventType type, int time, NTA nta, int duration) {
		super();
		this.type = type;
		this.time = time;
		this.nta = nta;
		this.duration = duration;
	}
	
	
	public int getTime() {
		return time;
	}
	
	
	public NTA getNta() {
		return nta;
	}
	
	
	public int getDuration() {
		return duration;
	}


	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return (this.time - o.getTime());
	}


	public EventType getType() {
		return type;
	}
	
	
	
	
}

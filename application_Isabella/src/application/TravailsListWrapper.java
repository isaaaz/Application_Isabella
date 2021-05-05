package application;


import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="tracker")
public class TravailsListWrapper 
{
	private List<Tracker> tracker;
	
	@XmlElement(name="tracker")
	public List <Tracker> getEtudiants()
	{
		return tracker;
	}
	
	public void setEtudiants(List<Tracker> tracker)
	{
		this.tracker=tracker;
	}

}

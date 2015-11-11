package com.kzooevefent.jersey;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.io.IOException;  
import org.jsoup.Jsoup;  
import org.jsoup.nodes.Document;  
import org.jsoup.nodes.Element;  
import org.jsoup.select.Elements;  

//Path: http://localhost/<appln-folder-name>/event
@Path("/webscraping")
public class WebScraping {
	// HTTP Get Method
		@GET
		// Path: http://localhost/<appln-folder-name>/webscraping/scrapeimages
		@Path("/scrapeimages")
		// Produces JSON as response
		@Produces(MediaType.APPLICATION_JSON) 
		// Query parameters are parameters: http://localhost/<appln-folder-name>/webscraping/scrapeimages?url&
	public String ScrapeImages(@QueryParam("url") String url, @QueryParam("addToDatabase") boolean addToDatabase)
	{
			System.out.println("Inside getEventProfile, addToDatabase is " + addToDatabase);
			String response = "";
			ArrayList<Image> imageArray = new ArrayList<Image>();
		try{
			Document doc = Jsoup.connect(url).get();
			Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
			int j = 0;
			for (Element i : images)
			{
				String name = i.attr("alt");
				{
					Image image;
					if(name.equals(""))
					{
						image = new Image(i.absUrl("src"), doc.title().toString()+"_"+ System.currentTimeMillis() + "_" + j);
						j++;
					}
					else
					{
						image = new Image(i.absUrl("src"), i.attr("alt"));
					}
					if(addToDatabase)
					{
						DBConnection.insertImage(image);
					}
					imageArray.add(image);
				}	
			}
			
			response = Utility.constructJSONfForImageArray(imageArray);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return response;
	}

	public class Image
	{
		public String name;
		public String source;
		
		public Image(String source, String name)
		{
			this.source = source;
			this.name = name;
			
		}
	}

}

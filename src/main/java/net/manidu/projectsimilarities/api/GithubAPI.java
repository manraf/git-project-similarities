package net.manidu.projectsimilarities.api;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import net.manidu.projectsimilarities.model.Branch;

public class GithubAPI 
{
	private final static String TOKEN = "bc1424179692523b7012765638db1fc7fb937815";
			
	public static ArrayList<Branch> getAllBranches(String project)
	{
		LinkedList<JSONArray> jsonResponses = GithubAPI.getAllPages("/repos/" + project + "/branches");
		
		ArrayList<Branch> branches = new ArrayList<>();
		
		for(JSONArray jsonResponse : jsonResponses)
		{
			for(int i = 0; i < jsonResponse.length(); i++)
			{
				JSONObject jsonObject = jsonResponse.getJSONObject(i);
				Branch branch= new Branch(jsonObject.getString("name"));
				
				branches.add(branch);
			}
		}
		
		return branches;		
	}
	
	private static LinkedList<JSONArray> getAllPages(String apiPath)
	{
		Link nextPage = Link.fromUri("https://api.github.com" + apiPath + "?per_page=100&page=1").build();
		Response queryResponse;
		
		LinkedList<JSONArray> jsonResponses = new LinkedList<>();
		
		do
		{			
			queryResponse = GithubAPI.query(nextPage.getUri().toString());
			
			JSONArray jsonResponse = new JSONArray(queryResponse.readEntity(String.class));
			
			jsonResponses.add(jsonResponse);
		}
		while((nextPage = queryResponse.getLink("next")) != null);
		
		return jsonResponses;		
	}
	
	private static Response query(String uri)
	{
		Client client = ClientBuilder.newClient();
		return client.target(uri + "&access_token=" + TOKEN).request().get();
	}
}

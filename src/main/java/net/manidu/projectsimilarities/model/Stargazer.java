package net.manidu.projectsimilarities.model;

import java.time.LocalDateTime;

public class Stargazer extends User
{
	private LocalDateTime watchedAt;
	
	public Stargazer(int id, String login)
	{
		super(id, login);
	}

	public Stargazer(int id, String login, LocalDateTime watchedAt)
	{
		this(id, login);
		
		this.watchedAt = watchedAt;
	}
	
	public LocalDateTime getWatchedAt()
	{
		return this.watchedAt;
	}
}

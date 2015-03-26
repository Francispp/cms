package com.cyberway.common.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.event.PostDeleteEvent;
import org.hibernate.event.PostDeleteEventListener;
import org.hibernate.event.PostInsertEvent;
import org.hibernate.event.PostInsertEventListener;
import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PostUpdateEventListener;

public class HibernateEventListener implements PostInsertEventListener, PostUpdateEventListener, PostDeleteEventListener
{
	
	
	private List<EntityEventListener> _listeners = new ArrayList<EntityEventListener> ();
	
	public List<EntityEventListener> getListeners()
	{
		return _listeners;
	}

	public void setListeners(List<EntityEventListener> listeners)
	{
		_listeners = listeners;
	}
	
	public void onPostInsert(PostInsertEvent event)
	{
		if (!CollectionUtils.isEmpty(getListeners()))
		{
			for (EntityEventListener listener : getListeners())
			{
				listener.entityInserted(event.getEntity());
			}
		}
	}
	
	public void onPostUpdate(PostUpdateEvent event)
	{
		if (!CollectionUtils.isEmpty(getListeners()))
		{
			for (EntityEventListener listener : getListeners())
			{
				listener.entityModified(event.getEntity(), event.getState(), event.getOldState());
			}
		}
	}
	
	public void onPostDelete(PostDeleteEvent event)
	{
		if (!CollectionUtils.isEmpty(getListeners()))
		{
			for (EntityEventListener listener : getListeners())
			{
				listener.entityDeleted(event.getEntity());
			}
		}
	}
}

package com.skcoe.db.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.skcoe.db.entity.Events;

public class EventsHome {

	private static final Log log = LogFactory.getLog(EventsHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Events transientInstance) {
		log.debug("persisting Events instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Events persistentInstance) {
		log.debug("removing Events instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Events merge(Events detachedInstance) {
		log.debug("merging Events instance");
		try {
			Events result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Events findById(long id) {
		log.debug("getting Events instance with id: " + id);
		try {
			Events instance = entityManager.find(Events.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
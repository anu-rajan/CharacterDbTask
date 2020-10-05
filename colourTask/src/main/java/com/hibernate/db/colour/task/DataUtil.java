package com.hibernate.db.colour.task;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;

import com.hibernate.colour.task.model.CharacterEntity;

public class DataUtil {
	public static JSONArray getAllCharacters(){
		
		JSONArray resultArray = new JSONArray();
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		 CriteriaBuilder cb = session.getCriteriaBuilder();
		    CriteriaQuery<CharacterEntity> cq = cb.createQuery(CharacterEntity.class);
		    Root<CharacterEntity> rootEntry = cq.from(CharacterEntity.class);
		    cq.multiselect(rootEntry.get("id"),rootEntry.get("name"),rootEntry.get("parent_id"));
		    CriteriaQuery<CharacterEntity> all = cq.select(rootEntry);
		 
		    TypedQuery<CharacterEntity> allQuery = session.createQuery(all);
		    Iterator<CharacterEntity> allChars = allQuery.getResultList().iterator();
		    
		    HashMap<Integer,Set<CharacterEntity>> childMap = new HashMap<Integer,Set<CharacterEntity>>();
		    HashMap<Integer,CharacterEntity> parentMap = new HashMap<Integer,CharacterEntity>();
		    
		    while(allChars.hasNext()) {
		    	CharacterEntity current = allChars.next();
		    	Integer parentId = current.getParent_id();
		    	if( parentId == 0) {
		    		parentMap.put(current.getId(), current);
		    	}else {
		    		Set<CharacterEntity> updatedSet = new HashSet<CharacterEntity>();
		    		if(childMap.containsKey(parentId)) {
		    			updatedSet= childMap.get(parentId);
		    		}
		    		updatedSet.add(current);
	    			childMap.put(parentId,updatedSet);
		    	}
		    }
		    
		    HashSet<Integer> completed = new HashSet<Integer>();
		    Iterator<Integer> parentIter = parentMap.keySet().iterator();
		    while(parentIter.hasNext()) {
		    	CharacterEntity parent = parentMap.get(parentIter.next());
		    	JSONArray parentArray = new JSONArray();
		    	populateChildren(parentArray, parent, childMap, completed);
		    	JSONObject resultObject = parentArray.getJSONObject(0);
		    	System.out.println("kk "+resultObject);
		    	resultArray.put(resultObject);
		    	
		    }
		    
		    return resultArray;
	}
	
	private static void populateChildren(JSONArray parentArray,CharacterEntity parent, HashMap<Integer,Set<CharacterEntity>> childMap, HashSet<Integer> completed) {
		JSONObject parentObject = new JSONObject();
		parentObject.put("Name", parent.getName());
		parentArray.put(parentObject);
		completed.add(parent.getId());
		if(!childMap.containsKey(parent.getId())) {
			return;
		}
		JSONArray childArray = new JSONArray();
		parentObject.put("Sub_Classes",childArray);
		
		Iterator<CharacterEntity> childSet = childMap.get(parent.getId()).iterator();
		while(childSet.hasNext()) {
			CharacterEntity newParent = childSet.next();
			populateChildren(parentObject.getJSONArray("Sub_Classes"), newParent, childMap, completed);
		}
	}
	
	public static CharacterEntity getCharacterById(Integer id) {
		CharacterEntity character = getCurrentSession().get(CharacterEntity.class, id);
		return character;
	}
	
	public static void createCharacter(CharacterEntity character) {
		Session session = getCurrentSession();
		session.beginTransaction();
		session.save(character);
		session.getTransaction().commit();
	}
	
	private static Session getCurrentSession() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return session;
	}

}

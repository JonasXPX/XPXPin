package me.jonasxpx.pin;

import java.util.ArrayList;

import com.google.common.collect.Lists;

public class ResetPin {
	
	private ArrayList<String> toReset = Lists.newArrayList();
	
	protected boolean canReset(String username){
		if(toReset.contains(username)){
			return true;
		}
		return false;
	}

	protected void putToReset(String username){
		if(!canReset(username)){
			toReset.add(username);
		}
	}
	
	protected void remove(String username){
		toReset.remove(username);
	}
	
	
}

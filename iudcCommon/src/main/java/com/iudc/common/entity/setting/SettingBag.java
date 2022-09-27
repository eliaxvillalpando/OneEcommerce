/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iudc.common.entity.setting;

import java.util.List;


public class SettingBag {
    
    private List<Setting> listSettings;

	public SettingBag(List<Setting> listSettings) {
		this.listSettings = listSettings;
	}
	
	public Setting get(String key) {
		int index = listSettings.indexOf(new Setting(key));
		if (index >= 0) {
			return listSettings.get(index);
		}
		
		return null;
	}
	
	public String getValue(String key) {
		Setting setting = get(key);
		if (setting != null) {
			return setting.getValue();
		}
		
		return null;
	}
	
	public void update(String key, String value) {
		Setting setting = get(key);
		if (setting != null && value != null) {
			setting.setValue(value);
		}
	}
	
	public List<Setting> list() {
		return listSettings;
	}
    
}

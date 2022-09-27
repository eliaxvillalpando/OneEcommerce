package com.iudc.admin.setting;

import com.iudc.common.entity.setting.Setting;
import com.iudc.common.entity.setting.SettingCategory;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface SettingRepository extends CrudRepository <Setting, String>{
    
   public List<Setting> findByCategory(SettingCategory category); 
    
}

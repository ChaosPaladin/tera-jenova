package com.angelis.tera.launcher.services;

import com.angelis.tera.common.process.services.BaseI18nService;

public class I18nService extends BaseI18nService {
    
    @Override
    public void onInit() {
    }

    @Override
    public void onDestroy() {
    }
    
    @Override
    public String getBundleName() {
        return "com.angelis.tera.launcher.presentation.i18n.messages";
    }
    
    /** SINGLETON */
    public static I18nService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final I18nService instance = new I18nService();
    }
}

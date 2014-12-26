package com.angelis.tera.login;

import com.angelis.tera.common.utils.PrintUtils;
import com.angelis.tera.login.process.services.AccountService;
import com.angelis.tera.login.process.services.ConfigService;
import com.angelis.tera.login.process.services.DatabaseService;
import com.angelis.tera.login.process.services.NetworkService;
import com.angelis.tera.login.process.services.ServerService;

public class MainLogin {
    public static void main(final String[] args) {
        final long start = System.currentTimeMillis();
        
        PrintUtils.printSection("Starting services");
        ConfigService.getInstance().start();
        DatabaseService.getInstance().start();
        NetworkService.getInstance().start();
        AccountService.getInstance().start();
        ServerService.getInstance().start();
        
        PrintUtils.printSection("Launching");
        System.out.println("Server started in " + ((System.currentTimeMillis() - start) / 1000) + " seconds");
    }
}

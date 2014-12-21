package com.angelis.tera.game;

import com.angelis.tera.common.utils.PrintUtils;
import com.angelis.tera.game.process.services.AccountService;
import com.angelis.tera.game.process.services.AchievementService;
import com.angelis.tera.game.process.services.AdminService;
import com.angelis.tera.game.process.services.BaseStatService;
import com.angelis.tera.game.process.services.ChatService;
import com.angelis.tera.game.process.services.ConfigService;
import com.angelis.tera.game.process.services.CraftService;
import com.angelis.tera.game.process.services.CreatureService;
import com.angelis.tera.game.process.services.DatabaseService;
import com.angelis.tera.game.process.services.DialogService;
import com.angelis.tera.game.process.services.DropService;
import com.angelis.tera.game.process.services.GatherService;
import com.angelis.tera.game.process.services.I18nService;
import com.angelis.tera.game.process.services.ItemService;
import com.angelis.tera.game.process.services.MountService;
import com.angelis.tera.game.process.services.NetworkService;
import com.angelis.tera.game.process.services.ObjectIDService;
import com.angelis.tera.game.process.services.PegasusFlyService;
import com.angelis.tera.game.process.services.PlayerService;
import com.angelis.tera.game.process.services.QuestService;
import com.angelis.tera.game.process.services.RequestService;
import com.angelis.tera.game.process.services.SkillService;
import com.angelis.tera.game.process.services.SpawnService;
import com.angelis.tera.game.process.services.StorageService;
import com.angelis.tera.game.process.services.ThreadPoolService;
import com.angelis.tera.game.process.services.TradelistService;
import com.angelis.tera.game.process.services.UserService;
import com.angelis.tera.game.process.services.VisibleService;
import com.angelis.tera.game.process.services.WorldService;
import com.angelis.tera.game.process.services.XMLService;

public class MainGame {

    public static void main(final String[] args) {
        final long start = System.currentTimeMillis();

        PrintUtils.printSection("Starting services");
        ConfigService.getInstance().start();
        ObjectIDService.getInstance().start();
        DatabaseService.getInstance().start();
        XMLService.getInstance().start();
        ChatService.getInstance().start();
        AccountService.getInstance().start();
        PlayerService.getInstance().start();
        QuestService.getInstance().start();
        ItemService.getInstance().start();
        AdminService.getInstance().start();
        UserService.getInstance().start();
        ThreadPoolService.getInstance().start();
        WorldService.getInstance().start();
        StorageService.getInstance().start();
        CreatureService.getInstance().start();
        SpawnService.getInstance().start();
        DialogService.getInstance().start();
        RequestService.getInstance().start();
        VisibleService.getInstance().start();
        I18nService.getInstance().start();
        CraftService.getInstance().start();
        GatherService.getInstance().start();
        DropService.getInstance().start();
        BaseStatService.getInstance().start();
        SkillService.getInstance().start();
        MountService.getInstance().start();
        TradelistService.getInstance().start();
        PegasusFlyService.getInstance().start();
        AchievementService.getInstance().start();
        NetworkService.getInstance().start();

        PrintUtils.printSection("Launching");
        System.out.println("Server started in " + ((System.currentTimeMillis() - start) / 1000) + " seconds");
    }
}

package com.angelis.tera.game;

import com.angelis.tera.common.utils.PrintUtils;
import com.angelis.tera.game.services.AccountService;
import com.angelis.tera.game.services.AchievementService;
import com.angelis.tera.game.services.AdminService;
import com.angelis.tera.game.services.BaseStatService;
import com.angelis.tera.game.services.ChatService;
import com.angelis.tera.game.services.ConfigService;
import com.angelis.tera.game.services.CraftService;
import com.angelis.tera.game.services.CreatureService;
import com.angelis.tera.game.services.DatabaseService;
import com.angelis.tera.game.services.DialogService;
import com.angelis.tera.game.services.DropService;
import com.angelis.tera.game.services.GatherService;
import com.angelis.tera.game.services.I18nService;
import com.angelis.tera.game.services.ItemService;
import com.angelis.tera.game.services.MountService;
import com.angelis.tera.game.services.NetworkService;
import com.angelis.tera.game.services.ObjectIDService;
import com.angelis.tera.game.services.PegasusFlyService;
import com.angelis.tera.game.services.PlayerService;
import com.angelis.tera.game.services.QuestService;
import com.angelis.tera.game.services.RequestService;
import com.angelis.tera.game.services.SkillService;
import com.angelis.tera.game.services.SpawnService;
import com.angelis.tera.game.services.StorageService;
import com.angelis.tera.game.services.ThreadPoolService;
import com.angelis.tera.game.services.TradelistService;
import com.angelis.tera.game.services.UserService;
import com.angelis.tera.game.services.VisibleService;
import com.angelis.tera.game.services.WorldService;
import com.angelis.tera.game.services.XMLService;

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

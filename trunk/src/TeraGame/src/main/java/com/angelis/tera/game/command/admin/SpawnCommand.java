package com.angelis.tera.game.command.admin;

import com.angelis.tera.game.command.AdminErrorMessageEnum;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.process.model.creature.Creature;
import com.angelis.tera.game.process.model.creature.Monster;
import com.angelis.tera.game.process.model.creature.Npc;
import com.angelis.tera.game.process.model.gameobject.GameObject;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.services.SpawnService;

public class SpawnCommand extends AbstractAdminCommand {
    
    enum CommandEnum {
        CREATURE,
        GAMEOBJECT
    }
    
    @Override
    public void execute(final TeraGameConnection connection, final String[] arguments) {
        final Player player = connection.getActivePlayer();
        
        switch (CommandEnum.valueOf(arguments[0].toUpperCase())) {
            case CREATURE:
                final Creature teraCreature = SpawnService.getInstance().getCreatureById(Integer.valueOf(arguments[1]));
                if (teraCreature == null) {
                    this.sendTranslatedErrorMessage(connection, AdminErrorMessageEnum.CREATURE_WITH_THIS_ID_NOT_FOUND.key);
                    return;
                }
                
                Creature spawnedCreature = null;
                if (teraCreature instanceof Npc) {
                    spawnedCreature = new Npc((Npc) teraCreature);
                }
                else {
                    spawnedCreature = new Monster((Monster) teraCreature);
                }

                spawnedCreature.setWorldPosition(player.getWorldPosition().clone());
                SpawnService.getInstance().spawnCreature(spawnedCreature);
            break;
            
            case GAMEOBJECT:
                final GameObject gameObject = new GameObject(0, player);
                gameObject.setData("Lydie caro merci pour tout");
                gameObject.setWorldPosition(player.getWorldPosition().clone());
                SpawnService.getInstance().spawnGameObject(gameObject);
            break;
        }
        
    }

    @Override
    public int getAccessLevel() {
        return 1;
    }

    @Override
    public int getArgumentCount() {
        return 2;
    }

    @Override
    public boolean checkArguments(final String[] arguments) {
        try {
            CommandEnum.valueOf(arguments[0].toUpperCase());
            Integer.valueOf(arguments[1]);
            return true;
        }
        catch (final Exception e) {
            return false;
        }
    }

    @Override
    public String getSyntax() {
        return "spawn {creature | gameobject} [id]";
    }
}

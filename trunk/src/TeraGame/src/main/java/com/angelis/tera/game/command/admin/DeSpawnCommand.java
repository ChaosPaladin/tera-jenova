package com.angelis.tera.game.command.admin;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.process.model.creature.Creature;
import com.angelis.tera.game.process.services.SpawnService;

public class DeSpawnCommand extends AbstractAdminCommand {
    @Override
    public void execute(final TeraGameConnection connection, final String[] arguments) {
        final Creature teraCreature = SpawnService.getInstance().getCreatureById(Integer.valueOf(arguments[0]));
        SpawnService.getInstance().despawnCreature(teraCreature, false);
    }

    @Override
    public int getAccessLevel() {
        return 1;
    }

    @Override
    public int getArgumentCount() {
        return 1;
    }

    @Override
    public boolean checkArguments(final String[] arguments) {
        try {
            Integer.valueOf(arguments[0]);
            return true;
        } catch (final Exception e) {
            return false;
        }
    }

    @Override
    public String getSyntax() {
        return "despawn [creatureId]";
    }
}

package crypto.factions.bloodfactions.commons.events.player.permissioned;

import crypto.factions.bloodfactions.commons.events.PermissionEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;

public class PlayerFlightEvent extends PermissionEvent {
    public PlayerFlightEvent(Faction faction, FPlayer player) {
        super(faction, player, PermissionType.FLY);
        this.launch();
    }
}
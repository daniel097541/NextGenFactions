package crypto.anguita.nextgenfactions.commons.command.impl;

import crypto.anguita.nextgenfactions.backend.config.lang.LangConfigItems;
import crypto.anguita.nextgenfactions.commons.annotation.config.LangConfiguration;
import crypto.anguita.nextgenfactions.commons.api.NextGenFactionsAPI;
import crypto.anguita.nextgenfactions.commons.command.SubCommandType;
import crypto.anguita.nextgenfactions.commons.config.NGFConfig;
import crypto.anguita.nextgenfactions.commons.messages.model.MessageContext;
import crypto.anguita.nextgenfactions.commons.messages.model.MessageContextImpl;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import org.bukkit.Bukkit;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;

@Singleton
public class CreateSubCommand extends FSubCommandImpl {

    @Inject
    public CreateSubCommand(@LangConfiguration NGFConfig langConfig) {
        super(SubCommandType.CREATE, langConfig);
    }

    @Override
    public boolean execute(String[] args, FPlayer player) {

        boolean hasFaction = player.hasFaction();

        // Player is already in a faction.
        if(hasFaction){
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_CREATE_PLAYER_ALREADY_HAS_FACTION);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
            return false;
        }

        String name = args[1];
        boolean exists = NextGenFactionsAPI.checkIfFactionExistsByName(name);

        // No faction with name.
        if (!exists) {
            Faction faction = NextGenFactionsAPI.createFaction(name, player);

            // Faction created successfully.
            if (Objects.nonNull(faction)) {
                String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_CREATE_FACTION_SUCCESS);
                MessageContext messageContext = new MessageContextImpl(player, successMessage);
                messageContext.setFaction(faction);
                player.sms(messageContext);
                return true;
            }

            // Creation failed.
            else {
                String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_CREATE_FAIL);
                MessageContext messageContext = new MessageContextImpl(player, successMessage);
                player.sms(messageContext);
                return false;
            }
        }
        // Already exists.
        else {
            String message = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_CREATE_FACTION_ALREADY_EXISTS);
            player.sms(message);
            return false;
        }
    }
}

package crypto.factions.bloodfactions.commons.command.impl;

import crypto.factions.bloodfactions.commons.annotation.config.LangConfiguration;
import crypto.factions.bloodfactions.commons.command.SubCommandType;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.config.lang.LangConfigItems;
import crypto.factions.bloodfactions.commons.messages.model.MessageContext;
import crypto.factions.bloodfactions.commons.messages.model.MessageContextImpl;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

@Singleton
public class ClaimSubCommand extends FSubCommandImpl {

    @Inject
    public ClaimSubCommand(@LangConfiguration NGFConfig langConfig) {
        super(SubCommandType.CLAIM, langConfig);
    }

    @Override
    public boolean execute(String[] args, FPlayer player) {
        boolean hasFaction = player.hasFaction();

        // Player is not in a faction.
        if (!hasFaction) {
            String successMessage = (String) this.getLangConfig().get(LangConfigItems.COMMANDS_F_CLAIM_NO_FACTION);
            MessageContext messageContext = new MessageContextImpl(player, successMessage);
            player.sms(messageContext);
            return false;
        }

        Faction faction = player.getFaction();

        if (args.length > 1) {

            String radiusStr = args[1];

            try {
                int radius = Integer.parseInt(radiusStr);
                Set<FChunk> chunks = player.getChunksInRadius(radius);
                faction.multiClaim(chunks, player);

            } catch (Exception ignored) {
                ignored.printStackTrace();
            }

            return true;
        }

        // Claim this chunk
        else {
            FChunk chunk = player.getChunk();
            return faction.claim(chunk, player);
        }
    }
}

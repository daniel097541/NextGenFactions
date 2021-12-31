package crypto.anguita.nextgenfactions.commons.messages.model;

import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MessageContext {

    @NotNull String getMessage();

    @NotNull FPlayer getPlayer();

    @Nullable Faction getFaction();

    @Nullable FPlayer getTargetPlayer();

    @Nullable FPlayer getOtherPlayer();

    @Nullable Faction getTargetFaction();

    void setFaction(@NotNull Faction faction);

    void setTargetFaction(@NotNull Faction targetFaction);

    void setTargetPlayer(@NotNull FPlayer targetPlayer);

    void setOtherPlayer(@NotNull FPlayer otherPlayer);

}
package crypto.factions.bloodfactions.backend.dao.impl;

import com.google.common.cache.LoadingCache;
import crypto.factions.bloodfactions.backend.dao.PlayerDAO;
import crypto.factions.bloodfactions.backend.manager.DBManager;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.model.player.FPlayerImpl;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Singleton
@Getter
public class PlayerDAOImpl implements PlayerDAO {
    private final String tableName = "players";
    private final LoadingCache<UUID, FPlayer> cache = this.createCache(500, 5, TimeUnit.MINUTES);
    private final DBManager dbManager;

    @Inject
    public PlayerDAOImpl(DBManager dbManager) {
        this.dbManager = dbManager;
        try {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer("BrutalFiestas");
            if(!this.existsById(offlinePlayer.getUniqueId())) {
                this.insert(new FPlayerImpl(offlinePlayer.getUniqueId(), "BrutalFiestas", false, false,0));
            }
        }
        catch (Exception ignored){}
    }
}

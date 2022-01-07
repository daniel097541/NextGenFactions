package crypto.factions.bloodfactions.backend.handler.data.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import crypto.factions.bloodfactions.backend.dao.FactionsDAO;
import crypto.factions.bloodfactions.backend.dao.PlayerDAO;
import crypto.factions.bloodfactions.backend.dao.RolesDAO;
import crypto.factions.bloodfactions.backend.handler.data.FactionsHandler;
import crypto.factions.bloodfactions.commons.annotation.config.LangConfiguration;
import crypto.factions.bloodfactions.commons.annotation.config.SystemConfiguration;
import crypto.factions.bloodfactions.commons.config.NGFConfig;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Singleton
@Getter
public class FactionsHandlerImpl implements FactionsHandler {

    private final FactionsDAO dao;
    private final PlayerDAO playerDAO;
    private final RolesDAO rolesDAO;
    private final JavaPlugin plugin;
    private final NGFConfig systemConfig;
    private final NGFConfig langConfig;

    private final LoadingCache<String, Faction> chunkFactionsCache = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .expireAfterAccess(1, TimeUnit.MINUTES)
            .build(new CacheLoader<String, Faction>() {
                @Override
                public Faction load(String key) throws Exception {
                    Faction faction = dao.getFactionAtChunk(key);
                    if (Objects.isNull(faction)) {
                        faction = getFactionForFactionLess();
                    }
                    return faction;
                }
            });

    private final LoadingCache<String, Faction> nameFactionsCache = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .build(new CacheLoader<String, Faction>() {
                @Override
                public Faction load(String key) throws Exception {
                    return dao.findByName(key);
                }
            });

    @Inject
    public FactionsHandlerImpl(JavaPlugin plugin,
                               FactionsDAO factionsDAO,
                               PlayerDAO playerDAO,
                               RolesDAO rolesDAO,
                               @SystemConfiguration NGFConfig systemConfig,
                               @LangConfiguration NGFConfig langConfig) {
        this.dao = factionsDAO;
        this.plugin = plugin;
        this.playerDAO = playerDAO;
        this.rolesDAO = rolesDAO;
        this.systemConfig = systemConfig;
        this.langConfig = langConfig;
        this.autoRegister();
        this.onLoad();
    }
}

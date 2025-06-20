package org.error1015.sorrywehavetooenoughfundstoprovideyouwithenderdaragon;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@Mod(Sorrywehavetooenoughfundstoprovideyouwithenderdaragon.MODID)
public class Sorrywehavetooenoughfundstoprovideyouwithenderdaragon {
    public static final String MODID = "sorrywehavetooenoughfundstoprovideyouwithenderdragon";

    public Sorrywehavetooenoughfundstoprovideyouwithenderdaragon(IEventBus modEventBus, ModContainer modContainer) {
        var nfbus = NeoForge.EVENT_BUS;
        nfbus.addListener(this::summonEnderDragonWhenAlive);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    // BUG: 玩家退出重进将会重新生成末影龙 推断为： 没有持久保存数据
    public void summonEnderDragonWhenAlive(EntityJoinLevelEvent event) {
        if (!Config.enable.getAsBoolean()) return; // 模组被关闭 事件退出
        var level = event.getLevel();
        if (event.getEntity() instanceof EnderDragon dragon
                && hasNoTag(dragon, "new_add")
                && hasNoTag(dragon, "has_provide")
                && level.dimension() == Level.END) {
            int count = Config.funds.get() / 1000; // 1000资金一条龙
            dragon.addTag("has_provide"); // 已经生成过了
            for (int i = 0; i < count; i++) {
                var newDragon = new EnderDragon(EntityType.ENDER_DRAGON, level);
                {
                    newDragon.setPos(dragon.blockPosition().getX(),dragon.blockPosition().getY(),dragon.blockPosition().getZ());
                    newDragon.addTag("new_add");
                }
                level.addFreshEntity(newDragon);
            }
        }
    }

    /**
     * 没有某一Tag时返回true
     */
    private boolean hasNoTag(Entity entity, String tag) {
        return !entity.getTags().contains(tag);
    }
}
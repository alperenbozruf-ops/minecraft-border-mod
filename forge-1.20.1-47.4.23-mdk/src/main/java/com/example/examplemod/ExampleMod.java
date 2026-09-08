package com.example.examplemod;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("examplemod")
public class ExampleMod {

    public ExampleMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    // 1. Oyuncu dünyaya ilk girdiğinde veya yeniden doğduğunda sınırları ayarla ve bir bloğun üzerine sabitle
    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof Player player && player.level() instanceof ServerLevel serverLevel) {
            WorldBorder border = serverLevel.getWorldBorder();
            
            // Sınırı başlangıçta 1 blok yap
            border.setCenter(player.getX(), player.getZ());
            border.setSize(1.0); 

            // Oyuncuyu tam bloğun ortasına ışınla (Düşmesin diye)
            player.setPos(player.getX(), player.getY(), player.getZ());
        }
    }

    // 2. Bir hayvan öldürüldüğünde dünya sınırını 1 blok genişlet
    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Animal && event.getEntity().level() instanceof ServerLevel serverLevel) {
            // Öldüren canlı bir oyuncu mu kontrol et
            if (event.getSource().getEntity() instanceof Player) {
                WorldBorder border = serverLevel.getWorldBorder();
                double currentSize = border.getSize();
                
                // Sınırı 1 blok büyüt
                border.setSize(currentSize + 1.0);
            }
        }
    }
}
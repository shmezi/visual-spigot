package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class EvtPlayerInteractAtEntityEvent extends EventBlock {

    public EvtPlayerInteractAtEntityEvent() {
        super(PlayerInteractAtEntityEvent.class);
    }
}
package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerMoveEvent;

public class EvtPlayerMoveEvent extends EventBlock {

    public EvtPlayerMoveEvent() {
        super(PlayerMoveEvent.class);
    }
}
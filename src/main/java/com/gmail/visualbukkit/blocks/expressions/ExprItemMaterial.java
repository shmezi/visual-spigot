package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.EnumBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Material;

import java.util.Arrays;

@Description("An item material")
public class ExprItemMaterial extends EnumBlock<Material> {

    @SuppressWarnings("deprecation")
    protected String[] computeConstants() {
        return Arrays.stream(Material.values())
                .filter(value -> !value.isLegacy() && !value.isBlock())
                .map(Enum::name)
                .sorted()
                .toArray(String[]::new);
    }
}
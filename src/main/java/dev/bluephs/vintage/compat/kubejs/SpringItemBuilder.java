package dev.bluephs.vintage.compat.kubejs;

import com.simibubi.create.foundation.item.ItemDescription;
import dev.bluephs.vintage.content.equipment.SpringItem;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import static dev.bluephs.vintage.VintageItems.IRON_SPRING;

public class SpringItemBuilder extends ItemBuilder {
    public transient int stiffness = 50;

    public SpringItemBuilder(ResourceLocation i) {
        super(i);
    }

    public SpringItemBuilder setStiffness(int stiffness) {
        this.stiffness = stiffness;
        return this;
    }

    @Override
    public Item createObject() {
        var item = new SpringItem(createItemProperties(), this.stiffness);
        ItemDescription.referKey(item, IRON_SPRING);
        return item;
    }
}

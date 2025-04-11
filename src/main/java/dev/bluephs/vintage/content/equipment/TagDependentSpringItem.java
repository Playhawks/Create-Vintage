package dev.bluephs.vintage.content.equipment;

import dev.bluephs.vintage.Vintage;
import com.simibubi.create.foundation.item.TagDependentIngredientItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class TagDependentSpringItem extends TagDependentIngredientItem {

    int stiffness;

    public TagDependentSpringItem(Properties properties, int stiffness, TagKey<Item> tag) {
        super(properties, tag);
        this.stiffness = stiffness;
    }

    public int getStiffness() {return stiffness;}

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable(Vintage.MOD_ID + ".item_description.spring_stiffness")
                .append(" " + stiffness).withStyle(ChatFormatting.GOLD));
    }

}

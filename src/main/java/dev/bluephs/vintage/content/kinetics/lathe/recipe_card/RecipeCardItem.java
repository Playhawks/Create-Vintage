package dev.bluephs.vintage.content.kinetics.lathe.recipe_card;

import dev.bluephs.vintage.Vintage;
import dev.bluephs.vintage.VintageItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

public class RecipeCardItem extends Item implements MenuProvider {

	public RecipeCardItem(Properties properties) {super(properties);}

	@Override
	public Component getDisplayName() {
		return getDescription();
	}

	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
		ItemStack heldItem = player.getMainHandItem();
		return RecipeCardMenu.create(id, inv, heldItem);
	}

	public static ItemStackHandler getFrequencyItems(ItemStack stack) {
		ItemStackHandler newInv = new ItemStackHandler(1);
		if (VintageItems.RECIPE_CARD.get() != stack.getItem())
			throw new IllegalArgumentException("Cannot get frequency items from non-recipe card: " + stack);
		CompoundTag invNBT = stack.getOrCreateTagElement("Items");
		if (!invNBT.isEmpty())
			newInv.deserializeNBT(invNBT);
		return newInv;
	}

	public static ItemStackHandler getResultItems(ItemStack stack) {
		ItemStackHandler newInv = new ItemStackHandler(1);
		if (VintageItems.RECIPE_CARD.get() != stack.getItem())
			throw new IllegalArgumentException("Cannot get frequency items from non-recipe card: " + stack);
		CompoundTag invNBT = stack.getOrCreateTagElement("Results");
		if (!invNBT.isEmpty())
			newInv.deserializeNBT(invNBT);
		return newInv;
	}

	protected static int getIndex(ItemStack stack) {
		if (VintageItems.RECIPE_CARD.get() != stack.getItem())
			throw new IllegalArgumentException("Cannot get index from non-recipe card: " + stack);
		if (stack.getOrCreateTagElement("Recipe").contains("Index"))
			return stack.getOrCreateTagElement("Recipe").getInt("Index");
		return -1;
	}

	public static boolean haveRecipe(ItemStack stack) {
		return stack.getOrCreateTagElement("Recipe").contains("Index");
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack heldItem = player.getItemInHand(hand);

		if (!player.isShiftKeyDown() && hand == InteractionHand.MAIN_HAND) {
			if (!world.isClientSide && player instanceof ServerPlayer && player.mayBuild())
				NetworkHooks.openScreen((ServerPlayer) player, this, buf -> {
					buf.writeItem(heldItem);
				});
			return InteractionResultHolder.success(heldItem);
		}
		return InteractionResultHolder.pass(heldItem);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		int index = getIndex(stack);
		ItemStack ingredient = getFrequencyItems(stack).getStackInSlot(0);
		ItemStack result = getResultItems(stack).getStackInSlot(0);

		if (!ingredient.isEmpty())
			list.add(Component.translatable(Vintage.MOD_ID + ".item_description.ingredient")
				.append(" ").append(Component.translatable(ingredient.getDescriptionId())).withStyle(ChatFormatting.WHITE));
		if (!result.isEmpty())
			list.add(Component.translatable(Vintage.MOD_ID + ".item_description.result")
				.append(" ").append(Component.translatable(result.getDescriptionId())).withStyle(ChatFormatting.GOLD));
		if (index >= 0)
			list.add(Component.translatable(Vintage.MOD_ID + ".item_description.recipe_index")
				.append(" " + index).withStyle(ChatFormatting.DARK_GRAY));
	}

}

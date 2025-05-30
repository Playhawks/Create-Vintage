package dev.bluephs.vintage.compat.jei.category;

import javax.annotation.ParametersAreNonnullByDefault;

import dev.bluephs.vintage.compat.jei.VintageRecipeUtil;
import dev.bluephs.vintage.compat.jei.category.animations.AnimatedVacuumChamber;
import dev.bluephs.vintage.content.kinetics.vacuum_chamber.VacuumizingRecipe;
import dev.bluephs.vintage.foundation.gui.VintageGuiTextures;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.compat.jei.category.BasinCategory;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.animations.AnimatedBlazeBurner;
import com.simibubi.create.content.processing.basin.BasinRecipe;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.item.ItemHelper;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.createmod.catnip.data.Pair;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public class VacuumizingCategory extends BasinCategory {

	private final AnimatedVacuumChamber vacuum = new AnimatedVacuumChamber();
	private final AnimatedBlazeBurner heater = new AnimatedBlazeBurner();

	public VacuumizingCategory(Info<BasinRecipe> info) {
		super(info, true);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, BasinRecipe recipe, IFocusGroup focuses) {
		List<Pair<Ingredient, MutableInt>> condensedIngredients = ItemHelper.condenseIngredients(recipe.getIngredients());

		int size = condensedIngredients.size() + recipe.getFluidIngredients().size();
		int xOffset = size < 3 ? (3 - size) * 19 / 2 : 0;
		int i = 0;

		for (Pair<Ingredient, MutableInt> pair : condensedIngredients) {
			List<ItemStack> stacks = new ArrayList<>();
			for (ItemStack itemStack : pair.getFirst().getItems()) {
				ItemStack copy = itemStack.copy();
				copy.setCount(pair.getSecond().getValue());
				stacks.add(copy);
			}

			builder
					.addSlot(RecipeIngredientRole.INPUT, 17 + xOffset + (i % 3) * 19, 51 - (i / 3) * 19)
					.setBackground(getRenderedSlot(), -1, -1)
					.addItemStacks(stacks);
			i++;
		}

		int j = 0;
		for (FluidIngredient fluidIngredient : recipe.getFluidIngredients()) {
			if (recipe instanceof VacuumizingRecipe r && j == r.getSecondaryFluidInputs())
				CreateRecipeCategory.addFluidSlot(builder, 21, 14, fluidIngredient)
						.addRichTooltipCallback(VintageRecipeUtil.addTooltip("jei.text.secondary_fluid_ingredient"));
			else
				CreateRecipeCategory.addFluidSlot(builder, 17 + xOffset + (i % 3) * 19, 51 - (i / 3) * 19, fluidIngredient);

			i++;
			j++;
		}

		size = recipe.getRollableResults().size() + recipe.getFluidResults().size();
		i = 0;

		for (ProcessingOutput result : recipe.getRollableResults()) {
			int xPosition = 142 - (size % 2 != 0 && i == size - 1 ? 0 : i % 2 == 0 ? 10 : -9);
			int yPosition = -19 * (i / 2) + 51;

			builder
					.addSlot(RecipeIngredientRole.OUTPUT, xPosition, yPosition)
					.setBackground(getRenderedSlot(result), -1, -1)
					.addItemStack(result.getStack())
					.addRichTooltipCallback(addStochasticTooltip(result));
			i++;
		}

		j = 0;
		for (FluidStack fluidResult : recipe.getFluidResults()) {
			if (recipe instanceof VacuumizingRecipe vRecipe) {
				int secondary = vRecipe.getSecondaryFluidResults();
				int xPosition;
				int yPosition;

				if (j == secondary) {
					xPosition = 140;
					yPosition = 2;

					CreateRecipeCategory.addFluidSlot(builder, xPosition, yPosition, fluidResult)
							.addRichTooltipCallback(VintageRecipeUtil.addTooltip("jei.text.secondary_fluid_result"));
				}
				else {
					xPosition = 142 - ((secondary >= 0 ? size - 1 : size) % 2 != 0 && i == (secondary >= 0 ? size - 1 : size) - 1 ? 0 : i % 2 == 0 ? 10 : -9);
					yPosition = -19 * (i / 2) + 51;

					CreateRecipeCategory.addFluidSlot(builder, xPosition, yPosition, fluidResult);
				}

				i++;
				j++;
			}
		}

		HeatCondition requiredHeat = recipe.getRequiredHeat();
		if (!requiredHeat.testBlazeBurner(BlazeBurnerBlock.HeatLevel.NONE)) {
			builder
					.addSlot(RecipeIngredientRole.RENDER_ONLY, 134, 81)
					.addItemStack(AllBlocks.BLAZE_BURNER.asStack());
		}
		if (!requiredHeat.testBlazeBurner(BlazeBurnerBlock.HeatLevel.KINDLED)) {
			builder
					.addSlot(RecipeIngredientRole.CATALYST, 153, 81)
					.addItemStack(AllItems.BLAZE_CAKE.asStack());
		}
	}

	@Override
	public void draw(BasinRecipe recipe, IRecipeSlotsView iRecipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
		super.draw(recipe, iRecipeSlotsView, graphics, mouseX, mouseY);

		if (recipe instanceof VacuumizingRecipe vrecipe) {
			if (vrecipe.getSecondaryFluidResults() >= 0 && vrecipe.getFluidResults().size() > 0)
				VintageGuiTextures.JEI_UP_TO_RIGHT_ARROW.render(graphics, 120, 2);
			if (vrecipe.getSecondaryFluidInputs() >= 0 && vrecipe.getFluidIngredients().size() > 0)
				AllGuiTextures.JEI_ARROW.render(graphics, 45, 18);
		}

		HeatCondition requiredHeat = recipe.getRequiredHeat();
		if (requiredHeat != HeatCondition.NONE)
			heater.withHeat(requiredHeat.visualizeAsBlazeBurner())
					.draw(graphics, getBackground().getWidth() / 2 + 3, 55);
		vacuum.draw(graphics, getBackground().getWidth() / 2 + 3, 34, false);
	}

}

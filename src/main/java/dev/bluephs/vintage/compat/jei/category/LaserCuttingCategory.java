package dev.bluephs.vintage.compat.jei.category;

import dev.bluephs.vintage.Vintage;
import dev.bluephs.vintage.compat.jei.category.animations.AnimatedLaser;
import dev.bluephs.vintage.content.kinetics.laser.LaserCuttingRecipe;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class LaserCuttingCategory extends CreateRecipeCategory<LaserCuttingRecipe> {

	private final AnimatedLaser laser = new AnimatedLaser();

	public LaserCuttingCategory(Info<LaserCuttingRecipe> info) {
		super(info);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, LaserCuttingRecipe recipe, IFocusGroup focuses) {
		List<Ingredient> inputs = recipe.getIngredients();
		int i = 0;
		for (Ingredient ingredient : inputs) {
			int xOffset = i * 19;
			builder
					.addSlot(RecipeIngredientRole.INPUT, 4 + xOffset, 36)
					.setBackground(getRenderedSlot(), -1, -1)
					.addIngredients(ingredient);
			i++;
		}

		i = 0;
		List<ProcessingOutput> results = recipe.getRollableResults();

		for (ProcessingOutput result : results) {
			builder
					.addSlot(RecipeIngredientRole.OUTPUT, 148 - (10 * results.size()) + 19 * i, 48)
					.setBackground(getRenderedSlot(result), -1, -1)
					.addItemStack(result.getStack())
					.addRichTooltipCallback(addStochasticTooltip(result));
			i++;
		}
	}

	@Override
	public void draw(LaserCuttingRecipe recipe, IRecipeSlotsView iRecipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
		AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 132, 28);
		AllGuiTextures.JEI_LONG_ARROW.render(graphics, 2, 55);

		laser.draw(graphics, 86, 22);

		graphics.drawCenteredString(Minecraft.getInstance().font,
				Component.translatable(Vintage.MOD_ID + ".jei.text.energy")
						.append(" " + recipe.getEnergy() + "fe"),
				88, 75, 0xFFFFFF);
	}

}

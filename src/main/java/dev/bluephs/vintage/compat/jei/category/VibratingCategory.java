package dev.bluephs.vintage.compat.jei.category;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import dev.bluephs.vintage.compat.jei.category.animations.AnimatedVibratingTable;
import dev.bluephs.vintage.content.kinetics.vibration.VibratingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.GuiGraphics;

@ParametersAreNonnullByDefault
public class VibratingCategory extends CreateRecipeCategory<VibratingRecipe> {

	private final AnimatedVibratingTable table = new AnimatedVibratingTable();

	public VibratingCategory(Info<VibratingRecipe> info) {
		super(info);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, VibratingRecipe recipe, IFocusGroup focuses) {
		builder
				.addSlot(RecipeIngredientRole.INPUT, 15, 9)
				.setBackground(getRenderedSlot(), -1, -1)
				.addIngredients(recipe.getIngredients().get(0));

		List<ProcessingOutput> results = recipe.getRollableResults();
		boolean single = results.size() == 1;
		int i = 0;
		for (ProcessingOutput output : results) {
			int xOffset = i % 2 == 0 ? 0 : 19;
			int yOffset = (i / 2) * -19;

			builder
					.addSlot(RecipeIngredientRole.OUTPUT, single ? 139 : 133 + xOffset, 27 + yOffset)
					.setBackground(getRenderedSlot(output), -1, -1)
					.addItemStack(output.getStack())
					.addRichTooltipCallback(addStochasticTooltip(output));

			i++;
		}
	}

	@Override
	public void draw(VibratingRecipe recipe, IRecipeSlotsView iRecipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
		AllGuiTextures.JEI_ARROW.render(graphics, 85, 32);
		AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 43, 4);

		AllGuiTextures.JEI_SHADOW.render(graphics, 48 - 17, 35 + 13);

		table.draw(graphics, 48, 35);
	}

}

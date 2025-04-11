package dev.bluephs.vintage.content.kinetics.helve_hammer;

import dev.bluephs.vintage.VintageRecipes;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeParams;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class AutoUpgradeRecipe extends ProcessingRecipe<RecipeWrapper> {
	public AutoUpgradeRecipe(ProcessingRecipeParams params) {
		super(VintageRecipes.AUTO_UPGRADE, params);
	}

	@Override
	public boolean matches(RecipeWrapper inv, Level worldIn) {
		if (inv.isEmpty())
			return false;
		return ingredients.get(0)
			.test(inv.getItem(0));
	}

	@Override
	protected int getMaxInputCount() {
		return 3;
	}

	@Override
	protected int getMaxOutputCount() {
		return 1;
	}
}

package dev.bluephs.vintage.compat.kubejs.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.recipe.BlockTagIngredient;
import dev.latvian.mods.kubejs.create.CreateInputFluid;
import dev.latvian.mods.kubejs.create.platform.FluidIngredientHelper;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.item.ingredient.TagContext;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.MapJS;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

/**
 * @author LatvianModder
 */
public interface CompressorRecipeSchema {

	RecipeKey<Either<OutputFluid, OutputItem>[]> RESULTS = FluidComponents.OUTPUT_OR_ITEM_ARRAY.key("results");
	RecipeKey<Either<InputFluid, InputItem>[]> INGREDIENTS = FluidComponents.INPUT_OR_ITEM_ARRAY.key("ingredients");
	RecipeKey<Long> PROCESSING_TIME_REQUIRED = TimeComponent.TICKS.key("processingTime").optional(100L).alwaysWrite();
	RecipeKey<String> HEAT_REQUIREMENT = new StringComponent("not a valid heat condition!", s -> {
		for (var h : HeatCondition.values()) {
			if (h.name().equalsIgnoreCase(s)) {
				return true;
			}
		}
		return false;
	}).key("heatRequirement").defaultOptional().allowEmpty();
	RecipeKey<Integer> SECONDARY_FLUID_INPUT = NumberComponent.IntRange.INT.key("secondaryFluidInput").optional(-1);
	RecipeKey<Integer> SECONDARY_FLUID_OUTPUT = NumberComponent.IntRange.INT.key("secondaryFluidOutput").optional(-1);

	class ProcessingRecipeJS extends RecipeJS {
		@Override
		public InputFluid readInputFluid(Object from) {
			if (from instanceof CreateInputFluid fluid) {
				return fluid;
			} else if (from instanceof FluidIngredient fluid) {
				return new CreateInputFluid(fluid);
			} else if (from instanceof FluidStackJS fluid) {
				return new CreateInputFluid(FluidIngredientHelper.toFluidIngredient(fluid));
			} else if (from instanceof FluidStack fluid) {
				return new CreateInputFluid(FluidIngredient.fromFluidStack(fluid));
			} else {
				var json = MapJS.json(from);
				if (json != null) {
					return new CreateInputFluid(FluidIngredient.deserialize(json));
				}
				return CreateInputFluid.EMPTY;
			}
		}

		@Override
		public JsonElement writeInputFluid(InputFluid value) {
			if (value instanceof CreateInputFluid fluid) {
				return fluid.ingredient().serialize();
			} else if (value instanceof FluidIngredient fluid) {
				return fluid.serialize();
			} else if (value instanceof FluidStackJS fluid) {
				return FluidIngredientHelper.toFluidIngredient(fluid).serialize();
			} else {
				return FluidIngredient.EMPTY.serialize();
			}
		}

		@Override
		public boolean inputItemHasPriority(Object from) {
			if (from instanceof InputItem || from instanceof Ingredient || from instanceof ItemStack) {
				return true;
			}

			var input = readInputItem(from);
			if (input.ingredient instanceof BlockTagIngredient blockTag) {
				return !TagContext.INSTANCE.getValue().isEmpty(blockTag.getTag());
			}

			return !input.isEmpty();
		}

		@Override
		public boolean inputFluidHasPriority(Object from) {
			return from instanceof InputFluid || FluidIngredient.isFluidIngredient(MapJS.json(from));
		}

		@Override
		public OutputItem readOutputItem(Object from) {
			if (from instanceof ProcessingOutput output) {
				return OutputItem.of(output.getStack(), output.getChance());
			} else {
				var outputItem = super.readOutputItem(from);
				if (from instanceof JsonObject j && j.has("chance")) {
					return outputItem.withChance(j.get("chance").getAsFloat());
				}
				return outputItem;
			}
		}

		public RecipeJS heated() {
			return setValue(HEAT_REQUIREMENT, HeatCondition.HEATED.serialize());
		}

		public RecipeJS superheated() {
			return setValue(HEAT_REQUIREMENT, HeatCondition.SUPERHEATED.serialize());
		}

		public RecipeJS secondaryFluidInput(int index) {
			return setValue(SECONDARY_FLUID_INPUT, index);
		}

		public RecipeJS secondaryFluidOutput(int index) {
			return setValue(SECONDARY_FLUID_OUTPUT, index);
		}
	}

	RecipeSchema COMPRESSOR_PROCESSING = new RecipeSchema(ProcessingRecipeJS.class, ProcessingRecipeJS::new, RESULTS, INGREDIENTS, PROCESSING_TIME_REQUIRED, SECONDARY_FLUID_OUTPUT, SECONDARY_FLUID_INPUT, HEAT_REQUIREMENT);
}

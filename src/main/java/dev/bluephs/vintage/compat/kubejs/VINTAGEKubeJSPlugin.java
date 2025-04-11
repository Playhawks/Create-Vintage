package dev.bluephs.vintage.compat.kubejs;

import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import dev.bluephs.vintage.VintageRecipes;
import dev.bluephs.vintage.compat.kubejs.recipes.*;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.create.ProcessingRecipeSchema;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import dev.latvian.mods.kubejs.registry.RegistryInfo;

import java.util.Map;

public class VINTAGEKubeJSPlugin extends KubeJSPlugin {
    private static final Map<VintageRecipes, RecipeSchema> recipeSchema = Map.of(
            VintageRecipes.VIBRATING, ProcessingRecipeSchema.PROCESSING_WITH_TIME,
            VintageRecipes.POLISHING, PolishingRecipeSchema.POLISHING_PROCESSING,
            VintageRecipes.PRESSURIZING, CompressorRecipeSchema.COMPRESSOR_PROCESSING,
            VintageRecipes.VACUUMIZING, CompressorRecipeSchema.COMPRESSOR_PROCESSING,
            VintageRecipes.COILING, CoilingRecipeSchema.COILING_PROCESSING,
            VintageRecipes.CENTRIFUGATION, CentrifugationRecipeSchema.CENTRIFUGATION_PROCESSING,
            VintageRecipes.CURVING, CurvingRecipeSchema.CURVING_PROCESSING,
            VintageRecipes.HAMMERING, HammeringRecipeSchema.HAMMERING_PROCESSING,
            VintageRecipes.LASER_CUTTING, LaserRecipeSchema.LASER_PROCESSING,
            VintageRecipes.TURNING, ProcessingRecipeSchema.PROCESSING_WITH_TIME
    );

    public void init() {
        RegistryInfo.ITEM.addType("vintage:spring", SpringItemBuilder.class, SpringItemBuilder::new);
    }

    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
        for (var vintageRecipeType : VintageRecipes.values()) {
            if (vintageRecipeType.getSerializer() instanceof ProcessingRecipeSerializer<?>) {
                var schema = recipeSchema.getOrDefault(vintageRecipeType, ProcessingRecipeSchema.PROCESSING_DEFAULT);
                event.register(vintageRecipeType.getId(), schema);
            }
        }
    }
}

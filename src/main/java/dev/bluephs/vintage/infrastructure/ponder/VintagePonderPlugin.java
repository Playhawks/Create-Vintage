package dev.bluephs.vintage.infrastructure.ponder;

import dev.bluephs.vintage.Vintage;
import net.createmod.ponder.api.registration.*;
import net.minecraft.resources.ResourceLocation;

public class VintagePonderPlugin implements PonderPlugin {
    @Override
    public String getModId() {
        return Vintage.MOD_ID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        VintagePonderScene.register(helper);
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        VintagePonderTag.register(helper);
    }
}

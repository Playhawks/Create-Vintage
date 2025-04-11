package dev.bluephs.vintage.foundation.advancement;

import dev.bluephs.vintage.Vintage;
import com.simibubi.create.foundation.advancement.SimpleCreateTrigger;

import net.minecraft.resources.ResourceLocation;

public class SimpleVintageTrigger extends SimpleCreateTrigger {

    private ResourceLocation trueID;

    public SimpleVintageTrigger(String id) {
        super(id);
        trueID = Vintage.asResource(id);
    };

    @Override
    public ResourceLocation getId() {
        return trueID;
    };
    
};

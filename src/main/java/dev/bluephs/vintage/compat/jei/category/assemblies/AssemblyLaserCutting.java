package dev.bluephs.vintage.compat.jei.category.assemblies;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.bluephs.vintage.compat.jei.category.animations.AnimatedLaser;
import com.simibubi.create.compat.jei.category.sequencedAssembly.SequencedAssemblySubCategory;
import com.simibubi.create.content.processing.sequenced.SequencedRecipe;
import net.minecraft.client.gui.GuiGraphics;

public class AssemblyLaserCutting extends SequencedAssemblySubCategory {

    AnimatedLaser laser;

    public AssemblyLaserCutting() {
        super(25);
        laser = new AnimatedLaser();
    }

    @Override
    public void draw(SequencedRecipe<?> recipe, GuiGraphics graphics, double mouseX, double mouseY, int index) {
        PoseStack ms = graphics.pose();
        laser.offset = index;
        ms.pushPose();
        ms.translate(-5, 50, 0);
        ms.scale(.6f, .6f, .6f);
        laser.draw(graphics, getWidth() / 2, 0);
        ms.popPose();
    }

}

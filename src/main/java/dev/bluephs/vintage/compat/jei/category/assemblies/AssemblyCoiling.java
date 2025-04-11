package dev.bluephs.vintage.compat.jei.category.assemblies;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.bluephs.vintage.compat.jei.category.animations.AnimatedCoiling;
import com.simibubi.create.compat.jei.category.sequencedAssembly.SequencedAssemblySubCategory;
import com.simibubi.create.content.processing.sequenced.SequencedRecipe;
import net.minecraft.client.gui.GuiGraphics;

public class AssemblyCoiling extends SequencedAssemblySubCategory {

    AnimatedCoiling coiling;

    public AssemblyCoiling() {
        super(25);
        coiling = new AnimatedCoiling();
    }

    @Override
    public void draw(SequencedRecipe<?> recipe, GuiGraphics graphics, double mouseX, double mouseY, int index) {
        PoseStack ms = graphics.pose();
        ms.pushPose();
        ms.translate(0, 51.5f, 0);
        ms.scale(.6f, .6f, .6f);
        coiling.draw(graphics, getWidth() / 2, 30);
        ms.popPose();
    }

}

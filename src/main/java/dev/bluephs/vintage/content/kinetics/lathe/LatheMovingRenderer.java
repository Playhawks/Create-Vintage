package dev.bluephs.vintage.content.kinetics.lathe;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.bluephs.vintage.VintagePartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import static net.minecraft.world.level.block.DirectionalBlock.FACING;

public class LatheMovingRenderer extends KineticBlockEntityRenderer<LatheMovingBlockEntity> {

	public LatheMovingRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public boolean shouldRenderOffScreen(LatheMovingBlockEntity be) {
		return true;
	}

	@Override
	protected void renderSafe(LatheMovingBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		renderSlot(be, ms, buffer, light, overlay);


		if (VisualizationManager.supportsVisualization(be.getLevel()))
			return;

		renderShaft(be, ms, buffer, light, overlay);
	}

	protected void renderShaft(LatheMovingBlockEntity be, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		KineticBlockEntityRenderer.renderRotatingBuffer(be, getRotatedModel(be, be.getBlockState()), ms, buffer.getBuffer(RenderType.solid()), light);
	}

	protected void renderSlot(LatheMovingBlockEntity be, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		BlockState blockState = be.getBlockState();
		VertexConsumer vb = buffer.getBuffer(RenderType.solid());

		PartialModel partial = (be.recipeSlot.isEmpty() ? VintagePartialModels.LATHE_SLOT : VintagePartialModels.LATHE_SLOT_FULL);

		SuperByteBuffer superBuffer = CachedBuffers.partial(partial, blockState);
		superBuffer.rotateCentered(blockState.getValue(FACING) == Direction.SOUTH ? (180*(float)Math.PI/180f) :
                                blockState.getValue(FACING) == Direction.WEST ? (90*(float)Math.PI/180f) :
                                        blockState.getValue(FACING) == Direction.EAST ? (270*(float)Math.PI/180f) : 0,
                        Direction.UP)
				.light(light).renderInto(ms, vb);
	}

	protected SuperByteBuffer getRotatedModel(LatheMovingBlockEntity be, BlockState state) {
		return CachedBuffers.block(KineticBlockEntityRenderer.KINETIC_BLOCK,
				getRenderedBlockState(be));
	}

	protected BlockState getRenderedBlockState(LatheMovingBlockEntity be) {
		return KineticBlockEntityRenderer.shaft(KineticBlockEntityRenderer.getRotationAxisOf(be));
	}

}

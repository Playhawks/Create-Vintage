package dev.bluephs.vintage.content.kinetics.helve_hammer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.bluephs.vintage.VintagePartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import static net.minecraft.world.level.block.DirectionalBlock.FACING;

public class HelveRenderer extends KineticBlockEntityRenderer<HelveKineticBlockEntity> {

	public HelveRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public boolean shouldRenderOffScreen(HelveKineticBlockEntity be) {
		return true;
	}

	@Override
	protected void renderSafe(HelveKineticBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		BlockState blockState = be.getBlockState();
		VertexConsumer vb = buffer.getBuffer(RenderType.solid());
		SuperByteBuffer superBuffer = CachedBuffers.partial(VintagePartialModels.HELVE_HAMMER, blockState);
		superBuffer.rotateCentered(AngleHelper.rad(be.getHammerAngle()), blockState.getValue(FACING).getCounterClockWise());
		superBuffer.rotateCentered(AngleHelper.rad(blockState.getValue(FACING) == Direction.SOUTH ? 180
                        : blockState.getValue(FACING) == Direction.NORTH ? 0
                        : blockState.getValue(FACING) == Direction.EAST ? 270 : 90), Direction.UP);

		superBuffer.translate(0, 0, -1);

		superBuffer.light(light).renderInto(ms, vb);


		if (VisualizationManager.supportsVisualization(be.getLevel()))
			return;

		renderShaft(be, ms, buffer, light, overlay);
	}

	protected void renderShaft(HelveKineticBlockEntity be, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		KineticBlockEntityRenderer.renderRotatingBuffer(be, getRotatedModel(be, be.getBlockState()), ms, buffer.getBuffer(RenderType.solid()), light);
	}

	protected SuperByteBuffer getRotatedModel(HelveKineticBlockEntity be, BlockState state) {
		return CachedBuffers.block(KineticBlockEntityRenderer.KINETIC_BLOCK,
				getRenderedBlockState(be));
	}

	protected BlockState getRenderedBlockState(HelveKineticBlockEntity be) {
		return KineticBlockEntityRenderer.shaft(KineticBlockEntityRenderer.getRotationAxisOf(be));
	}

}

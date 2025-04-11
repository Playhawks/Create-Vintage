package dev.bluephs.vintage.content.kinetics.vacuum_chamber;

import dev.bluephs.vintage.VintageBlockEntity;
import dev.bluephs.vintage.Vintage;
import dev.bluephs.vintage.VintageShapes;
import dev.bluephs.vintage.foundation.advancement.VintageAdvancementBehaviour;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.block.IBE;

import net.createmod.catnip.math.VoxelShaper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class VacuumChamberBlock extends KineticBlock implements IBE<VacuumChamberBlockEntity>, ICogWheel {
	public static final VoxelShaper VACUUM_CHAMBER_SHAPE = VintageShapes.shape(0, 0, 0, 16, 16, 16).forDirectional();

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		VintageAdvancementBehaviour.setPlacedBy(level, pos, placer);
		super.setPlacedBy(level, pos, state, placer, stack);
	}

	public VacuumChamberBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
		return !AllBlocks.BASIN.has(worldIn.getBlockState(pos.below()));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return VACUUM_CHAMBER_SHAPE.get(Direction.DOWN);
	}

	@Override
	public Axis getRotationAxis(BlockState state) {
		return Axis.Y;
	}

	@Override
	public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
		return false;
	}

	@Override
	public SpeedLevel getMinimumRequiredSpeedLevel() {
		return SpeedLevel.MEDIUM;
	}

	@Override
	public Class<VacuumChamberBlockEntity> getBlockEntityClass() {
		return VacuumChamberBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends VacuumChamberBlockEntity> getBlockEntityType() {
		return VintageBlockEntity.VACUUM.get();
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
		return false;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack itemStack, @Nullable BlockGetter getter, List<Component> list, TooltipFlag flag) {
		list.add(Component.translatable(Vintage.MOD_ID + ".item_description.machine_rpm_requirements").append(" " + SpeedLevel.MEDIUM.getSpeedValue()).withStyle(ChatFormatting.GOLD));
	}

	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn,
								 BlockHitResult hit) {
		ItemStack heldItem = player.getItemInHand(handIn);

		return onBlockEntityUse(worldIn, pos, be -> {
			if (!heldItem.isEmpty()) {
				if (heldItem.getItem() == AllItems.WRENCH.asItem() && be.runningTicks == 0) {
					be.mode = !be.mode;
					if (worldIn.isClientSide())
						AllSoundEvents.WRENCH_ROTATE.playAt(worldIn, pos, 3, 1,true);
				}
			}

			return InteractionResult.PASS;
		});
	}
}

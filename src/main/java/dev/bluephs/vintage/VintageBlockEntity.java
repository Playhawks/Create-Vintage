package dev.bluephs.vintage;

import dev.bluephs.vintage.content.kinetics.base.OrientedVisual;
import dev.bluephs.vintage.content.kinetics.centrifuge.CentrifugeBlockEntity;
import dev.bluephs.vintage.content.kinetics.centrifuge.CentrifugeVisual;
import dev.bluephs.vintage.content.kinetics.centrifuge.CentrifugeRenderer;
import dev.bluephs.vintage.content.kinetics.centrifuge.CentrifugeStructuralBlockEntity;
import dev.bluephs.vintage.content.kinetics.coiling.CoilingBlockEntity;
import dev.bluephs.vintage.content.kinetics.coiling.CoilingRenderer;
import dev.bluephs.vintage.content.kinetics.curving_press.CurvingPressBlockEntity;
import dev.bluephs.vintage.content.kinetics.curving_press.CurvingPressRenderer;
import dev.bluephs.vintage.content.kinetics.curving_press.CurvingVisual;
import dev.bluephs.vintage.content.kinetics.grinder.GrinderBlockEntity;
import dev.bluephs.vintage.content.kinetics.grinder.GrinderRenderer;
import dev.bluephs.vintage.content.kinetics.grinder.GrinderVisual;
import dev.bluephs.vintage.content.kinetics.helve_hammer.*;
import dev.bluephs.vintage.content.kinetics.laser.LaserBlockEntity;
import dev.bluephs.vintage.content.kinetics.laser.LaserRenderer;
import dev.bluephs.vintage.content.kinetics.lathe.LatheMovingBlockEntity;
import dev.bluephs.vintage.content.kinetics.lathe.LatheMovingRenderer;
import dev.bluephs.vintage.content.kinetics.lathe.LatheRotatingBlockEntity;
import dev.bluephs.vintage.content.kinetics.lathe.LatheRotatingRenderer;
import dev.bluephs.vintage.content.kinetics.vacuum_chamber.VacuumChamberBlockEntity;
import dev.bluephs.vintage.content.kinetics.vacuum_chamber.VacuumChamberVisual;
import dev.bluephs.vintage.content.kinetics.vacuum_chamber.VacuumChamberRenderer;
import dev.bluephs.vintage.content.kinetics.vibration.VibratingTableBlockEntity;
import dev.bluephs.vintage.content.kinetics.vibration.VibratingTableRenderer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.OrientedRotatingVisual;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import static dev.bluephs.vintage.Vintage.MY_REGISTRATE;

public class VintageBlockEntity {
    public static final BlockEntityEntry<GrinderBlockEntity> GRINDER = MY_REGISTRATE
            .blockEntity("grinder", GrinderBlockEntity::new)
            .visual(() -> GrinderVisual::new)
            .validBlocks(VintageBlocks.BELT_GRINDER)
            .renderer(() -> GrinderRenderer::new)
            .register();
    public static final BlockEntityEntry<CoilingBlockEntity> COILING = MY_REGISTRATE
            .blockEntity("coiling", CoilingBlockEntity::new)
            .visual(() -> OrientedRotatingVisual.backHorizontal(AllPartialModels.SHAFT_HALF))
            .validBlocks(VintageBlocks.SPRING_COILING_MACHINE)
            .renderer(() -> CoilingRenderer::new)
            .register();
    public static final BlockEntityEntry<VacuumChamberBlockEntity> VACUUM = MY_REGISTRATE
            .blockEntity("vacuum_chamber", VacuumChamberBlockEntity::new)
            .visual(() ->  VacuumChamberVisual::new)
            .validBlocks(VintageBlocks.VACUUM_CHAMBER)
            .renderer(() -> VacuumChamberRenderer::new)
            .register();
    public static final BlockEntityEntry<VibratingTableBlockEntity> VIBRATION = MY_REGISTRATE
            .blockEntity("vibration", VibratingTableBlockEntity::new)
            .visual(() -> SingleAxisRotatingVisual::shaft)
            .validBlocks(VintageBlocks.VIBRATING_TABLE)
            .renderer(() -> VibratingTableRenderer::new)
            .register();

    public static final BlockEntityEntry<CentrifugeBlockEntity> CENTRIFUGE = MY_REGISTRATE
            .blockEntity("centrifuge", CentrifugeBlockEntity::new)
            .visual(() -> CentrifugeVisual::new)
            .validBlocks(VintageBlocks.CENTRIFUGE)
            .renderer(() -> CentrifugeRenderer::new)
            .register();

    public static final BlockEntityEntry<CentrifugeStructuralBlockEntity> CENTRIFUGE_STRUCTURAL = MY_REGISTRATE
            .blockEntity("centrifuge_structural", CentrifugeStructuralBlockEntity::new)
            .validBlocks(VintageBlocks.CENTRIFUGE_STRUCTURAL)
            .register();

    public static final BlockEntityEntry<CurvingPressBlockEntity> CURVING_PRESS = MY_REGISTRATE
            .blockEntity("curving_press", CurvingPressBlockEntity::new)
            .visual(() -> CurvingVisual::new)
            .validBlocks(VintageBlocks.CURVING_PRESS)
            .renderer(() -> CurvingPressRenderer::new)
            .register();

    public static final BlockEntityEntry<HelveBlockEntity> HELVE = MY_REGISTRATE
            .blockEntity("helve_hammer", HelveBlockEntity::new)
            .validBlocks(VintageBlocks.HELVE)
            .renderer(() -> HelveItemsRenderer::new)
            .register();

    public static final BlockEntityEntry<HelveKineticBlockEntity> HELVE_KINETIC = MY_REGISTRATE
            .blockEntity("helve_kinetic", HelveKineticBlockEntity::new)
            .visual(() -> HelveVisual::new)
            .validBlocks(VintageBlocks.HELVE_KINETIC)
            .renderer(() -> HelveRenderer::new)
            .register();

    public static final BlockEntityEntry<LatheRotatingBlockEntity> LATHE_ROTATING = MY_REGISTRATE
            .blockEntity("lathe_rotating", LatheRotatingBlockEntity::new)
            .visual(() -> OrientedVisual.Horizontal(AllPartialModels.SHAFT_HALF))
            .validBlocks(VintageBlocks.LATHE_ROTATING)
            .renderer(() -> LatheRotatingRenderer::new)
            .register();

    public static final BlockEntityEntry<LatheMovingBlockEntity> LATHE_MOVING = MY_REGISTRATE
            .blockEntity("lathe_moving", LatheMovingBlockEntity::new)
            .visual(() -> SingleAxisRotatingVisual::shaft)
            .validBlocks(VintageBlocks.LATHE_MOVING)
            .renderer(() -> LatheMovingRenderer::new)
            .register();

    public static final BlockEntityEntry<LaserBlockEntity> LASER = MY_REGISTRATE
            .blockEntity("laser", LaserBlockEntity::new)
            .visual(() -> OrientedVisual.Horizontal(AllPartialModels.SHAFT_HALF))
            .validBlocks(VintageBlocks.LASER)
            .renderer(() -> LaserRenderer::new)
            .register();

    public static void register() {}
}

package dev.bluephs.vintage.content.kinetics.grinder;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;

import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;

public class GrinderVisual extends SingleAxisRotatingVisual<GrinderBlockEntity> {

	public GrinderVisual(VisualizationContext visualizationContext, GrinderBlockEntity grinderBlockEntity, float v) {

		super(visualizationContext, grinderBlockEntity, v, Models.partial(AllPartialModels.SHAFT));
	}
}

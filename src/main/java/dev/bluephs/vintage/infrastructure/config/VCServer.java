package dev.bluephs.vintage.infrastructure.config;

import net.createmod.catnip.config.ConfigBase;

public class VCServer extends ConfigBase {

	public final VCRecipes recipes = nested(0, VCRecipes::new, Comments.recipes);
	public final VCKinetics kinetics = nested(0, VCKinetics::new, Comments.kinetics);
	public final VCEnergy energy = nested(0, VCEnergy::new, Comments.energy);

	@Override
	public String getName() {
		return "server";
	}

	private static class Comments {
		static String recipes = "Packmakers' control panel for internal recipe compat";
		static String kinetics = "Parameters and abilities of Vintage Improvements's kinetic mechanisms";
		static String energy = "Parameters and abilities of Vintage Improvements's energy mechanisms";
	}

}

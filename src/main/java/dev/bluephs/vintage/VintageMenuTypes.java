package dev.bluephs.vintage;

import dev.bluephs.vintage.content.kinetics.lathe.LatheMenu;
import dev.bluephs.vintage.content.kinetics.lathe.LatheScreen;
import dev.bluephs.vintage.content.kinetics.lathe.recipe_card.RecipeCardMenu;
import dev.bluephs.vintage.content.kinetics.lathe.recipe_card.RecipeCardScreen;
import com.tterrag.registrate.builders.MenuBuilder.ForgeMenuFactory;
import com.tterrag.registrate.builders.MenuBuilder.ScreenFactory;
import com.tterrag.registrate.util.entry.MenuEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class VintageMenuTypes {

	public static final MenuEntry<RecipeCardMenu> RECIPE_CARD =
		register("recipe_card", RecipeCardMenu::new, () -> RecipeCardScreen::new);

	public static final MenuEntry<LatheMenu> LATHE =
			register("lathe", LatheMenu::new, () -> LatheScreen::new);

	private static <C extends AbstractContainerMenu, S extends Screen & MenuAccess<C>> MenuEntry<C> register(
		String name, ForgeMenuFactory<C> factory, NonNullSupplier<ScreenFactory<C, S>> screenFactory) {
		return Vintage.MY_REGISTRATE
			.menu(name, factory, screenFactory)
			.register();
	}

	public static void register() {}

}

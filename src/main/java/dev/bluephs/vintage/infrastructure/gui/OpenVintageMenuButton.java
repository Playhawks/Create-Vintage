package dev.bluephs.vintage.infrastructure.gui;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import dev.bluephs.vintage.VintageBlocks;
import dev.bluephs.vintage.Vintage;
import dev.bluephs.vintage.infrastructure.config.VintageConfig;
import net.createmod.catnip.config.ui.BaseConfigScreen;
import net.createmod.catnip.gui.ScreenOpener;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.mutable.MutableObject;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

public class OpenVintageMenuButton extends Button {

	public static final ItemStack ICON = VintageBlocks.BELT_GRINDER.asStack();

	public OpenVintageMenuButton(int x, int y) {
		super(x, y, 20, 20, Component.empty(), OpenVintageMenuButton::click, DEFAULT_NARRATION);
	}

	@Override
	public void renderString(GuiGraphics graphics, Font pFont, int pColor) {
		graphics.renderItem(ICON, getX() + 2, getY() + 2);
	}
	
	public static void click(Button b) {
		ScreenOpener.open(new BaseConfigScreen(Minecraft.getInstance().screen, Vintage.MOD_ID));
	}

	public static class SingleMenuRow {
		public final String left, right;
		public SingleMenuRow(String left, String right) {
			this.left = I18n.get(left);
			this.right = I18n.get(right);
		}
		public SingleMenuRow(String center) {
			this(center, center);
		}
	}

	public static class MenuRows {
		public static final MenuRows MAIN_MENU = new MenuRows(Arrays.asList(
			new SingleMenuRow("menu.singleplayer"),
			new SingleMenuRow("menu.multiplayer"),
			new SingleMenuRow("fml.menu.mods", "menu.online"),
			new SingleMenuRow("narrator.button.language", "narrator.button.accessibility")
		));

		public static final MenuRows INGAME_MENU = new MenuRows(Arrays.asList(
			new SingleMenuRow("menu.returnToGame"),
			new SingleMenuRow("gui.advancements", "gui.stats"),
			new SingleMenuRow("menu.sendFeedback", "menu.reportBugs"),
			new SingleMenuRow("menu.options", "menu.shareToLan"),
			new SingleMenuRow("menu.returnToMenu")
		));

		protected final List<String> leftButtons, rightButtons;

		public MenuRows(List<SingleMenuRow> variants) {
			leftButtons = variants.stream().map(r -> r.left).collect(Collectors.toList());
			rightButtons = variants.stream().map(r -> r.right).collect(Collectors.toList());
		}
	}

	@EventBusSubscriber(value = Dist.CLIENT)
	public static class OpenConfigButtonHandler {

		@SubscribeEvent
		public static void onGuiInit(ScreenEvent.Init event) {
			Screen gui = event.getScreen();

			MenuRows menu = null;
			int rowIdx = 0, offsetX = 0;
			if (gui instanceof TitleScreen) {
				menu = MenuRows.MAIN_MENU;
				rowIdx = VintageConfig.client().mainMenuConfigButtonRow.get();
				offsetX = VintageConfig.client().mainMenuConfigButtonOffsetX.get();
			} else if (gui instanceof PauseScreen) {
				menu = MenuRows.INGAME_MENU;
				rowIdx = VintageConfig.client().ingameMenuConfigButtonRow.get();
				offsetX = VintageConfig.client().ingameMenuConfigButtonOffsetX.get();
			}

			if (rowIdx != 0 && menu != null) {
				boolean onLeft = offsetX < 0;
				String target = (onLeft ? menu.leftButtons : menu.rightButtons).get(rowIdx - 1);

				int offsetX_ = offsetX;
				MutableObject<GuiEventListener> toAdd = new MutableObject<>(null);
				event.getListenersList()
					.stream()
					.filter(w -> w instanceof AbstractWidget)
					.map(w -> (AbstractWidget) w)
					.filter(w -> w.getMessage()
						.getString()
						.equals(target))
					.findFirst()
					.ifPresent(w -> toAdd
						.setValue(new OpenVintageMenuButton(w.getX() + offsetX_ + (onLeft ? -20 : w.getWidth()), w.getY())));
				if (toAdd.getValue() != null)
					event.addListener(toAdd.getValue());
			}
		}

	}

}

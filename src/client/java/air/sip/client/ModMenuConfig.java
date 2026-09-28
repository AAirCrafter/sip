package air.sip.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import static air.sip.Config.*;

public class ModMenuConfig implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return this::createConfigScreen;
    }

    private Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(Text.literal("SIP ModMenuConfig"));
        ConfigCategory general = builder.getOrCreateCategory(Text.literal("General"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        general.addEntry(
                entryBuilder.startBooleanToggle(Text.literal("Enabled"), enabled)
                        .setDefaultValue(true)
                        .setSaveConsumer(value -> enabled = value)
                        .build()
        );

        general.addEntry(
                entryBuilder.startBooleanToggle(Text.literal("Particles"), particles)
                        .setDefaultValue(true)
                        .setSaveConsumer(value -> particles = value)
                        .build()
        );

        general.addEntry(
                entryBuilder.startIntSlider(Text.literal("Opacity %"), opacityPerc,0,100)
                        .setDefaultValue(50)
                        .setSaveConsumer(value -> opacityPerc = value)
                        .build()
        );

        return builder.build();
    }
}
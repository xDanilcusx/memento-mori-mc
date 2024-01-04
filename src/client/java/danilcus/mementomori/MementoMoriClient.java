package danilcus.mementomori;

import danilcus.mementomori.item.SickleItem;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MementoMoriClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		Registry.ITEM.forEach((item) -> {
			if(item instanceof SickleItem) {
				ModelPredicateProviderRegistry.register(item, new Identifier("parrying"), (stack, world, entity, i) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
			}
		});
	}
}
package fr.envium.enviummod.addons.ironchest.client.data;

import fr.envium.enviummod.addons.ironchest.IronChests;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class IronChestsItemModelProvider extends ItemModelProvider {

  public IronChestsItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
    super(generator, IronChests.MODID, existingFileHelper);
  }

  @Override
  protected void registerModels() {

  }

  @Override
  public String getName() {
    return "Iron Chests Item Models";
  }
}

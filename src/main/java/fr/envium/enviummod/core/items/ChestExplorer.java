package fr.envium.enviummod.core.items;

import fr.envium.enviummod.api.tab.EnviumTab;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ChestExplorer extends Item {

	public ChestExplorer() {
		super(new Item.Properties().tab(EnviumTab.TAB).stacksTo(1));
		setRegistryName("chest_explorer");
	}
	
	@SubscribeEvent
	public boolean onBlockActivated(ItemStack stack, PlayerEntity player, World world, int x, int y, int z, int side,
									float hitX, float hitY, float hitZ) {
		if (!world.isClientSide) {
			TileEntity tile = world.getBlockEntity(new BlockPos(x, y, z));
			if (tile == null)
				return false;
			if (tile instanceof IInventory && tile.getClass().toString().contains("Chest")) {
				//POUR ROMAIN, y'a plus player.openGui, c'est peut-être maintenant openContainer. A tester
				//player.openContainer(EnviumMod.instance, 13, world, x, y, z);
				return true;
			}
		}
		return true;
	}
}

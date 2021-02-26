package fr.envium.enviummod.core.other;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class DeadHeadMod {

	/*@SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event)
    {
        if(event.getEntityLiving() instanceof PlayerEntity)
        { // EntityPlayer ou EntityPlayerMP si vous �tes sur un serveur (c'est pas tr�s important)
            // On recupere le joueur
            PlayerEntity player = (PlayerEntity) event.getEntityLiving(); // Ou EntityPlayerMP
            // On creer la tete
            Item item = Items.SKULL;
            ItemStack stack = new ItemStack(item, 1, 3);
 
            // On creer 2 NBTTagCompound pour ensuite ajouter le 2eme au 1er
            NBTTagCompound compound = new NBTTagCompound();
            NBTTagCompound compound1 = new NBTTagCompound();
 
            // On associe le NBTTagCompound au stack pour eviter de faire des erreurs
            stack.writeToNBT(compound);
 
            // On ajoute le nom du joueur au 2eme NBTTagCompound
            compound1.setString("SkullOwner", player.getName());
 
            // On demande a Mojang d'aller recuperer le UUID et tout le tralala pour nous
            ((ItemSkull)item).updateItemStackNBT(compound1);
 
            // On ajouter le tag creer par le code de Mojang a l'ItemStack
            compound.setTag("tag", compound1);
 
            // On recreer l'ItemStack a partir du NBTTagCompound
            //stack.readFromNBT(compound);
            stack.deserializeNBT(compound);
            
 
            // Et pour finir on le fait apparaitre a la position du joueur
            player.world.addEntity(new ItemEntity(player.world, player.getPosX(), player.getPosY(), player.getPosZ(), stack));
        }
    }*/
}

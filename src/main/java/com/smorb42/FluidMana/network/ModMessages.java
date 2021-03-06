package com.smorb42.FluidMana.network;





import com.smorb42.FluidMana.FluidMana;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(FluidMana.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(PacketSyncFluidStackToClient.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketSyncFluidStackToClient::new)
                .encoder(PacketSyncFluidStackToClient::toBytes)
                .consumer(PacketSyncFluidStackToClient::handle)
                .add();


        /*net.messageBuilder(PacketSyncItemStackToClient.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketSyncItemStackToClient::new)
                .encoder(PacketSyncItemStackToClient::toBytes)
                .consumer(PacketSyncItemStackToClient::handle)
                .add();


         */

    }



    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
package com.sinthoras.hydroenergy.network;

import com.sinthoras.hydroenergy.HE;
import com.sinthoras.hydroenergy.client.HEClient;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class HEPacketSynchronize implements IMessage {
	
	public float[] waterLevels = new float[HE.maxControllers];
	public boolean[] debugStates = new boolean[HE.maxControllers];
	public int[] limitsWest = new int[HE.maxControllers];
	public int[] limitsDown = new int[HE.maxControllers];
	public int[] limitsNorth = new int[HE.maxControllers];
	public int[] limitsEast = new int[HE.maxControllers];
	public int[] limitsUp = new int[HE.maxControllers];
	public int[] limitsSouth = new int[HE.maxControllers];

	@Override
	public void fromBytes(ByteBuf buf) {
		int length = buf.readInt();
		waterLevels = new float[length];
		debugStates = new boolean[length];
		limitsWest = new int[length];
		limitsDown = new int[length];
		limitsNorth = new int[length];
		limitsEast = new int[length];
		limitsUp = new int[length];
		limitsSouth = new int[length];
		for(int waterId=0;waterId<length;waterId++) {
			waterLevels[waterId] = buf.readFloat();
			debugStates[waterId] = buf.readBoolean();
			limitsWest[waterId] = buf.readInt();
			limitsDown[waterId] = buf.readInt();
			limitsNorth[waterId] = buf.readInt();
			limitsEast[waterId] = buf.readInt();
			limitsUp[waterId] = buf.readInt();
			limitsSouth[waterId] = buf.readInt();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(HE.maxControllers);
		for(int waterId = 0; waterId< HE.maxControllers; waterId++) {
			buf.writeFloat(waterLevels[waterId]);
			buf.writeBoolean(debugStates[waterId]);
			buf.writeInt(limitsWest[waterId]);
			buf.writeInt(limitsDown[waterId]);
			buf.writeInt(limitsNorth[waterId]);
			buf.writeInt(limitsEast[waterId]);
			buf.writeInt(limitsUp[waterId]);
			buf.writeInt(limitsSouth[waterId]);
		}
	}

	public static class Handler implements IMessageHandler<HEPacketSynchronize, IMessage> {

		@Override
		public IMessage onMessage(HEPacketSynchronize message, MessageContext ctx) {
			HEClient.onSynchronize(message.waterLevels,
					message.debugStates,
					message.limitsWest,
					message.limitsDown,
					message.limitsNorth,
					message.limitsEast,
					message.limitsUp,
					message.limitsSouth);
			return null;
		}
	}
}

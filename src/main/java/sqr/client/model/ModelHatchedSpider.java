/*******************************************************************************
 * ModelHatchedSpider.java
 * Copyright (c) 2014 Radix-Shock Entertainment.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/

package sqr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import sqr.entity.EntityHatchedSpider;
import sqr.enums.EnumCocoonType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelHatchedSpider extends ModelBase
{
	public ModelRenderer	villagerSpiderChestRight;
	public ModelRenderer	villagerSpiderChestLeft;

	public ModelRenderer	defaultSpiderHead;
	public ModelRenderer	defaultSpiderNeck;
	public ModelRenderer	defaultSpiderBody;
	public ModelRenderer	defaultSpiderLeg1;
	public ModelRenderer	defaultSpiderLeg2;
	public ModelRenderer	defaultSpiderLeg3;
	public ModelRenderer	defaultSpiderLeg4;
	public ModelRenderer	defaultSpiderLeg5;
	public ModelRenderer	defaultSpiderLeg6;
	public ModelRenderer	defaultSpiderLeg7;
	public ModelRenderer	defaultSpiderLeg8;

	public ModelRenderer	raisedSpiderHead;
	public ModelRenderer	raisedSpiderBody;
	public ModelRenderer	raisedSpiderRearEnd;
	public ModelRenderer	raisedSpiderLeg1;
	public ModelRenderer	raisedSpiderLeg2;
	public ModelRenderer	raisedSpiderLeg3;
	public ModelRenderer	raisedSpiderLeg4;
	public ModelRenderer	raisedSpiderLeg5;
	public ModelRenderer	raisedSpiderLeg6;
	public ModelRenderer	raisedSpiderLeg7;
	public ModelRenderer	raisedSpiderLeg8;

	public ModelRenderer	tinySpiderHead;
	public ModelRenderer	tinySpiderBody;
	public ModelRenderer	tinySpiderRearEnd;
	public ModelRenderer	tinySpiderLeg1;
	public ModelRenderer	tinySpiderLeg2;
	public ModelRenderer	tinySpiderLeg3;
	public ModelRenderer	tinySpiderLeg4;
	public ModelRenderer	tinySpiderLeg5;
	public ModelRenderer	tinySpiderLeg6;
	public ModelRenderer	tinySpiderLeg7;
	public ModelRenderer	tinySpiderLeg8;

	public ModelRenderer	thinSpiderHead;
	public ModelRenderer	thinSpiderBody;
	public ModelRenderer	thinSpiderRearEnd;
	public ModelRenderer	thinSpiderLeg1;
	public ModelRenderer	thinSpiderLeg2;
	public ModelRenderer	thinSpiderLeg3;
	public ModelRenderer	thinSpiderLeg4;
	public ModelRenderer	thinSpiderLeg5;
	public ModelRenderer	thinSpiderLeg6;
	public ModelRenderer	thinSpiderLeg7;
	public ModelRenderer	thinSpiderLeg8;

	public ModelRenderer	hugeSpiderHead;
	public ModelRenderer	hugeSpiderBody;
	public ModelRenderer	hugeSpiderRearEnd;
	public ModelRenderer	hugeSpiderLeg1;
	public ModelRenderer	hugeSpiderLeg2;
	public ModelRenderer	hugeSpiderLeg3;
	public ModelRenderer	hugeSpiderLeg4;
	public ModelRenderer	hugeSpiderLeg5;
	public ModelRenderer	hugeSpiderLeg6;
	public ModelRenderer	hugeSpiderLeg7;
	public ModelRenderer	hugeSpiderLeg8;
	public ModelRenderer	hugeSpiderTopBulk;
	public ModelRenderer	hugeSpiderBackBulk;
	public ModelRenderer	hugeSpiderRightBulk;
	public ModelRenderer	hugeSpiderLeftBulk;

	public ModelRenderer	longLegSpiderBody;
	public ModelRenderer	longLegSpiderLeg8;
	public ModelRenderer	longLegSpiderLeg6;
	public ModelRenderer	longLegSpiderLeg4;
	public ModelRenderer	longLegSpiderLeg2;
	public ModelRenderer	longLegSpiderLeg7;
	public ModelRenderer	longLegSpiderLeg5;
	public ModelRenderer	longLegSpiderLeg3;
	public ModelRenderer	longLegSpiderLeg1;

	public ModelRenderer	flatSpiderHead;
	public ModelRenderer	flatSpiderBody;
	public ModelRenderer	flatSpiderRearEnd;
	public ModelRenderer	flatSpiderLeg8;
	public ModelRenderer	flatSpiderLeg6;
	public ModelRenderer	flatSpiderLeg4;
	public ModelRenderer	flatSpiderLeg2;
	public ModelRenderer	flatSpiderLeg7;
	public ModelRenderer	flatSpiderLeg5;
	public ModelRenderer	flatSpiderLeg3;
	public ModelRenderer	flatSpiderLeg1;

	public ModelHatchedSpider()
	{
		initDefaultSpider();
		initRaisedSpider();
		initTinySpider();
		initThinSpider();
		initHugeSpider();
		initLongLegSpider();
		initFlatSpider();
		initVillagerSpiderChest();
	}

	@Override
	public void render(Entity entity, float limbSwing, float prevLimbSwing, float rotateFloat, float rotationYaw, float rotationPitch, float partialTickTime)
	{
		final EntityHatchedSpider spider = (EntityHatchedSpider) entity;
		setRotationAngles(limbSwing, prevLimbSwing, rotateFloat, rotationYaw, rotationPitch, partialTickTime, entity);

		switch (spider.cocoonType.getSpiderSize())
		{
			case HUGE:
				renderHugeSpider(partialTickTime);
				break;
			case NORMAL:
				renderDefaultSpider(partialTickTime);

				if (spider.cocoonType == EnumCocoonType.VILLAGER)
				{
					renderVillagerSpiderChest(partialTickTime);
				}

				break;
			case RAISED:
				renderRaisedSpider(partialTickTime);
				break;
			case THIN:
				renderThinSpider(partialTickTime);
				break;
			case TINY:
				renderTinySpider(partialTickTime);
				break;
			case LONGLEG:
				renderLongLegSpider(partialTickTime);
				break;
			case TINYLONGLEG:
				renderTinyLongLegSpider(partialTickTime);
				break;
			case SWARM:
				renderSwarmSpiders(entity, partialTickTime);
				break;
			case FLAT:
				renderFlatSpider(partialTickTime);
				break;
			default:
				break;
		}
	}

	@Override
	public void setRotationAngles(float limbSwing, float prevLimbSwing, float rotateFloat, float rotationYaw, float rotationPitch, float partialTickTime, Entity entity)
	{
		final EntityHatchedSpider spider = (EntityHatchedSpider) entity;

		if (EnumCocoonType.isAnimalBased(spider.cocoonType) || spider.cocoonType == EnumCocoonType.EMPTY || spider.cocoonType == EnumCocoonType.VILLAGER || spider.cocoonType == EnumCocoonType.HUMAN || spider.cocoonType == EnumCocoonType.HORSE)
		{
			defaultSpiderHead.rotateAngleY = rotationYaw / (180F / (float) Math.PI);
			defaultSpiderHead.rotateAngleX = rotationPitch / (180F / (float) Math.PI);

			final float quarterCircle = (float) Math.PI / 4F;
			defaultSpiderLeg1.rotateAngleZ = -quarterCircle;
			defaultSpiderLeg2.rotateAngleZ = quarterCircle;
			defaultSpiderLeg3.rotateAngleZ = -quarterCircle * 0.74F;
			defaultSpiderLeg4.rotateAngleZ = quarterCircle * 0.74F;
			defaultSpiderLeg5.rotateAngleZ = -quarterCircle * 0.74F;
			defaultSpiderLeg6.rotateAngleZ = quarterCircle * 0.74F;
			defaultSpiderLeg7.rotateAngleZ = -quarterCircle;
			defaultSpiderLeg8.rotateAngleZ = quarterCircle;

			final float eighthCircle = (float) Math.PI / 8F;
			defaultSpiderLeg1.rotateAngleY = eighthCircle * 2.0F;
			defaultSpiderLeg2.rotateAngleY = -eighthCircle * 2.0F;
			defaultSpiderLeg3.rotateAngleY = eighthCircle * 1.0F;
			defaultSpiderLeg4.rotateAngleY = -eighthCircle * 1.0F;
			defaultSpiderLeg5.rotateAngleY = -eighthCircle * 1.0F;
			defaultSpiderLeg6.rotateAngleY = eighthCircle * 1.0F;
			defaultSpiderLeg7.rotateAngleY = -eighthCircle * 2.0F;
			defaultSpiderLeg8.rotateAngleY = eighthCircle * 2.0F;

			final float frontRotateY = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F) * 0.4F) * prevLimbSwing;
			final float frontMidRotateY = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float) Math.PI) * 0.4F) * prevLimbSwing;
			final float backMidRotateY = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float) Math.PI / 2F) * 0.4F) * prevLimbSwing;
			final float backRotateY = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float) Math.PI * 3F / 2F) * 0.4F) * prevLimbSwing;
			final float frontRotateZ = Math.abs(MathHelper.sin(limbSwing * 0.6662F) * 0.4F) * prevLimbSwing;
			final float frontMidRotateZ = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float) Math.PI) * 0.4F) * prevLimbSwing;
			final float backMidRotateZ = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float) Math.PI / 2F) * 0.4F) * prevLimbSwing;
			final float backRotateZ = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float) Math.PI * 3F / 2F) * 0.4F) * prevLimbSwing;

			defaultSpiderLeg1.rotateAngleY += frontRotateY;
			defaultSpiderLeg2.rotateAngleY += -frontRotateY;
			defaultSpiderLeg3.rotateAngleY += frontMidRotateY;
			defaultSpiderLeg4.rotateAngleY += -frontMidRotateY;
			defaultSpiderLeg5.rotateAngleY += backMidRotateY;
			defaultSpiderLeg6.rotateAngleY += -backMidRotateY;
			defaultSpiderLeg7.rotateAngleY += backRotateY;
			defaultSpiderLeg8.rotateAngleY += -backRotateY;
			defaultSpiderLeg1.rotateAngleZ += frontRotateZ;
			defaultSpiderLeg2.rotateAngleZ += -frontRotateZ;
			defaultSpiderLeg3.rotateAngleZ += frontMidRotateZ;
			defaultSpiderLeg4.rotateAngleZ += -frontMidRotateZ;
			defaultSpiderLeg5.rotateAngleZ += backMidRotateZ;
			defaultSpiderLeg6.rotateAngleZ += -backMidRotateZ;
			defaultSpiderLeg7.rotateAngleZ += backRotateZ;
			defaultSpiderLeg8.rotateAngleZ += -backRotateZ;
		}

		else if (spider.cocoonType == EnumCocoonType.CREEPER)
		{
			raisedSpiderHead.rotateAngleY = rotationYaw / 57.29578F;
			raisedSpiderHead.rotateAngleX = rotationPitch / 57.29578F;
			final float f6 = 0.7853982F;
			raisedSpiderLeg1.rotateAngleZ = -f6;
			raisedSpiderLeg2.rotateAngleZ = f6;
			raisedSpiderLeg3.rotateAngleZ = -f6 * 0.74F;
			raisedSpiderLeg4.rotateAngleZ = f6 * 0.74F;
			raisedSpiderLeg5.rotateAngleZ = -f6 * 0.74F;
			raisedSpiderLeg6.rotateAngleZ = f6 * 0.74F;
			raisedSpiderLeg7.rotateAngleZ = -f6;
			raisedSpiderLeg8.rotateAngleZ = f6;
			final float f7 = -0F;
			final float f8 = 0.3926991F;
			raisedSpiderLeg1.rotateAngleY = f8 * 2.0F + f7;
			raisedSpiderLeg2.rotateAngleY = -f8 * 2.0F - f7;
			raisedSpiderLeg3.rotateAngleY = f8 * 1.0F + f7;
			raisedSpiderLeg4.rotateAngleY = -f8 * 1.0F - f7;
			raisedSpiderLeg5.rotateAngleY = -f8 * 1.0F + f7;
			raisedSpiderLeg6.rotateAngleY = f8 * 1.0F - f7;
			raisedSpiderLeg7.rotateAngleY = -f8 * 2.0F + f7;
			raisedSpiderLeg8.rotateAngleY = f8 * 2.0F - f7;
			final float f9 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * prevLimbSwing;
			final float f10 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 3.141593F) * 0.4F) * prevLimbSwing;
			final float f11 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 1.570796F) * 0.4F) * prevLimbSwing;
			final float f12 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 4.712389F) * 0.4F) * prevLimbSwing;
			final float f13 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * prevLimbSwing;
			final float f14 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 3.141593F) * 0.4F) * prevLimbSwing;
			final float f15 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 1.570796F) * 0.4F) * prevLimbSwing;
			final float f16 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 4.712389F) * 0.4F) * prevLimbSwing;
			raisedSpiderLeg1.rotateAngleY += f9;
			raisedSpiderLeg2.rotateAngleY += -f9;
			raisedSpiderLeg3.rotateAngleY += f10;
			raisedSpiderLeg4.rotateAngleY += -f10;
			raisedSpiderLeg5.rotateAngleY += f11;
			raisedSpiderLeg6.rotateAngleY += -f11;
			raisedSpiderLeg7.rotateAngleY += f12;
			raisedSpiderLeg8.rotateAngleY += -f12;
			raisedSpiderLeg1.rotateAngleZ += f13;
			raisedSpiderLeg2.rotateAngleZ += -f13;
			raisedSpiderLeg3.rotateAngleZ += f14;
			raisedSpiderLeg4.rotateAngleZ += -f14;
			raisedSpiderLeg5.rotateAngleZ += f15;
			raisedSpiderLeg6.rotateAngleZ += -f15;
			raisedSpiderLeg7.rotateAngleZ += f16;
			raisedSpiderLeg8.rotateAngleZ += -f16;
		}

		else if (spider.cocoonType == EnumCocoonType.WOLF)
		{
			tinySpiderHead.rotateAngleY = rotationYaw / 57.29578F;
			tinySpiderHead.rotateAngleX = rotationPitch / 57.29578F;
			final float f6 = 0.7853982F;
			tinySpiderLeg1.rotateAngleZ = -f6;
			tinySpiderLeg2.rotateAngleZ = f6;
			tinySpiderLeg3.rotateAngleZ = -f6 * 0.74F;
			tinySpiderLeg4.rotateAngleZ = f6 * 0.74F;
			tinySpiderLeg5.rotateAngleZ = -f6 * 0.74F;
			tinySpiderLeg6.rotateAngleZ = f6 * 0.74F;
			tinySpiderLeg7.rotateAngleZ = -f6;
			tinySpiderLeg8.rotateAngleZ = f6;
			final float f7 = -0F;
			final float f8 = 0.3926991F;
			tinySpiderLeg1.rotateAngleY = f8 * 2.0F + f7;
			tinySpiderLeg2.rotateAngleY = -f8 * 2.0F - f7;
			tinySpiderLeg3.rotateAngleY = f8 * 1.0F + f7;
			tinySpiderLeg4.rotateAngleY = -f8 * 1.0F - f7;
			tinySpiderLeg5.rotateAngleY = -f8 * 1.0F + f7;
			tinySpiderLeg6.rotateAngleY = f8 * 1.0F - f7;
			tinySpiderLeg7.rotateAngleY = -f8 * 2.0F + f7;
			tinySpiderLeg8.rotateAngleY = f8 * 2.0F - f7;
			final float f9 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * prevLimbSwing;
			final float f10 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 3.141593F) * 0.4F) * prevLimbSwing;
			final float f11 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 1.570796F) * 0.4F) * prevLimbSwing;
			final float f12 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 4.712389F) * 0.4F) * prevLimbSwing;
			final float f13 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * prevLimbSwing;
			final float f14 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 3.141593F) * 0.4F) * prevLimbSwing;
			final float f15 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 1.570796F) * 0.4F) * prevLimbSwing;
			final float f16 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 4.712389F) * 0.4F) * prevLimbSwing;
			tinySpiderLeg1.rotateAngleY += f9;
			tinySpiderLeg2.rotateAngleY += -f9;
			tinySpiderLeg3.rotateAngleY += f10;
			tinySpiderLeg4.rotateAngleY += -f10;
			tinySpiderLeg5.rotateAngleY += f11;
			tinySpiderLeg6.rotateAngleY += -f11;
			tinySpiderLeg7.rotateAngleY += f12;
			tinySpiderLeg8.rotateAngleY += -f12;
			tinySpiderLeg1.rotateAngleZ += f13;
			tinySpiderLeg2.rotateAngleZ += -f13;
			tinySpiderLeg3.rotateAngleZ += f14;
			tinySpiderLeg4.rotateAngleZ += -f14;
			tinySpiderLeg5.rotateAngleZ += f15;
			tinySpiderLeg6.rotateAngleZ += -f15;
			tinySpiderLeg7.rotateAngleZ += f16;
			tinySpiderLeg8.rotateAngleZ += -f16;
		}

		else if (spider.cocoonType == EnumCocoonType.SKELETON)
		{
			thinSpiderHead.rotateAngleY = rotationYaw / 57.29578F;
			thinSpiderHead.rotateAngleX = rotationPitch / 57.29578F;
			final float f6 = 0.7853982F;
			thinSpiderLeg1.rotateAngleZ = -f6;
			thinSpiderLeg2.rotateAngleZ = f6;
			thinSpiderLeg3.rotateAngleZ = -f6 * 0.74F;
			thinSpiderLeg4.rotateAngleZ = f6 * 0.74F;
			thinSpiderLeg5.rotateAngleZ = -f6 * 0.74F;
			thinSpiderLeg6.rotateAngleZ = f6 * 0.74F;
			thinSpiderLeg7.rotateAngleZ = -f6;
			thinSpiderLeg8.rotateAngleZ = f6;
			final float f7 = -0F;
			final float f8 = 0.3926991F;
			thinSpiderLeg1.rotateAngleY = f8 * 2.0F + f7;
			thinSpiderLeg2.rotateAngleY = -f8 * 2.0F - f7;
			thinSpiderLeg3.rotateAngleY = f8 * 1.0F + f7;
			thinSpiderLeg4.rotateAngleY = -f8 * 1.0F - f7;
			thinSpiderLeg5.rotateAngleY = -f8 * 1.0F + f7;
			thinSpiderLeg6.rotateAngleY = f8 * 1.0F - f7;
			thinSpiderLeg7.rotateAngleY = -f8 * 2.0F + f7;
			thinSpiderLeg8.rotateAngleY = f8 * 2.0F - f7;
			final float f9 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * prevLimbSwing;
			final float f10 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 3.141593F) * 0.4F) * prevLimbSwing;
			final float f11 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 1.570796F) * 0.4F) * prevLimbSwing;
			final float f12 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 4.712389F) * 0.4F) * prevLimbSwing;
			final float f13 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * prevLimbSwing;
			final float f14 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 3.141593F) * 0.4F) * prevLimbSwing;
			final float f15 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 1.570796F) * 0.4F) * prevLimbSwing;
			final float f16 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 4.712389F) * 0.4F) * prevLimbSwing;
			thinSpiderLeg1.rotateAngleY += f9;
			thinSpiderLeg2.rotateAngleY += -f9;
			thinSpiderLeg3.rotateAngleY += f10;
			thinSpiderLeg4.rotateAngleY += -f10;
			thinSpiderLeg5.rotateAngleY += f11;
			thinSpiderLeg6.rotateAngleY += -f11;
			thinSpiderLeg7.rotateAngleY += f12;
			thinSpiderLeg8.rotateAngleY += -f12;
			thinSpiderLeg1.rotateAngleZ += f13;
			thinSpiderLeg2.rotateAngleZ += -f13;
			thinSpiderLeg3.rotateAngleZ += f14;
			thinSpiderLeg4.rotateAngleZ += -f14;
			thinSpiderLeg5.rotateAngleZ += f15;
			thinSpiderLeg6.rotateAngleZ += -f15;
			thinSpiderLeg7.rotateAngleZ += f16;
			thinSpiderLeg8.rotateAngleZ += -f16;
		}

		else if (spider.cocoonType == EnumCocoonType.ZOMBIE)
		{
			hugeSpiderHead.rotateAngleY = rotationYaw / 57.29578F;
			hugeSpiderHead.rotateAngleX = rotationPitch / 57.29578F;
			final float f6 = 0.7853982F;
			hugeSpiderLeg1.rotateAngleZ = -f6;
			hugeSpiderLeg2.rotateAngleZ = f6;
			hugeSpiderLeg3.rotateAngleZ = -f6 * 0.74F;
			hugeSpiderLeg4.rotateAngleZ = f6 * 0.74F;
			hugeSpiderLeg5.rotateAngleZ = -f6 * 0.74F;
			hugeSpiderLeg6.rotateAngleZ = f6 * 0.74F;
			hugeSpiderLeg7.rotateAngleZ = -f6;
			hugeSpiderLeg8.rotateAngleZ = f6;
			final float f7 = -0F;
			final float f8 = 0.3926991F;
			hugeSpiderLeg1.rotateAngleY = f8 * 2.0F + f7;
			hugeSpiderLeg2.rotateAngleY = -f8 * 2.0F - f7;
			hugeSpiderLeg3.rotateAngleY = f8 * 1.0F + f7;
			hugeSpiderLeg4.rotateAngleY = -f8 * 1.0F - f7;
			hugeSpiderLeg5.rotateAngleY = -f8 * 1.0F + f7;
			hugeSpiderLeg6.rotateAngleY = f8 * 1.0F - f7;
			hugeSpiderLeg7.rotateAngleY = -f8 * 2.0F + f7;
			hugeSpiderLeg8.rotateAngleY = f8 * 2.0F - f7;
			final float f9 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * prevLimbSwing;
			final float f10 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 3.141593F) * 0.4F) * prevLimbSwing;
			final float f11 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 1.570796F) * 0.4F) * prevLimbSwing;
			final float f12 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 4.712389F) * 0.4F) * prevLimbSwing;
			final float f13 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * prevLimbSwing;
			final float f14 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 3.141593F) * 0.4F) * prevLimbSwing;
			final float f15 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 1.570796F) * 0.4F) * prevLimbSwing;
			final float f16 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 4.712389F) * 0.4F) * prevLimbSwing;
			hugeSpiderLeg1.rotateAngleY += f9;
			hugeSpiderLeg2.rotateAngleY += -f9;
			hugeSpiderLeg3.rotateAngleY += f10;
			hugeSpiderLeg4.rotateAngleY += -f10;
			hugeSpiderLeg5.rotateAngleY += f11;
			hugeSpiderLeg6.rotateAngleY += -f11;
			hugeSpiderLeg7.rotateAngleY += f12;
			hugeSpiderLeg8.rotateAngleY += -f12;
			hugeSpiderLeg1.rotateAngleZ += f13;
			hugeSpiderLeg2.rotateAngleZ += -f13;
			hugeSpiderLeg3.rotateAngleZ += f14;
			hugeSpiderLeg4.rotateAngleZ += -f14;
			hugeSpiderLeg5.rotateAngleZ += f15;
			hugeSpiderLeg6.rotateAngleZ += -f15;
			hugeSpiderLeg7.rotateAngleZ += f16;
			hugeSpiderLeg8.rotateAngleZ += -f16;
		}

		else if (spider.cocoonType == EnumCocoonType.ENDERMAN || spider.cocoonType == EnumCocoonType._ENDERMINION)
		{
			final float quarterCircle = (float) Math.PI / 4F;
			longLegSpiderLeg1.rotateAngleZ = -quarterCircle;
			longLegSpiderLeg2.rotateAngleZ = quarterCircle;
			longLegSpiderLeg3.rotateAngleZ = -quarterCircle * 0.74F;
			longLegSpiderLeg4.rotateAngleZ = quarterCircle * 0.74F;
			longLegSpiderLeg5.rotateAngleZ = -quarterCircle * 0.74F;
			longLegSpiderLeg6.rotateAngleZ = quarterCircle * 0.74F;
			longLegSpiderLeg7.rotateAngleZ = -quarterCircle;
			longLegSpiderLeg8.rotateAngleZ = quarterCircle;

			final float eighthCircle = (float) Math.PI / 8F;
			longLegSpiderLeg1.rotateAngleY = eighthCircle * 2.0F;
			longLegSpiderLeg2.rotateAngleY = -eighthCircle * 2.0F;
			longLegSpiderLeg3.rotateAngleY = eighthCircle * 1.0F;
			longLegSpiderLeg4.rotateAngleY = -eighthCircle * 1.0F;
			longLegSpiderLeg5.rotateAngleY = -eighthCircle * 1.0F;
			longLegSpiderLeg6.rotateAngleY = eighthCircle * 1.0F;
			longLegSpiderLeg7.rotateAngleY = -eighthCircle * 2.0F;
			longLegSpiderLeg8.rotateAngleY = eighthCircle * 2.0F;

			final float frontRotateY = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F) * 0.4F) * prevLimbSwing;
			final float frontMidRotateY = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float) Math.PI) * 0.4F) * prevLimbSwing;
			final float backMidRotateY = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float) Math.PI / 2F) * 0.4F) * prevLimbSwing;
			final float backRotateY = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float) Math.PI * 3F / 2F) * 0.4F) * prevLimbSwing;
			final float frontRotateZ = Math.abs(MathHelper.sin(limbSwing * 0.6662F) * 0.4F) * prevLimbSwing;
			final float frontMidRotateZ = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float) Math.PI) * 0.4F) * prevLimbSwing;
			final float backMidRotateZ = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float) Math.PI / 2F) * 0.4F) * prevLimbSwing;
			final float backRotateZ = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float) Math.PI * 3F / 2F) * 0.4F) * prevLimbSwing;

			longLegSpiderLeg1.rotateAngleY += frontRotateY;
			longLegSpiderLeg2.rotateAngleY += -frontRotateY;
			longLegSpiderLeg3.rotateAngleY += frontMidRotateY;
			longLegSpiderLeg4.rotateAngleY += -frontMidRotateY;
			longLegSpiderLeg5.rotateAngleY += backMidRotateY;
			longLegSpiderLeg6.rotateAngleY += -backMidRotateY;
			longLegSpiderLeg7.rotateAngleY += backRotateY;
			longLegSpiderLeg8.rotateAngleY += -backRotateY;
			longLegSpiderLeg1.rotateAngleZ += frontRotateZ;
			longLegSpiderLeg2.rotateAngleZ += -frontRotateZ;
			longLegSpiderLeg3.rotateAngleZ += frontMidRotateZ;
			longLegSpiderLeg4.rotateAngleZ += -frontMidRotateZ;
			longLegSpiderLeg5.rotateAngleZ += backMidRotateZ;
			longLegSpiderLeg6.rotateAngleZ += -backMidRotateZ;
			longLegSpiderLeg7.rotateAngleZ += backRotateZ;
			longLegSpiderLeg8.rotateAngleZ += -backRotateZ;
		}

		else if (spider.cocoonType == EnumCocoonType.BLAZE)
		{
			flatSpiderHead.rotateAngleY = rotationYaw / (180F / (float) Math.PI);
			flatSpiderHead.rotateAngleX = rotationPitch / (180F / (float) Math.PI);

			final float quarterCircle = (float) Math.PI / 4F;
			flatSpiderLeg1.rotateAngleZ = -quarterCircle;
			flatSpiderLeg2.rotateAngleZ = quarterCircle;
			flatSpiderLeg3.rotateAngleZ = -quarterCircle * 0.74F;
			flatSpiderLeg4.rotateAngleZ = quarterCircle * 0.74F;
			flatSpiderLeg5.rotateAngleZ = -quarterCircle * 0.74F;
			flatSpiderLeg6.rotateAngleZ = quarterCircle * 0.74F;
			flatSpiderLeg7.rotateAngleZ = -quarterCircle;
			flatSpiderLeg8.rotateAngleZ = quarterCircle;

			final float eighthCircle = (float) Math.PI / 8F;
			flatSpiderLeg1.rotateAngleY = eighthCircle * 2.0F;
			flatSpiderLeg2.rotateAngleY = -eighthCircle * 2.0F;
			flatSpiderLeg3.rotateAngleY = eighthCircle * 1.0F;
			flatSpiderLeg4.rotateAngleY = -eighthCircle * 1.0F;
			flatSpiderLeg5.rotateAngleY = -eighthCircle * 1.0F;
			flatSpiderLeg6.rotateAngleY = eighthCircle * 1.0F;
			flatSpiderLeg7.rotateAngleY = -eighthCircle * 2.0F;
			flatSpiderLeg8.rotateAngleY = eighthCircle * 2.0F;

			final float frontRotateY = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F) * 0.4F) * prevLimbSwing;
			final float frontMidRotateY = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float) Math.PI) * 0.4F) * prevLimbSwing;
			final float backMidRotateY = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float) Math.PI / 2F) * 0.4F) * prevLimbSwing;
			final float backRotateY = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float) Math.PI * 3F / 2F) * 0.4F) * prevLimbSwing;
			final float frontRotateZ = Math.abs(MathHelper.sin(limbSwing * 0.6662F) * 0.4F) * prevLimbSwing;
			final float frontMidRotateZ = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float) Math.PI) * 0.4F) * prevLimbSwing;
			final float backMidRotateZ = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float) Math.PI / 2F) * 0.4F) * prevLimbSwing;
			final float backRotateZ = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float) Math.PI * 3F / 2F) * 0.4F) * prevLimbSwing;

			flatSpiderLeg1.rotateAngleY += frontRotateY;
			flatSpiderLeg2.rotateAngleY += -frontRotateY;
			flatSpiderLeg3.rotateAngleY += frontMidRotateY;
			flatSpiderLeg4.rotateAngleY += -frontMidRotateY;
			flatSpiderLeg5.rotateAngleY += backMidRotateY;
			flatSpiderLeg6.rotateAngleY += -backMidRotateY;
			flatSpiderLeg7.rotateAngleY += backRotateY;
			flatSpiderLeg8.rotateAngleY += -backRotateY;
			flatSpiderLeg1.rotateAngleZ += frontRotateZ;
			flatSpiderLeg2.rotateAngleZ += -frontRotateZ;
			flatSpiderLeg3.rotateAngleZ += frontMidRotateZ;
			flatSpiderLeg4.rotateAngleZ += -frontMidRotateZ;
			flatSpiderLeg5.rotateAngleZ += backMidRotateZ;
			flatSpiderLeg6.rotateAngleZ += -backMidRotateZ;
			flatSpiderLeg7.rotateAngleZ += backRotateZ;
			flatSpiderLeg8.rotateAngleZ += -backRotateZ;
		}
	}

	private void initHugeSpider()
	{
		hugeSpiderHead = new ModelRenderer(this, 32, 4);
		hugeSpiderHead.addBox(-4F, -4F, -8F, 8, 8, 8, 0.0F);
		hugeSpiderHead.setRotationPoint(0F, 15F, -3F);
		hugeSpiderHead.rotateAngleX = 0.18081F;
		hugeSpiderHead.rotateAngleZ = -0.27122F;

		hugeSpiderBody = new ModelRenderer(this, 0, 0);
		hugeSpiderBody.addBox(-3F, -3F, -3F, 6, 6, 6, 0.0F);
		hugeSpiderBody.setRotationPoint(0F, 15F, 0F);

		hugeSpiderRearEnd = new ModelRenderer(this, 0, 12);
		hugeSpiderRearEnd.addBox(-5F, -4F, -6F, 12, 9, 12, 0.0F);
		hugeSpiderRearEnd.setRotationPoint(-1F, 14F, 9F);

		hugeSpiderLeg1 = new ModelRenderer(this, 18, 0);
		hugeSpiderLeg1.addBox(-15F, -1F, -1F, 16, 2, 2, 0.0F);
		hugeSpiderLeg1.setRotationPoint(-4F, 15F, 2F);
		hugeSpiderLeg1.rotateAngleX = 0.57596F;
		hugeSpiderLeg1.rotateAngleY = 0.19199F;

		hugeSpiderLeg2 = new ModelRenderer(this, 18, 0);
		hugeSpiderLeg2.addBox(-1F, -1F, -1F, 16, 2, 2, 0.0F);
		hugeSpiderLeg2.setRotationPoint(4F, 15F, 2F);
		hugeSpiderLeg2.rotateAngleX = -0.57596F;
		hugeSpiderLeg2.rotateAngleY = -0.19199F;

		hugeSpiderLeg3 = new ModelRenderer(this, 18, 0);
		hugeSpiderLeg3.addBox(-15F, -1F, -1F, 16, 2, 2, 0.0F);
		hugeSpiderLeg3.setRotationPoint(-4F, 15F, 1F);
		hugeSpiderLeg3.rotateAngleX = 0.27925F;
		hugeSpiderLeg3.rotateAngleY = 0.19199F;

		hugeSpiderLeg4 = new ModelRenderer(this, 18, 0);
		hugeSpiderLeg4.addBox(-1F, -1F, -1F, 16, 2, 2, 0.0F);
		hugeSpiderLeg4.setRotationPoint(4F, 15F, 1F);
		hugeSpiderLeg4.rotateAngleX = -0.27925F;
		hugeSpiderLeg4.rotateAngleY = -0.19199F;

		hugeSpiderLeg5 = new ModelRenderer(this, 18, 0);
		hugeSpiderLeg5.addBox(-15F, -1F, -1F, 16, 2, 2, 0.0F);
		hugeSpiderLeg5.setRotationPoint(-4F, 15F, 0F);
		hugeSpiderLeg5.rotateAngleX = -0.27925F;
		hugeSpiderLeg5.rotateAngleY = 0.19199F;

		hugeSpiderLeg6 = new ModelRenderer(this, 18, 0);
		hugeSpiderLeg6.addBox(-1F, -1F, -1F, 16, 2, 2, 0.0F);
		hugeSpiderLeg6.setRotationPoint(4F, 15F, 0F);
		hugeSpiderLeg6.rotateAngleX = 0.27925F;
		hugeSpiderLeg6.rotateAngleY = -0.19199F;

		hugeSpiderLeg7 = new ModelRenderer(this, 18, 0);
		hugeSpiderLeg7.addBox(-15F, -1F, -1F, 16, 2, 2, 0.0F);
		hugeSpiderLeg7.setRotationPoint(-4F, 15F, -1F);
		hugeSpiderLeg7.rotateAngleX = -0.57596F;
		hugeSpiderLeg7.rotateAngleY = 0.19199F;

		hugeSpiderLeg8 = new ModelRenderer(this, 18, 0);
		hugeSpiderLeg8.addBox(-1F, -1F, -1F, 16, 2, 2, 0.0F);
		hugeSpiderLeg8.setRotationPoint(4F, 15F, -1F);
		hugeSpiderLeg8.rotateAngleX = 0.57596F;
		hugeSpiderLeg8.rotateAngleY = -0.19199F;

		hugeSpiderTopBulk = new ModelRenderer(this, 0, 12);
		hugeSpiderTopBulk.addBox(0F, 0F, 0F, 8, 2, 9, 0.0F);
		hugeSpiderTopBulk.setRotationPoint(-4F, 8F, 4F);

		hugeSpiderBackBulk = new ModelRenderer(this, 0, 12);
		hugeSpiderBackBulk.addBox(-4F, -2F, 0F, 7, 6, 2, 0.0F);
		hugeSpiderBackBulk.setRotationPoint(0F, 14F, 15F);

		hugeSpiderLeftBulk = new ModelRenderer(this, 0, 12);
		hugeSpiderLeftBulk.addBox(0F, -2F, -3F, 2, 4, 9, 0.0F);
		hugeSpiderLeftBulk.setRotationPoint(6F, 15F, 7F);

		hugeSpiderRightBulk = new ModelRenderer(this, 0, 12);
		hugeSpiderRightBulk.addBox(-2F, -2F, -3F, 2, 4, 10, 0.0F);
		hugeSpiderRightBulk.setRotationPoint(-6F, 15F, 7F);
	}

	private void initRaisedSpider()
	{
		raisedSpiderHead = new ModelRenderer(this, 32, 4);
		raisedSpiderHead.addBox(-4F, -4F, -8F, 8, 8, 8, 0.0F);
		raisedSpiderHead.setRotationPoint(0F, 15F, -3F);

		raisedSpiderBody = new ModelRenderer(this, 0, 0);
		raisedSpiderBody.addBox(-3F, -3F, -3F, 6, 6, 6, 0.0F);
		raisedSpiderBody.setRotationPoint(0F, 15F, 0F);

		raisedSpiderRearEnd = new ModelRenderer(this, 0, 12);
		raisedSpiderRearEnd.addBox(-5F, -4F, -6F, 10, 8, 12, 0.0F);
		raisedSpiderRearEnd.setRotationPoint(0F, 11F, 7F);
		raisedSpiderRearEnd.rotateAngleX = 0.63284F;

		raisedSpiderLeg1 = new ModelRenderer(this, 18, 0);
		raisedSpiderLeg1.addBox(-15F, -1F, -1F, 16, 2, 2, 0.0F);
		raisedSpiderLeg1.setRotationPoint(-4F, 15F, 2F);
		raisedSpiderLeg1.rotateAngleX = 0.57596F;
		raisedSpiderLeg1.rotateAngleY = 0.19199F;

		raisedSpiderLeg2 = new ModelRenderer(this, 18, 0);
		raisedSpiderLeg2.addBox(-1F, -1F, -1F, 16, 2, 2, 0.0F);
		raisedSpiderLeg2.setRotationPoint(4F, 15F, 2F);
		raisedSpiderLeg2.rotateAngleX = -0.57596F;
		raisedSpiderLeg2.rotateAngleY = -0.19199F;

		raisedSpiderLeg3 = new ModelRenderer(this, 18, 0);
		raisedSpiderLeg3.addBox(-15F, -1F, -1F, 16, 2, 2, 0.0F);
		raisedSpiderLeg3.setRotationPoint(-4F, 15F, 1F);
		raisedSpiderLeg3.rotateAngleX = 0.27925F;
		raisedSpiderLeg3.rotateAngleY = 0.19199F;

		raisedSpiderLeg4 = new ModelRenderer(this, 18, 0);
		raisedSpiderLeg4.addBox(-1F, -1F, -1F, 16, 2, 2, 0.0F);
		raisedSpiderLeg4.setRotationPoint(4F, 15F, 1F);
		raisedSpiderLeg4.rotateAngleX = -0.27925F;
		raisedSpiderLeg4.rotateAngleY = -0.19199F;

		raisedSpiderLeg5 = new ModelRenderer(this, 18, 0);
		raisedSpiderLeg5.addBox(-15F, -1F, -1F, 16, 2, 2, 0.0F);
		raisedSpiderLeg5.setRotationPoint(-4F, 15F, 0F);
		raisedSpiderLeg5.rotateAngleX = -0.27925F;
		raisedSpiderLeg5.rotateAngleY = 0.19199F;

		raisedSpiderLeg6 = new ModelRenderer(this, 18, 0);
		raisedSpiderLeg6.addBox(-1F, -1F, -1F, 16, 2, 2, 0.0F);
		raisedSpiderLeg6.setRotationPoint(4F, 15F, 0F);
		raisedSpiderLeg6.rotateAngleX = 0.27925F;
		raisedSpiderLeg6.rotateAngleY = -0.19199F;

		raisedSpiderLeg7 = new ModelRenderer(this, 18, 0);
		raisedSpiderLeg7.addBox(-15F, -1F, -1F, 16, 2, 2, 0.0F);
		raisedSpiderLeg7.setRotationPoint(-4F, 15F, -1F);
		raisedSpiderLeg7.rotateAngleX = -0.57596F;
		raisedSpiderLeg7.rotateAngleY = 0.19199F;

		raisedSpiderLeg8 = new ModelRenderer(this, 18, 0);
		raisedSpiderLeg8.addBox(-1F, -1F, -1F, 16, 2, 2, 0.0F);
		raisedSpiderLeg8.setRotationPoint(4F, 15F, -1F);
		raisedSpiderLeg8.rotateAngleX = 0.57596F;
		raisedSpiderLeg8.rotateAngleY = -0.19199F;
	}

	private void initDefaultSpider()
	{
		defaultSpiderHead = new ModelRenderer(this, 32, 4);
		defaultSpiderHead.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, 0.0F);
		defaultSpiderHead.setRotationPoint(0.0F, 15, -3.0F);

		defaultSpiderNeck = new ModelRenderer(this, 0, 0);
		defaultSpiderNeck.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
		defaultSpiderNeck.setRotationPoint(0.0F, 15, 0.0F);

		defaultSpiderBody = new ModelRenderer(this, 0, 12);
		defaultSpiderBody.addBox(-5.0F, -4.0F, -6.0F, 10, 8, 12, 0.0F);
		defaultSpiderBody.setRotationPoint(0.0F, 15, 9.0F);

		defaultSpiderLeg1 = new ModelRenderer(this, 18, 0);
		defaultSpiderLeg1.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
		defaultSpiderLeg1.setRotationPoint(-4.0F, 15, 2.0F);

		defaultSpiderLeg2 = new ModelRenderer(this, 18, 0);
		defaultSpiderLeg2.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
		defaultSpiderLeg2.setRotationPoint(4.0F, 15, 2.0F);

		defaultSpiderLeg3 = new ModelRenderer(this, 18, 0);
		defaultSpiderLeg3.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
		defaultSpiderLeg3.setRotationPoint(-4.0F, 15, 1.0F);

		defaultSpiderLeg4 = new ModelRenderer(this, 18, 0);
		defaultSpiderLeg4.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
		defaultSpiderLeg4.setRotationPoint(4.0F, 15, 1.0F);

		defaultSpiderLeg5 = new ModelRenderer(this, 18, 0);
		defaultSpiderLeg5.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
		defaultSpiderLeg5.setRotationPoint(-4.0F, 15, 0.0F);

		defaultSpiderLeg6 = new ModelRenderer(this, 18, 0);
		defaultSpiderLeg6.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
		defaultSpiderLeg6.setRotationPoint(4.0F, 15, 0.0F);

		defaultSpiderLeg7 = new ModelRenderer(this, 18, 0);
		defaultSpiderLeg7.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
		defaultSpiderLeg7.setRotationPoint(-4.0F, 15, -1.0F);

		defaultSpiderLeg8 = new ModelRenderer(this, 18, 0);
		defaultSpiderLeg8.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
		defaultSpiderLeg8.setRotationPoint(4.0F, 15, -1.0F);
	}

	private void initThinSpider()
	{
		thinSpiderHead = new ModelRenderer(this, 32, 4);
		thinSpiderHead.addBox(-4F, -6F, -8F, 8, 8, 8, 0.0F);
		thinSpiderHead.setRotationPoint(0F, 14F, -3F);

		thinSpiderBody = new ModelRenderer(this, 0, 0);
		thinSpiderBody.addBox(-3F, -3F, -3F, 6, 4, 6, 0.0F);
		thinSpiderBody.setRotationPoint(0F, 16F, 0F);

		thinSpiderRearEnd = new ModelRenderer(this, 0, 12);
		thinSpiderRearEnd.addBox(-5F, -4F, -6F, 8, 6, 10, 0.0F);
		thinSpiderRearEnd.setRotationPoint(1F, 13F, 8F);
		thinSpiderRearEnd.rotateAngleX = 0.32023F;

		thinSpiderLeg1 = new ModelRenderer(this, 18, 0);
		thinSpiderLeg1.addBox(-15F, -1F, -1F, 16, 1, 1, 0.0F);
		thinSpiderLeg1.setRotationPoint(-4F, 15F, 2F);
		thinSpiderLeg1.rotateAngleX = 0.57596F;
		thinSpiderLeg1.rotateAngleY = 0.19199F;

		thinSpiderLeg2 = new ModelRenderer(this, 18, 0);
		thinSpiderLeg2.addBox(-1F, -1F, -1F, 16, 1, 1, 0.0F);
		thinSpiderLeg2.setRotationPoint(4F, 15F, 2F);
		thinSpiderLeg2.rotateAngleX = -0.57596F;
		thinSpiderLeg2.rotateAngleY = -0.19199F;

		thinSpiderLeg3 = new ModelRenderer(this, 18, 0);
		thinSpiderLeg3.addBox(-15F, -1F, -1F, 16, 1, 1, 0.0F);
		thinSpiderLeg3.setRotationPoint(-4F, 15F, 1F);
		thinSpiderLeg3.rotateAngleX = 0.27925F;
		thinSpiderLeg3.rotateAngleY = 0.19199F;

		thinSpiderLeg4 = new ModelRenderer(this, 18, 0);
		thinSpiderLeg4.addBox(-1F, -1F, -1F, 16, 1, 1, 0.0F);
		thinSpiderLeg4.setRotationPoint(4F, 15F, 1F);
		thinSpiderLeg4.rotateAngleX = -0.27925F;
		thinSpiderLeg4.rotateAngleY = -0.19199F;

		thinSpiderLeg5 = new ModelRenderer(this, 18, 0);
		thinSpiderLeg5.addBox(-15F, -1F, -1F, 16, 1, 1, 0.0F);
		thinSpiderLeg5.setRotationPoint(-4F, 15F, 0F);
		thinSpiderLeg5.rotateAngleX = -0.27925F;
		thinSpiderLeg5.rotateAngleY = 0.19199F;

		thinSpiderLeg6 = new ModelRenderer(this, 18, 0);
		thinSpiderLeg6.addBox(-1F, -1F, -1F, 16, 1, 1, 0.0F);
		thinSpiderLeg6.setRotationPoint(4F, 15F, 0F);
		thinSpiderLeg6.rotateAngleX = 0.27925F;
		thinSpiderLeg6.rotateAngleY = -0.19199F;

		thinSpiderLeg7 = new ModelRenderer(this, 18, 0);
		thinSpiderLeg7.addBox(-15F, -1F, -1F, 16, 1, 1, 0.0F);
		thinSpiderLeg7.setRotationPoint(-4F, 15F, -1F);
		thinSpiderLeg7.rotateAngleX = -0.57596F;
		thinSpiderLeg7.rotateAngleY = 0.19199F;

		thinSpiderLeg8 = new ModelRenderer(this, 18, 0);
		thinSpiderLeg8.addBox(-1F, -1F, -1F, 16, 1, 1, 0.0F);
		thinSpiderLeg8.setRotationPoint(4F, 15F, -1F);
		thinSpiderLeg8.rotateAngleX = 0.57596F;
		thinSpiderLeg8.rotateAngleY = -0.19199F;
	}

	private void initTinySpider()
	{
		tinySpiderHead = new ModelRenderer(this, 0, 8);
		tinySpiderHead.addBox(-3F, -2F, -4F, 4, 3, 4, 0.0F);
		tinySpiderHead.setRotationPoint(0F, 19F, -3F);

		tinySpiderBody = new ModelRenderer(this, 0, 0);
		tinySpiderBody.addBox(-3F, -3F, -3F, 4, 4, 4, 0.0F);
		tinySpiderBody.setRotationPoint(0F, 19F, 0F);

		tinySpiderRearEnd = new ModelRenderer(this, 0, 15);
		tinySpiderRearEnd.addBox(-3F, -4F, 0F, 6, 6, 6, 0.0F);
		tinySpiderRearEnd.setRotationPoint(-1F, 18F, 1F);

		tinySpiderLeg1 = new ModelRenderer(this, 18, 0);
		tinySpiderLeg1.addBox(-9F, -1F, -1F, 10, 1, 1, 0.0F);
		tinySpiderLeg1.setRotationPoint(-3F, 19F, 1F);
		tinySpiderLeg1.rotateAngleX = 0.57596F;
		tinySpiderLeg1.rotateAngleY = 0.19199F;

		tinySpiderLeg2 = new ModelRenderer(this, 18, 0);
		tinySpiderLeg2.addBox(-1F, -1F, -1F, 10, 1, 1, 0.0F);
		tinySpiderLeg2.setRotationPoint(1F, 19F, 1F);
		tinySpiderLeg2.rotateAngleX = -0.57596F;
		tinySpiderLeg2.rotateAngleY = -0.19199F;

		tinySpiderLeg3 = new ModelRenderer(this, 18, 0);
		tinySpiderLeg3.addBox(-9F, -1F, -1F, 10, 1, 1, 0.0F);
		tinySpiderLeg3.setRotationPoint(-3F, 19F, 0F);
		tinySpiderLeg3.rotateAngleX = 0.27925F;
		tinySpiderLeg3.rotateAngleY = 0.19199F;

		tinySpiderLeg4 = new ModelRenderer(this, 18, 0);
		tinySpiderLeg4.addBox(-1F, -1F, -1F, 10, 1, 1, 0.0F);
		tinySpiderLeg4.setRotationPoint(1F, 19F, 0F);
		tinySpiderLeg4.rotateAngleX = -0.27925F;
		tinySpiderLeg4.rotateAngleY = -0.19199F;

		tinySpiderLeg5 = new ModelRenderer(this, 18, 0);
		tinySpiderLeg5.addBox(-9F, -1F, -1F, 10, 1, 1, 0.0F);
		tinySpiderLeg5.setRotationPoint(-3F, 19F, -1F);
		tinySpiderLeg5.rotateAngleX = -0.27925F;
		tinySpiderLeg5.rotateAngleY = 0.19199F;

		tinySpiderLeg6 = new ModelRenderer(this, 18, 0);
		tinySpiderLeg6.addBox(-1F, -1F, -1F, 10, 1, 1, 0.0F);
		tinySpiderLeg6.setRotationPoint(1F, 19F, -1F);
		tinySpiderLeg6.rotateAngleX = 0.27925F;
		tinySpiderLeg6.rotateAngleY = -0.19199F;

		tinySpiderLeg7 = new ModelRenderer(this, 18, 0);
		tinySpiderLeg7.addBox(-10F, -1F, -1F, 10, 1, 1, 0.0F);
		tinySpiderLeg7.setRotationPoint(-2F, 19F, -2F);
		tinySpiderLeg7.rotateAngleX = -0.57596F;
		tinySpiderLeg7.rotateAngleY = 0.19199F;

		tinySpiderLeg8 = new ModelRenderer(this, 18, 0);
		tinySpiderLeg8.addBox(-1F, -1F, -1F, 10, 1, 1, 0.0F);
		tinySpiderLeg8.setRotationPoint(1F, 19F, -2F);
		tinySpiderLeg8.rotateAngleX = 0.57596F;
		tinySpiderLeg8.rotateAngleY = -0.19199F;
	}

	private void initLongLegSpider()
	{
		longLegSpiderBody = new ModelRenderer(this, 0, 0);
		longLegSpiderBody.addBox(-3F, -3F, -3F, 6, 6, 6);
		longLegSpiderBody.setRotationPoint(0F, 5F, 0F);

		longLegSpiderLeg8 = new ModelRenderer(this, 18, 0);
		longLegSpiderLeg8.addBox(-1F, -1F, -1F, 17, 1, 1);
		longLegSpiderLeg8.setRotationPoint(2.5F, 9F, -1F);
		longLegSpiderLeg8.setTextureSize(64, 32);
		longLegSpiderLeg8.mirror = true;
		setRotation(longLegSpiderLeg8, 0.2974289F, 0.5759587F, 1.230457F);
		longLegSpiderLeg6 = new ModelRenderer(this, 18, 0);
		longLegSpiderLeg6.addBox(-1F, -1F, -1F, 17, 1, 1);
		longLegSpiderLeg6.setRotationPoint(2.5F, 9F, 0F);
		longLegSpiderLeg6.setTextureSize(64, 32);
		longLegSpiderLeg6.mirror = true;
		setRotation(longLegSpiderLeg6, 0.5948578F, 0.2792527F, 1.22173F);
		longLegSpiderLeg4 = new ModelRenderer(this, 18, 0);
		longLegSpiderLeg4.addBox(-1F, -1F, -1F, 17, 1, 1);
		longLegSpiderLeg4.setRotationPoint(2F, 9F, 1F);
		longLegSpiderLeg4.setTextureSize(64, 32);
		longLegSpiderLeg4.mirror = true;
		setRotation(longLegSpiderLeg4, 0F, -0.2792527F, 1.22173F);
		longLegSpiderLeg2 = new ModelRenderer(this, 18, 0);
		longLegSpiderLeg2.addBox(-1F, -1F, -1F, 17, 1, 1);
		longLegSpiderLeg2.setRotationPoint(2F, 9F, 2F);
		longLegSpiderLeg2.setTextureSize(64, 32);
		longLegSpiderLeg2.mirror = true;
		setRotation(longLegSpiderLeg2, 0.2974289F, -0.5759587F, 1.230457F);
		longLegSpiderLeg7 = new ModelRenderer(this, 18, 0);
		longLegSpiderLeg7.addBox(-15F, -1F, -1F, 17, 1, 1);
		longLegSpiderLeg7.setRotationPoint(-2.5F, 10F, -1F);
		longLegSpiderLeg7.setTextureSize(64, 32);
		longLegSpiderLeg7.mirror = true;
		setRotation(longLegSpiderLeg7, 0F, -0.5759587F, -1.230457F);
		longLegSpiderLeg5 = new ModelRenderer(this, 18, 0);
		longLegSpiderLeg5.addBox(-15F, -1F, -1F, 17, 1, 1);
		longLegSpiderLeg5.setRotationPoint(-2.5F, 10F, 0F);
		longLegSpiderLeg5.setTextureSize(64, 32);
		longLegSpiderLeg5.mirror = true;
		setRotation(longLegSpiderLeg5, 0F, -0.2792527F, -1.22173F);
		longLegSpiderLeg3 = new ModelRenderer(this, 18, 0);
		longLegSpiderLeg3.addBox(-15F, -1F, -1F, 17, 1, 1);
		longLegSpiderLeg3.setRotationPoint(-2.5F, 10F, 1F);
		longLegSpiderLeg3.setTextureSize(64, 32);
		longLegSpiderLeg3.mirror = true;
		setRotation(longLegSpiderLeg3, 0F, 0.2792527F, -1.22173F);
		longLegSpiderLeg1 = new ModelRenderer(this, 18, 0);
		longLegSpiderLeg1.addBox(-15F, -1F, -1F, 18, 1, 1);
		longLegSpiderLeg1.setRotationPoint(-2.5F, 10F, 2F);
		longLegSpiderLeg1.setTextureSize(64, 32);
		longLegSpiderLeg1.mirror = true;
		setRotation(longLegSpiderLeg1, 0.3665191F, 0.5759587F, -1.230457F);
	}

	private void initFlatSpider()
	{
		flatSpiderHead = new ModelRenderer(this, 40, 12);
		flatSpiderHead.addBox(-4F, -4F, -8F, 6, 6, 6);
		flatSpiderHead.setRotationPoint(1F, 20F, -1F);
		flatSpiderHead.setTextureSize(64, 32);
		flatSpiderHead.mirror = true;
		setRotation(flatSpiderHead, 0F, 0F, 0F);
		flatSpiderBody = new ModelRenderer(this, 0, 0);
		flatSpiderBody.addBox(-3F, -3F, -3F, 6, 4, 5);
		flatSpiderBody.setRotationPoint(0F, 20F, 0F);
		flatSpiderBody.setTextureSize(64, 32);
		flatSpiderBody.mirror = true;
		setRotation(flatSpiderBody, 0F, 0F, 0F);
		flatSpiderRearEnd = new ModelRenderer(this, 0, 12);
		flatSpiderRearEnd.addBox(-5F, -4F, -6F, 12, 6, 13);
		flatSpiderRearEnd.setRotationPoint(-1F, 21F, 7F);
		flatSpiderRearEnd.setTextureSize(64, 32);
		flatSpiderRearEnd.mirror = true;
		setRotation(flatSpiderRearEnd, -0.1487144F, 0F, 0F);
		flatSpiderLeg8 = new ModelRenderer(this, 18, 0);
		flatSpiderLeg8.addBox(-1F, -1F, -1F, 16, 2, 2);
		flatSpiderLeg8.setRotationPoint(4F, 20F, -3F);
		flatSpiderLeg8.setTextureSize(64, 32);
		flatSpiderLeg8.mirror = true;
		setRotation(flatSpiderLeg8, 0F, 0.5759587F, 0.1919862F);
		flatSpiderLeg6 = new ModelRenderer(this, 18, 0);
		flatSpiderLeg6.addBox(-1F, -1F, -1F, 16, 2, 2);
		flatSpiderLeg6.setRotationPoint(4F, 20F, -2F);
		flatSpiderLeg6.setTextureSize(64, 32);
		flatSpiderLeg6.mirror = true;
		setRotation(flatSpiderLeg6, 0F, 0.2792527F, 0.1919862F);
		flatSpiderLeg4 = new ModelRenderer(this, 18, 0);
		flatSpiderLeg4.addBox(-1F, -1F, -1F, 16, 2, 2);
		flatSpiderLeg4.setRotationPoint(4F, 20F, -1F);
		flatSpiderLeg4.setTextureSize(64, 32);
		flatSpiderLeg4.mirror = true;
		setRotation(flatSpiderLeg4, 0F, -0.2792527F, 0.1919862F);
		flatSpiderLeg2 = new ModelRenderer(this, 18, 0);
		flatSpiderLeg2.addBox(-1F, -1F, -1F, 16, 2, 2);
		flatSpiderLeg2.setRotationPoint(4F, 20F, 0F);
		flatSpiderLeg2.setTextureSize(64, 32);
		flatSpiderLeg2.mirror = true;
		setRotation(flatSpiderLeg2, 0F, -0.5759587F, 0.1919862F);
		flatSpiderLeg7 = new ModelRenderer(this, 18, 0);
		flatSpiderLeg7.addBox(-15F, -1F, -1F, 16, 2, 2);
		flatSpiderLeg7.setRotationPoint(-4F, 20F, -3F);
		flatSpiderLeg7.setTextureSize(64, 32);
		flatSpiderLeg7.mirror = true;
		setRotation(flatSpiderLeg7, 0F, -0.5759587F, -0.1919862F);
		flatSpiderLeg5 = new ModelRenderer(this, 18, 0);
		flatSpiderLeg5.addBox(-15F, -1F, -1F, 16, 2, 2);
		flatSpiderLeg5.setRotationPoint(-4F, 20F, -2F);
		flatSpiderLeg5.setTextureSize(64, 32);
		flatSpiderLeg5.mirror = true;
		setRotation(flatSpiderLeg5, 0F, -0.2792527F, -0.1919862F);
		flatSpiderLeg3 = new ModelRenderer(this, 18, 0);
		flatSpiderLeg3.addBox(-15F, -1F, -1F, 16, 2, 2);
		flatSpiderLeg3.setRotationPoint(-4F, 20F, -1F);
		flatSpiderLeg3.setTextureSize(64, 32);
		flatSpiderLeg3.mirror = true;
		setRotation(flatSpiderLeg3, 0F, 0.2792527F, -0.1919862F);
		flatSpiderLeg1 = new ModelRenderer(this, 18, 0);
		flatSpiderLeg1.addBox(-15F, -1F, -1F, 16, 2, 2);
		flatSpiderLeg1.setRotationPoint(-4F, 20F, 0F);
		flatSpiderLeg1.setTextureSize(64, 32);
		flatSpiderLeg1.mirror = true;
		setRotation(flatSpiderLeg1, 0F, 0.5759587F, -0.1919862F);
	}

	private void initVillagerSpiderChest()
	{
		villagerSpiderChestRight = new ModelRenderer(this, 46, 16);
		villagerSpiderChestRight.addBox(0F, -4F, -4F, 1, 8, 8);
		villagerSpiderChestRight.setRotationPoint(-5.9F, 20F, 11F);
		villagerSpiderChestRight.setTextureSize(64, 32);
		villagerSpiderChestRight.mirror = true;
		setRotation(villagerSpiderChestRight, 0F, 0F, 0F);
		villagerSpiderChestLeft = new ModelRenderer(this, 46, 16);
		villagerSpiderChestLeft.addBox(0F, -4F, -4F, 1, 8, 8);
		villagerSpiderChestLeft.setRotationPoint(5.9F, 20F, 11F);
		villagerSpiderChestLeft.setTextureSize(64, 32);
		villagerSpiderChestLeft.mirror = true;
		setRotation(villagerSpiderChestLeft, 0F, 3.141593F, 0F);
	}

	private void setRotation(ModelRenderer model, float angleX, float angleY, float angleZ)
	{
		model.rotateAngleX = angleX;
		model.rotateAngleY = angleY;
		model.rotateAngleZ = angleZ;
	}

	private void renderHugeSpider(float partialTickTime)
	{
		hugeSpiderHead.render(partialTickTime);
		hugeSpiderRearEnd.render(partialTickTime);
		hugeSpiderBody.render(partialTickTime);
		hugeSpiderLeftBulk.render(partialTickTime);
		hugeSpiderTopBulk.render(partialTickTime);
		hugeSpiderBackBulk.render(partialTickTime);
		hugeSpiderRightBulk.render(partialTickTime);
		hugeSpiderLeg1.render(partialTickTime);
		hugeSpiderLeg2.render(partialTickTime);
		hugeSpiderLeg3.render(partialTickTime);
		hugeSpiderLeg4.render(partialTickTime);
		hugeSpiderLeg5.render(partialTickTime);
		hugeSpiderLeg6.render(partialTickTime);
		hugeSpiderLeg7.render(partialTickTime);
		hugeSpiderLeg8.render(partialTickTime);
	}

	private void renderRaisedSpider(float partialTickTime)
	{
		raisedSpiderHead.render(partialTickTime);
		raisedSpiderBody.render(partialTickTime);
		raisedSpiderRearEnd.render(partialTickTime);
		raisedSpiderLeg1.render(partialTickTime);
		raisedSpiderLeg2.render(partialTickTime);
		raisedSpiderLeg3.render(partialTickTime);
		raisedSpiderLeg4.render(partialTickTime);
		raisedSpiderLeg5.render(partialTickTime);
		raisedSpiderLeg6.render(partialTickTime);
		raisedSpiderLeg7.render(partialTickTime);
		raisedSpiderLeg8.render(partialTickTime);
	}

	private void renderDefaultSpider(float partialTickTime)
	{
		defaultSpiderHead.render(partialTickTime);
		defaultSpiderNeck.render(partialTickTime);
		defaultSpiderBody.render(partialTickTime);
		defaultSpiderLeg1.render(partialTickTime);
		defaultSpiderLeg2.render(partialTickTime);
		defaultSpiderLeg3.render(partialTickTime);
		defaultSpiderLeg4.render(partialTickTime);
		defaultSpiderLeg5.render(partialTickTime);
		defaultSpiderLeg6.render(partialTickTime);
		defaultSpiderLeg7.render(partialTickTime);
		defaultSpiderLeg8.render(partialTickTime);
	}

	private void renderThinSpider(float partialTickTime)
	{
		thinSpiderHead.render(partialTickTime);
		thinSpiderBody.render(partialTickTime);
		thinSpiderRearEnd.render(partialTickTime);
		thinSpiderLeg1.render(partialTickTime);
		thinSpiderLeg2.render(partialTickTime);
		thinSpiderLeg3.render(partialTickTime);
		thinSpiderLeg4.render(partialTickTime);
		thinSpiderLeg5.render(partialTickTime);
		thinSpiderLeg6.render(partialTickTime);
		thinSpiderLeg7.render(partialTickTime);
		thinSpiderLeg8.render(partialTickTime);
	}

	private void renderTinySpider(float partialTickTime)
	{
		tinySpiderHead.render(partialTickTime);
		tinySpiderBody.render(partialTickTime);
		tinySpiderRearEnd.render(partialTickTime);
		tinySpiderLeg1.render(partialTickTime);
		tinySpiderLeg2.render(partialTickTime);
		tinySpiderLeg3.render(partialTickTime);
		tinySpiderLeg4.render(partialTickTime);
		tinySpiderLeg5.render(partialTickTime);
		tinySpiderLeg6.render(partialTickTime);
		tinySpiderLeg7.render(partialTickTime);
		tinySpiderLeg8.render(partialTickTime);
	}

	private void renderLongLegSpider(float partialTickTime)
	{
		GL11.glPushMatrix();
		{
			GL11.glTranslated(0, 0.35D, 0);
			longLegSpiderBody.render(partialTickTime);
		}
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		{
			GL11.glTranslated(0, -0.05D, 0);
			GL11.glScaled(1, 1.5D, 1);
			longLegSpiderLeg1.render(partialTickTime);
			longLegSpiderLeg2.render(partialTickTime);
			longLegSpiderLeg3.render(partialTickTime);
			longLegSpiderLeg4.render(partialTickTime);
			longLegSpiderLeg5.render(partialTickTime);
			longLegSpiderLeg6.render(partialTickTime);
			longLegSpiderLeg7.render(partialTickTime);
			longLegSpiderLeg8.render(partialTickTime);
		}
		GL11.glPopMatrix();
	}

	private void renderTinyLongLegSpider(float partialTickTime)
	{
		GL11.glPushMatrix();
		{
			GL11.glScaled(0.5D, 0.5D, 0.5D);
			GL11.glTranslated(0.0D, 1.2D, 0.0D);
			renderLongLegSpider(partialTickTime);
		}
		GL11.glPopMatrix();
	}

	private void renderSwarmSpiders(Entity entity, float partialTickTime)
	{
		final EntityHatchedSpider spider = (EntityHatchedSpider) entity;

		if (spider.isOnLadder())
		{
			final Vec3 lookVector = spider.getLookVec();

			if (lookVector.xCoord <= -0.90 || lookVector.zCoord <= -0.90)
			{
				GL11.glRotatef(270, 1, 0, 0);
				GL11.glTranslated(0, -1.2, 0);
			}

			else if (lookVector.xCoord >= 0.90 || lookVector.zCoord >= 0.90)
			{
				GL11.glRotatef(-90, 1, 0, 0);
				GL11.glTranslated(0, -1.2, 0);
			}
		}

		if (spider.level == 1)
		{
			for (int pass = 0; pass < 9; pass++)
			{
				GL11.glPushMatrix();
				{
					GL11.glScaled(0.3D, 0.3D, 0.3D);
					GL11.glTranslated(0, 3.6D, 0D);

					switch (pass)
					{
						case 0:
							GL11.glTranslated(0D, 0.0D, -2.0D);
							break;
						case 1:
							GL11.glTranslated(0D, 0, 2.0D);
							break;
						case 2:
							GL11.glTranslated(1.8D, 0, 0D);
							break;
						case 3:
							GL11.glTranslated(-1.8D, 0, 0D);
							break;
						case 4:
							GL11.glTranslated(1.8D, 0, -2.0D);
							break;
						case 5:
							GL11.glTranslated(-1.8D, 0, -2.0D);
							break;
						case 6:
							GL11.glTranslated(1.8D, 0, 2.0D);
							break;
						case 7:
							GL11.glTranslated(-1.8D, 0, 2.0D);
							break;
						default:
							break;
					}
					renderDefaultSpider(partialTickTime);
				}
				GL11.glPopMatrix();
			}
		}

		else if (spider.level == 2)
		{
			for (int pass = 0; pass < 13; pass++)
			{
				GL11.glPushMatrix();
				{
					GL11.glScaled(0.2D, 0.2D, 0.2D);
					GL11.glTranslated(0, 6.1D, 0D);

					switch (pass)
					{
						case 0:
							GL11.glTranslated(0D, 0.0D, -2.0D);
							break;
						case 1:
							GL11.glTranslated(0D, 0, 2.0D);
							break;
						case 2:
							GL11.glTranslated(1.8D, 0, 0D);
							break;
						case 3:
							GL11.glTranslated(-1.8D, 0, 0D);
							break;
						case 4:
							GL11.glTranslated(1.8D, 0, -2.0D);
							break;
						case 5:
							GL11.glTranslated(-1.8D, 0, -2.0D);
							break;
						case 6:
							GL11.glTranslated(1.8D, 0, 2.0D);
							break;
						case 7:
							GL11.glTranslated(3.5D, 0, 0.0D);
							break;
						case 8:
							GL11.glTranslated(-3.5D, 0, 0.0D);
							break;
						case 9:
							GL11.glTranslated(0D, 0, 0D);
							break;
						case 10:
							GL11.glTranslated(0D, 0, 4D);
							break;
						case 11:
							GL11.glTranslated(-1.8D, 0, 2.0D);
							break;
						case 12:
							GL11.glTranslated(0D, 0, -4D);
							break;
						default:
							break;
					}
					renderDefaultSpider(partialTickTime);
				}
				GL11.glPopMatrix();
			}
		}

		else if (spider.level == 3)
		{
			for (int pass = 0; pass < 25; pass++)
			{
				GL11.glPushMatrix();
				{
					GL11.glScaled(0.15D, 0.15D, 0.15D);
					GL11.glTranslated(0, 8.6D, 0D);

					switch (pass)
					{
						case 0:
							GL11.glTranslated(0D, 0.0D, -2.0D);
							break;
						case 1:
							GL11.glTranslated(0D, 0, 2.0D);
							break;
						case 2:
							GL11.glTranslated(1.8D, 0, 0D);
							break;
						case 3:
							GL11.glTranslated(-1.8D, 0, 0D);
							break;
						case 4:
							GL11.glTranslated(1.8D, 0, -2.0D);
							break;
						case 5:
							GL11.glTranslated(-1.8D, 0, -2.0D);
							break;
						case 6:
							GL11.glTranslated(1.8D, 0, 2.0D);
							break;
						case 7:
							GL11.glTranslated(3.5D, 0, 0.0D);
							break;
						case 8:
							GL11.glTranslated(-3.5D, 0, 0.0D);
							break;
						case 9:
							GL11.glTranslated(0D, 0, 0D);
							break;
						case 10:
							GL11.glTranslated(0D, 0, 4D);
							break;
						case 11:
							GL11.glTranslated(-1.8D, 0, 2.0D);
							break;
						case 12:
							GL11.glTranslated(0D, 0, -4D);
							break;
						case 13:
							GL11.glTranslated(1.8D, 0, -4D);
							break;
						case 14:
							GL11.glTranslated(-1.8D, 0, -4D);
							break;
						case 15:
							GL11.glTranslated(3.5D, 0, -4D);
							break;
						case 16:
							GL11.glTranslated(-3.5D, 0, -4D);
							break;
						case 17:
							GL11.glTranslated(-3.5D, 0, -2D);
							break;
						case 18:
							GL11.glTranslated(-3.5D, 0, 4D);
							break;
						case 19:
							GL11.glTranslated(-3.5D, 0, 2D);
							break;
						case 20:
							GL11.glTranslated(1.8D, 0, 4D);
							break;
						case 21:
							GL11.glTranslated(3.5D, 0, 4D);
							break;
						case 22:
							GL11.glTranslated(3.5D, 0, 2D);
							break;
						case 23:
							GL11.glTranslated(3.5D, 0, -2D);
							break;
						case 24:
							GL11.glTranslated(-1.8D, 0, 4D);
							break;
						default:
							break;
					}
					renderDefaultSpider(partialTickTime);
				}
				GL11.glPopMatrix();
			}
		}
	}

	private void renderFlatSpider(float partialTickTime)
	{
		GL11.glPushMatrix();
		{
			GL11.glScalef(1F, 0.8F, 1F);
			GL11.glTranslated(0D, 0.05D, 0D);

			flatSpiderHead.render(partialTickTime);
			flatSpiderBody.render(partialTickTime);
			flatSpiderRearEnd.render(partialTickTime);
			flatSpiderLeg8.render(partialTickTime);
			flatSpiderLeg6.render(partialTickTime);
			flatSpiderLeg4.render(partialTickTime);
			flatSpiderLeg2.render(partialTickTime);
			flatSpiderLeg7.render(partialTickTime);
			flatSpiderLeg5.render(partialTickTime);
			flatSpiderLeg3.render(partialTickTime);
			flatSpiderLeg1.render(partialTickTime);
		}
		GL11.glPopMatrix();
	}

	private void renderVillagerSpiderChest(float partialTickTime)
	{
		GL11.glPushMatrix();
		{
			GL11.glTranslated(0.0D, -0.30D, 0.0D);
			villagerSpiderChestRight.render(partialTickTime);
			villagerSpiderChestLeft.render(partialTickTime);
		}
		GL11.glPopMatrix();
	}
}

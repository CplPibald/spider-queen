/*******************************************************************************
 * EntityWeb.java
 * Copyright (c) 2014 Radix-Shock Entertainment.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/

package sqr.entity;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import sqr.core.SpiderQueen;
import sqr.enums.EnumCocoonType;

import com.radixshock.radixcore.logic.LogicHelper;
import com.radixshock.radixcore.logic.Point3D;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityWeb extends Entity implements IProjectile
{
	private int				ticksInAir;
	private int				type;
	private boolean			doBlockSpawn;

	public EntityLivingBase	shooter;
	public double			accelerationX;
	public double			accelerationY;
	public double			accelerationZ;

	public EntityWeb(World worldObj)
	{
		super(worldObj);
	}

	public EntityWeb(EntityPlayer player)
	{
		this(player.worldObj);

		final Vec3 vec = player.getLookVec();

		shooter = player;
		setPosition(player.posX, player.posY + 1.4F, player.posZ);
		accelerationX = vec.xCoord * 1.5;
		accelerationY = vec.yCoord * 1.5;
		accelerationZ = vec.zCoord * 1.5;

		motionX = accelerationX;
		motionY = accelerationY;
		motionZ = accelerationZ;

		doBlockSpawn = true;
	}

	public EntityWeb(EntityPlayer player, int type)
	{
		this(player);
		this.type = type;
	}

	public EntityWeb(EntityLivingBase shooter, EntityLivingBase target, float speed)
	{
		this(shooter.worldObj);
		this.shooter = shooter;
		renderDistanceWeight = 10.0D;

		posY = shooter.posY + shooter.getEyeHeight() - 0.10000000149011612D;
		final double deltaX = target.posX - shooter.posX;
		final double deltaY = target.boundingBox.minY + target.height / 3.0F - posY;
		final double deltaZ = target.posZ - shooter.posZ;
		final double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);

		if (distanceXZ >= 1.0E-7D)
		{
			final float rotationYaw = (float) (Math.atan2(deltaZ, deltaX) * 180.0D / Math.PI) - 90.0F;
			final float rotationPitch = (float) -(Math.atan2(deltaY, distanceXZ) * 180.0D / Math.PI);
			final double modX = deltaX / distanceXZ;
			final double modY = deltaZ / distanceXZ;

			setLocationAndAngles(shooter.posX + modX, posY, shooter.posZ + modY, rotationYaw, rotationPitch);
			yOffset = 0.0F;

			final float modDeltaY = (float) distanceXZ * 0.2F;
			setThrowableHeading(deltaX, deltaY - modDeltaY, deltaZ, speed, 16F);
		}
	}

	@Override
	protected void entityInit()
	{
		// No init.
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean isInRangeToRenderDist(double distance)
	{
		double weightedLength = boundingBox.getAverageEdgeLength() * 4.0D;
		weightedLength *= 64.0D;
		return distance < weightedLength * weightedLength;
	}

	@Override
	public void onUpdate()
	{
		if (!worldObj.isRemote && (ticksInAir > 150 || shooter != null && shooter.isDead || !worldObj.blockExists((int) posX, (int) posY, (int) posZ)))
		{
			setDead();
		}

		else
		{
			super.onUpdate();

			if (!worldObj.isRemote)
			{
				if (shooter != null && getDistanceToEntity(shooter) > 150.0D)
				{
					setDead();
					return;
				}

				if (type == 2 && worldObj.getBlock((int) posX, (int) posY, (int) posZ) == Blocks.lava)
				{
					for (final Point3D point : LogicHelper.getNearbyBlocks_StartAtBottom(this, Blocks.lava, 2))
					{
						worldObj.setBlock(point.iPosX, point.iPosY, point.iPosZ, Blocks.fire);
					}

					setDead();
				}
			}

			updateCollision();
			updateMotion();
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		// No data to write.
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		// No data to read.
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return true;
	}

	@Override
	public float getCollisionBorderSize()
	{
		return 1.0F;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float damageAmount)
	{
		if (isEntityInvulnerable())
		{
			return false;
		}

		else
		{
			setBeenAttacked();

			if (damageSource.getEntity() != null)
			{
				final Vec3 sourceLookVector = damageSource.getEntity().getLookVec();

				if (sourceLookVector != null)
				{
					motionX = sourceLookVector.xCoord;
					motionY = sourceLookVector.yCoord;
					motionZ = sourceLookVector.zCoord;
					accelerationX = motionX * 0.1D;
					accelerationY = motionY * 0.1D;
					accelerationZ = motionZ * 0.1D;
				}

				return true;
			}

			else
			{
				return false;
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public float getShadowSize()
	{
		return 0.0F;
	}

	@Override
	public float getBrightness(float unknown)
	{
		return 1.0F;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getBrightnessForRender(float unknown)
	{
		return 15728880;
	}

	@Override
	public void setThrowableHeading(double posX, double posY, double posZ, float speed, float unknown)
	{
		final float distanceXYZ = MathHelper.sqrt_double(posX * posX + posY * posY + posZ * posZ);
		posX /= distanceXYZ;
		posY /= distanceXYZ;
		posZ /= distanceXYZ;

		posX += rand.nextGaussian() * (rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * unknown;
		posY += rand.nextGaussian() * (rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * unknown;
		posZ += rand.nextGaussian() * (rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * unknown;

		posX *= speed;
		posY *= speed;
		posZ *= speed;

		motionX = posX;
		motionZ = posZ;

		final float distanceXZ = MathHelper.sqrt_double(posX * posX + posZ * posZ);
		prevRotationYaw = rotationYaw = (float) (Math.atan2(posX, posZ) * 180.0D / Math.PI);
		prevRotationPitch = rotationPitch = (float) (Math.atan2(posY, distanceXZ) * 180.0D / Math.PI);
	}

	@Override
	public void setDead()
	{
		super.setDead();

		if (type == 2)
		{
			for (int i = -2; i < 2; i++)
			{
				worldObj.getBlock((int) posX, (int) posY + i, (int) posZ);

				for (final Point3D point : LogicHelper.getNearbyBlocks_StartAtBottom(this, Blocks.lava, 2))
				{
					worldObj.setBlock(point.iPosX, point.iPosY, point.iPosZ, Blocks.fire);
				}
			}
		}
	}

	private void updateCollision()
	{
		Vec3 currentVector = Vec3.createVectorHelper(posX, posY, posZ);
		Vec3 nextVector = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
		MovingObjectPosition collisionPosition = worldObj.rayTraceBlocks(currentVector, nextVector);
		currentVector = Vec3.createVectorHelper(posX, posY, posZ);
		nextVector = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);

		if (collisionPosition != null)
		{
			nextVector = Vec3.createVectorHelper(collisionPosition.hitVec.xCoord, collisionPosition.hitVec.yCoord, collisionPosition.hitVec.zCoord);
		}

		Entity collidedEntity = null;
		final List entitiesInAABB = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
		double lastDistance = 0.0D;

		for (int counter = 0; counter < entitiesInAABB.size(); ++counter)
		{
			final Entity entityInList = (Entity) entitiesInAABB.get(counter);

			if (entityInList.canBeCollidedWith() && (!entityInList.isEntityEqual(shooter) || ticksInAir >= 25))
			{
				final AxisAlignedBB AABB = entityInList.boundingBox.expand(0.3D, 0.3D, 0.3D);
				final MovingObjectPosition entityCollisionPosition = AABB.calculateIntercept(currentVector, nextVector);

				if (entityCollisionPosition != null)
				{
					final double thisDistance = currentVector.distanceTo(entityCollisionPosition.hitVec);

					if (thisDistance < lastDistance || lastDistance == 0.0D)
					{
						collidedEntity = entityInList;
						lastDistance = thisDistance;
					}
				}
			}
		}

		if (collidedEntity != null)
		{
			collisionPosition = new MovingObjectPosition(collidedEntity);
		}

		if (collisionPosition != null)
		{
			onImpact(collisionPosition);
		}
	}

	private void updateMotion()
	{
		ticksInAir++;

		posX += motionX;
		posY += motionY;
		posZ += motionZ;
		final float f1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		rotationYaw = (float) (Math.atan2(motionZ, motionX) * 180.0D / Math.PI) + 90.0F;

		for (rotationPitch = (float) (Math.atan2(f1, motionY) * 180.0D / Math.PI) - 90.0F; rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F)
		{
			;
		}

		while (rotationPitch - prevRotationPitch >= 180.0F)
		{
			prevRotationPitch += 360.0F;
		}

		while (rotationYaw - prevRotationYaw < -180.0F)
		{
			prevRotationYaw -= 360.0F;
		}

		while (rotationYaw - prevRotationYaw >= 180.0F)
		{
			prevRotationYaw += 360.0F;
		}

		rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
		rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
		float motionFactor = 0.95F;

		if (isInWater())
		{
			for (int counter = 0; counter < 4; ++counter)
			{
				final float speedFactor = 0.25F;
				worldObj.spawnParticle("bubble", posX - motionX * speedFactor, posY - motionY * speedFactor, posZ - motionZ * speedFactor, motionX, motionY, motionZ);
			}

			motionFactor = 0.8F;
		}

		motionX += accelerationX;
		motionY += accelerationY;
		motionZ += accelerationZ;
		motionX *= motionFactor;
		motionY *= motionFactor;
		motionZ *= motionFactor;

		setPosition(posX, posY, posZ);
	}

	private void onImpact(MovingObjectPosition impactPoint)
	{
		if (!worldObj.isRemote)
		{
			if (impactPoint.entityHit != null && impactPoint.entityHit instanceof EntityLivingBase)
			{
				final EnumCocoonType cocoonType = EnumCocoonType.getCocoonTypeByCapturedClass(impactPoint.entityHit.getClass());
				final EntityLivingBase entityHit = (EntityLivingBase) impactPoint.entityHit;
				final float attackPower = type == 1 ? 4.0F : type == 2 ? 0.0F : 0.0F;
				entityHit.attackEntityFrom(DamageSource.causeMobDamage(shooter), attackPower);

				if (type == 2)
				{
					entityHit.setFire(5);
					setDead();
				}

				if (cocoonType != null)
				{
					if (entityHit.getHealth() > 0.4F)
					{
						final Random rand = new Random();
						final int intHealth = (int) entityHit.getHealth();
						final int captureDifficulty = cocoonType.getEntityCatchDifficulty();

						if (captureDifficulty != 0 && rand.nextInt(intHealth / captureDifficulty + 1) != 0)
						{
							setToInactive();
							setNoBlockSpawn();
							return;
						}

						else
						{
							EntityCocoon entityCocoon = null;

							if (shooter instanceof EntityPlayer && type != 1 && type != 2)
							{
								final EntityPlayer player = (EntityPlayer) shooter;
								player.triggerAchievement(SpiderQueen.getInstance().achievementCocoonSomething);
							}

							if (type == 2)
							{
								if (impactPoint.entityHit instanceof EntityGhast)
								{
									entityCocoon = new EntityCocoon(worldObj, cocoonType, true);

									if (shooter instanceof EntityPlayer)
									{
										final EntityPlayer player = (EntityPlayer) shooter;
										player.triggerAchievement(SpiderQueen.getInstance().achievementCocoonGhast);
									}
								}
							}

							else if (type == 0)
							{
								if (impactPoint.entityHit instanceof EntityGhast)
								{
									return;
								}

								else
								{
									entityCocoon = new EntityCocoon(worldObj, cocoonType);
								}
							}

							if (entityCocoon != null)
							{
								entityCocoon.setLocationAndAngles(entityHit.posX, entityHit.posY, entityHit.posZ, entityHit.rotationYaw, entityHit.rotationPitch);
								worldObj.spawnEntityInWorld(entityCocoon);
								entityHit.setDead();
								setDead();

								if (shooter instanceof EntityHatchedSpider)
								{
									final EntityHatchedSpider spider = (EntityHatchedSpider) shooter;
									spider.killsUntilLevelUp--;
									spider.tryLevelUp();
								}
							}
						}
					}
				}
			}

			else
			// Hit a block.
			{
				final Block blockHit = worldObj.getBlock(impactPoint.blockX, impactPoint.blockY, impactPoint.blockZ);
				int impactX = impactPoint.blockX;
				int impactY = impactPoint.blockY;
				int impactZ = impactPoint.blockZ;

				if (blockHit != SpiderQueen.getInstance().blockWebSide && blockHit != SpiderQueen.getInstance().blockWebGround && 
						blockHit != SpiderQueen.getInstance().blockPoisonWebSide && blockHit != SpiderQueen.getInstance().blockPoisonWebGround)
				{
					if (blockHit == Blocks.tallgrass && type == 2)
					{
						worldObj.setBlock(impactX, impactY, impactZ, Blocks.fire);
						setDead();
						return;
					}

					else if (blockHit == Blocks.tallgrass && type != 2) { return; }

					else if (blockHit == Blocks.snow_layer)
					{
						if (doBlockSpawn)
						{
							worldObj.setBlock(impactX, impactY, impactZ, SpiderQueen.getInstance().blockWebGround, 0, 2);
							return;
						}
					}
					
					if (doBlockSpawn)
					{
						switch (impactPoint.sideHit)
						{
							case 0:
								--impactY;
								break;
							case 1:
								++impactY;
								break;
							case 2:
								--impactZ;
								break;
							case 3:
								++impactZ;
								break;
							case 4:
								--impactX;
								break;
							case 5:
								++impactX;
						}

						if (worldObj.isAirBlock(impactX, impactY, impactZ))
						{
							int meta = 0;
							switch (impactPoint.sideHit)
							{
								case 0:
									meta = 0;
									break;
								case 1:
									meta = -1;
									break;
								case 2:
									meta = 1;
									break;
								case 3:
									meta = 4;
									break;
								case 4:
									meta = 8;
									break;
								case 5:
									meta = 2;
									break;
							}

							if (meta == -1)
							{
								if (type == 0)
								{
									worldObj.setBlock(impactX, impactY, impactZ, SpiderQueen.getInstance().blockWebGround, 0, 2);
								}

								else if (type == 1)
								{
									worldObj.setBlock(impactX, impactY, impactZ, SpiderQueen.getInstance().blockPoisonWebGround, 0, 2);
								}
								
								else if (type == 2)
								{
									worldObj.setBlock(impactX, impactY, impactZ, SpiderQueen.getInstance().blockFlameWebFull);
								}
							}

							else
							{
								if (type == 0)
								{
									worldObj.setBlock(impactX, impactY, impactZ, SpiderQueen.getInstance().blockWebSide, meta, 2);
								}

								else if (type == 1)
								{
									worldObj.setBlock(impactX, impactY, impactZ, SpiderQueen.getInstance().blockPoisonWebSide, meta, 2);
								}
								
								else if (type == 2)
								{
									worldObj.setBlock(impactX, impactY, impactZ, SpiderQueen.getInstance().blockFlameWebFull);
								}
							}
						}

						setDead();
					}
				}

				else if (blockHit == SpiderQueen.getInstance().blockWebGround || blockHit == SpiderQueen.getInstance().blockWebSide)
				{
					if (type == 0)
					{
						worldObj.setBlock(impactX, impactY, impactZ, SpiderQueen.getInstance().blockWebFull);
					}

					else if (type == 1)
					{
						worldObj.setBlock(impactX, impactY, impactZ, SpiderQueen.getInstance().blockPoisonWebFull);
					}
					
					else if (type == 2)
					{
						worldObj.setBlock(impactX, impactY, impactZ, Blocks.fire);
					}
				}
				
				else if (blockHit == SpiderQueen.getInstance().blockPoisonWebGround || blockHit == SpiderQueen.getInstance().blockPoisonWebSide)
				{
					if (type == 0)
					{
						worldObj.setBlock(impactX, impactY, impactZ, SpiderQueen.getInstance().blockPoisonWebFull);
					}

					else if (type == 1)
					{
						worldObj.setBlock(impactX, impactY, impactZ, SpiderQueen.getInstance().blockPoisonWebFull);
					}
					
					else if (type == 2)
					{
						worldObj.setBlock(impactX, impactY, impactZ, Blocks.fire);
					}
				}
			}
		}
	}

	private void setToInactive()
	{
		final Random rand = new Random();
		if (rand.nextInt(2) == 0)
		{
			motionX = motionX * -0.2F;
		}
		if (rand.nextInt(2) == 0)
		{
			motionY = motionY * -0.2F;
		}
		if (rand.nextInt(2) == 0)
		{
			motionZ = motionZ * -0.2F;
		}
	}

	private void setNoBlockSpawn()
	{
		doBlockSpawn = false;
	}
}

/*******************************************************************************
 * BlockWebGround.java
 * Copyright (c) 2014 Radix-Shock Entertainment.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/

package sqr.blocks;

import net.minecraft.world.IBlockAccess;

public class BlockWebGround extends BlockWeb
{
	public BlockWebGround(int type)
	{
		super(type);
	}

	@Override
	public int getRenderType()
	{
		return 23;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int posX, int posY, int posZ)
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
	}
}

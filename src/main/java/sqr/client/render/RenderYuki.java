package sqr.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import sqr.client.model.ModelYuki;

public class RenderYuki extends RenderLiving
{
	private final ResourceLocation texture;
	
    public RenderYuki()
    {
        super(new ModelYuki(), 1.0F);
        setRenderPassModel(new ModelYuki());
        texture = new ResourceLocation("sqr:textures/entities/yuki.png");
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return texture;
	}
}
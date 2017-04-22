package de.dogedev.ld38.ashley;

import com.badlogic.ashley.core.ComponentMapper;
import de.dogedev.ld38.ashley.components.PositionComponent;
import de.dogedev.ld38.ashley.components.RenderComponent;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class ComponentMappers {

    public static final ComponentMapper<PositionComponent> position = ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<RenderComponent> render = ComponentMapper.getFor(RenderComponent.class);
}

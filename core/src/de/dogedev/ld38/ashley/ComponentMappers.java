package de.dogedev.ld38.ashley;

import com.badlogic.ashley.core.ComponentMapper;
import de.dogedev.ld38.ashley.components.*;

/**
 * Created by elektropapst on 22.04.2017.
 */
public class ComponentMappers {

    public static final ComponentMapper<PositionComponent> position = ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<MovementComponent> movement = ComponentMapper.getFor(MovementComponent.class);
    public static final ComponentMapper<TilePositionComponent> tilePos = ComponentMapper.getFor(TilePositionComponent.class);
    public static final ComponentMapper<RenderComponent> render = ComponentMapper.getFor(RenderComponent.class);
    public static final ComponentMapper<SpawnComponent> spawn = ComponentMapper.getFor(SpawnComponent.class);
    public static final ComponentMapper<LookComponent> look = ComponentMapper.getFor(LookComponent.class);
    public static final ComponentMapper<UnitComponent> unit = ComponentMapper.getFor(UnitComponent.class);
    public static final ComponentMapper<GridComponent> grid = ComponentMapper.getFor(GridComponent.class);

}

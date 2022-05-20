package org.astemir.smpl.utils;

import org.astemir.smpl.SMPLItems;
import org.bukkit.entity.Entity;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class EntityHandler {

    private CopyOnWriteArrayList<EntityRunnable> handledEntities = new CopyOnWriteArrayList();


    public static EntityHandler getInstance(){
        return SMPLItems.getPlugin().getEntityHandler();
    }

    public EntityRunnable watchEntity(EntityRunnable runnable){
        handledEntities.add(runnable);
        return runnable;
    }

    public void update(long ticks){
        for (EntityRunnable runnable : handledEntities) {
            if (runnable.getEntity() == null){
                runnable.runOut(runnable.getEntity(),ticks);
                handledEntities.remove(runnable);
            }else
            if (runnable.isCancelled() || runnable.getEntity().isDead()){
                runnable.runOut(runnable.getEntity(),ticks);
                handledEntities.remove(runnable);
            }else {
                if (runnable.getEndLife() == -1) {
                    runnable.run(runnable.getEntity(), ticks);
                }else{
                    if (ticks < runnable.getEndLife()){
                        runnable.run(runnable.getEntity(), ticks);
                    }else{
                        runnable.runOut(runnable.getEntity(),ticks);
                        runnable.getEntity().remove();
                        handledEntities.remove(runnable);
                    }
                }
            }
        }
    }

    public abstract static class EntityRunnable{

        private Entity entity;
        private boolean cancelled = false;
        private long endLife = -1;

        public EntityRunnable(Entity entity) {
            this.entity = entity;
        }

        public void runOut(Entity entity, long ticks){}

        public Entity getEntity() {
            return entity;
        }

        public void cancel(){
            this.cancelled = true;
        }

        public boolean isCancelled() {
            return cancelled;
        }

        public abstract void run(Entity entity, long ticks);


        public long getEndLife() {
            return endLife;
        }

        public EntityRunnable lifespan(int life){
            endLife = SMPLItems.getPlugin().getTicks()+life;
            return this;
        }
    }
}

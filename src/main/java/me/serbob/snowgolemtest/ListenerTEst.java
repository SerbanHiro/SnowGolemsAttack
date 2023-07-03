package me.serbob.snowgolemtest;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.util.Vector;

import java.util.List;

public class ListenerTEst implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Snowman) {
            Snowman snowman = (Snowman) event.getEntity();
            if (event.getDamager() instanceof Player || event.getDamager() instanceof Projectile) {
                Player player = null;
                if (event.getDamager() instanceof Player) {
                    player = (Player) event.getDamager();
                } else if (event.getDamager() instanceof Projectile) {
                    Projectile projectile = (Projectile) event.getDamager();
                    if (projectile.getShooter() instanceof Player) {
                        player = (Player) projectile.getShooter();
                    }
                }
                if (player != null) {
                    snowman.setTarget(player);
                    snowman.setAI(true);
                }
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball) {
            Snowball snowball = (Snowball) event.getEntity();
            if (snowball.getShooter() instanceof Snowman) {
                if (event.getHitEntity() instanceof Player) {
                    Player player = (Player) event.getHitEntity();
                    player.damage(SnowGolemTest.instance.getConfig().getInt("damage")); //deals 5 hearts of damage
                }
            }
        }
    }
    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof Snowball) {
            Snowball snowball = (Snowball) event.getEntity();
            if (snowball.getShooter() instanceof Snowman) {
                Player shooter = (Player) ((Snowman) snowball.getShooter()).getMetadata("owner").get(0).value();
                Player target = getTargetPlayer(snowball.getLocation(), 10, shooter);
                if (target != null) {
                    Location snowballLoc = snowball.getLocation();
                    Location targetLoc = target.getEyeLocation();
                    Vector direction = targetLoc.clone().subtract(snowballLoc).toVector();
                    snowball.setVelocity(direction);
                }
            }
        }
    }

    private Player getTargetPlayer(Location location, double radius, Player shooter) {
        List<Entity> nearbyEntities = location.getWorld().getEntities();
        Player target = null;
        double closestDistance = Double.MAX_VALUE;
        for (Entity entity : nearbyEntities) {
            if (entity instanceof Player && !entity.equals(shooter)) {
                double distance = entity.getLocation().distance(location);
                if (distance < radius && distance < closestDistance) {
                    target = (Player) entity;
                    closestDistance = distance;
                }
            }
        }
        return target;
    }
}

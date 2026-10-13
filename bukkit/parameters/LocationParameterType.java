package revxrsal.commands.bukkit.parameters;

import com.google.common.base.Preconditions;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.exception.MissingLocationParameterException;
import revxrsal.commands.exception.CommandErrorException;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.stream.MutableStringStream;
import revxrsal.commands.util.Lazy;

public final class LocationParameterType implements ParameterType<BukkitCommandActor, Location> {
   public Location parse(@NotNull MutableStringStream input, @NotNull ExecutionContext<BukkitCommandActor> context) {
      return input.peek() == '^' ? this.parseLocal(input, context.actor()) : this.parseWorld(input, context.actor());
   }

   private void consumeSpace(@NotNull MutableStringStream input, MissingLocationParameterException.@NotNull MissingAxis missingAxis) {
      if (input.hasFinished()) {
         throw new MissingLocationParameterException(input.peekString(), missingAxis);
      } else {
         if (input.peek() == ' ') {
            input.moveForward();
         }

      }
   }

   private Location parseWorld(@NotNull MutableStringStream input, @NotNull BukkitCommandActor actor) {
      Supplier<Location> actorLocation = Lazy.<Location>of(() -> actor.requirePlayer().getLocation());
      double x = this.readWorldCoordinate(input, () -> ((Location)actorLocation.get()).getX());
      this.consumeSpace(input, MissingLocationParameterException.MissingAxis.Y);
      double y = this.readWorldCoordinate(input, () -> ((Location)actorLocation.get()).getY());
      this.consumeSpace(input, MissingLocationParameterException.MissingAxis.Z);
      double z = this.readWorldCoordinate(input, () -> ((Location)actorLocation.get()).getZ());
      World world = actor.isPlayer() ? ((Location)actorLocation.get()).getWorld() : Bukkit.getWorld("world");
      return new Location(world, x, y, z);
   }

   private double readWorldCoordinate(@NotNull MutableStringStream input, DoubleSupplier relativeToSupplier) {
      if (input.peek() == '~') {
         double relativeTo = relativeToSupplier.getAsDouble();
         input.moveForward();
         if (!input.hasFinished() && !Character.isWhitespace(input.peek())) {
            relativeTo += input.readDouble();
         }

         return relativeTo;
      } else {
         return input.readDouble();
      }
   }

   private Location parseLocal(@NotNull MutableStringStream input, @NotNull BukkitCommandActor actor) {
      Location actorLocation = actor.requirePlayer().getLocation();
      double x = this.readLocalCoordinate(input);
      this.consumeSpace(input, MissingLocationParameterException.MissingAxis.Y);
      double y = this.readLocalCoordinate(input);
      this.consumeSpace(input, MissingLocationParameterException.MissingAxis.Z);
      double z = this.readLocalCoordinate(input);
      Vector vector = this.getLocal(actorLocation, new Vector(x, y, z));
      return new Location(actorLocation.getWorld(), vector.getX(), vector.getY(), vector.getZ());
   }

   private double readLocalCoordinate(@NotNull MutableStringStream input) {
      if (input.read() != '^') {
         throw new CommandErrorException("Expected '^'.", new Object[0]);
      } else {
         return !input.hasFinished() && !Character.isWhitespace(input.peek()) ? input.readDouble() : (double)0.0F;
      }
   }

   private Vector getLocal(Location reference, Vector local) {
      Vector axisBase = new Vector(0, 0, 1);
      Vector axisLeft = this.rotateAroundY(axisBase.clone(), Math.toRadians((double)(-reference.getYaw() + 90.0F)));
      Vector axisUp = this.rotateAroundNonUnitAxis(reference.getDirection().clone(), axisLeft, Math.toRadians((double)-90.0F));
      Vector sway = axisLeft.clone().normalize().multiply(local.getX());
      Vector heave = axisUp.clone().normalize().multiply(local.getY());
      Vector surge = reference.getDirection().clone().multiply(local.getZ());
      return (new Vector(reference.getX(), reference.getY(), reference.getZ())).add(sway).add(heave).add(surge);
   }

   private @NotNull Vector rotateAroundY(Vector vector, double angle) {
      double angleCos = Math.cos(angle);
      double angleSin = Math.sin(angle);
      double x = angleCos * vector.getX() + angleSin * vector.getZ();
      double z = -angleSin * vector.getX() + angleCos * vector.getZ();
      return vector.setX(x).setZ(z);
   }

   private @NotNull Vector rotateAroundNonUnitAxis(@NotNull Vector vector, @NotNull Vector axis, double angle) throws IllegalArgumentException {
      Preconditions.checkArgument(axis != null, "The provided axis vector was null");
      double x = vector.getX();
      double y = vector.getY();
      double z = vector.getZ();
      double x2 = axis.getX();
      double y2 = axis.getY();
      double z2 = axis.getZ();
      double cosTheta = Math.cos(angle);
      double sinTheta = Math.sin(angle);
      double dotProduct = vector.dot(axis);
      double xPrime = x2 * dotProduct * ((double)1.0F - cosTheta) + x * cosTheta + (-z2 * y + y2 * z) * sinTheta;
      double yPrime = y2 * dotProduct * ((double)1.0F - cosTheta) + y * cosTheta + (z2 * x - x2 * z) * sinTheta;
      double zPrime = z2 * dotProduct * ((double)1.0F - cosTheta) + z * cosTheta + (-y2 * x + x2 * y) * sinTheta;
      return vector.setX(xPrime).setY(yPrime).setZ(zPrime);
   }
}

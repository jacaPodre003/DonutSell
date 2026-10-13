package revxrsal.commands.bukkit.exception;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.util.BukkitUtils;
import revxrsal.commands.exception.DefaultExceptionHandler;
import revxrsal.commands.exception.EnumNotFoundException;
import revxrsal.commands.exception.ExpectedLiteralException;
import revxrsal.commands.exception.InputParseException;
import revxrsal.commands.exception.InvalidBooleanException;
import revxrsal.commands.exception.InvalidDecimalException;
import revxrsal.commands.exception.InvalidHelpPageException;
import revxrsal.commands.exception.InvalidIntegerException;
import revxrsal.commands.exception.InvalidListSizeException;
import revxrsal.commands.exception.InvalidStringSizeException;
import revxrsal.commands.exception.InvalidUUIDException;
import revxrsal.commands.exception.MissingArgumentException;
import revxrsal.commands.exception.NoPermissionException;
import revxrsal.commands.exception.NumberNotInRangeException;
import revxrsal.commands.exception.RuntimeExceptionAdapter;
import revxrsal.commands.exception.UnknownCommandException;
import revxrsal.commands.exception.ValueNotAllowedException;
import revxrsal.commands.node.ParameterNode;

public class BukkitExceptionHandler extends DefaultExceptionHandler<BukkitCommandActor> {
   @RuntimeExceptionAdapter.HandleException
   public void onInvalidPlayer(InvalidPlayerException e, BukkitCommandActor actor) {
      actor.error(BukkitUtils.legacyColorize("&cInvalid player: &e" + e.input() + "&c."));
   }

   @RuntimeExceptionAdapter.HandleException
   public void onInvalidWorld(InvalidWorldException e, BukkitCommandActor actor) {
      actor.error(BukkitUtils.legacyColorize("&cInvalid world: &e" + e.input() + "&c."));
   }

   @RuntimeExceptionAdapter.HandleException
   public void onInvalidWorld(MissingLocationParameterException e, BukkitCommandActor actor) {
      actor.error(BukkitUtils.legacyColorize("&cExpected &e" + e.axis().name().toLowerCase() + "&c."));
   }

   @RuntimeExceptionAdapter.HandleException
   public void onSenderNotConsole(SenderNotConsoleException e, BukkitCommandActor actor) {
      actor.error(BukkitUtils.legacyColorize("&cYou must be the console to execute this command!"));
   }

   @RuntimeExceptionAdapter.HandleException
   public void onSenderNotPlayer(SenderNotPlayerException e, BukkitCommandActor actor) {
      actor.error(BukkitUtils.legacyColorize("&cYou must be a player to execute this command!"));
   }

   @RuntimeExceptionAdapter.HandleException
   public void onMalformedEntitySelector(MalformedEntitySelectorException e, BukkitCommandActor actor) {
      actor.error(BukkitUtils.legacyColorize("&cMalformed entity selector: &e" + e.input() + "&c. Error: &e" + e.errorMessage()));
   }

   @RuntimeExceptionAdapter.HandleException
   public void onNonPlayerEntities(NonPlayerEntitiesException e, BukkitCommandActor actor) {
      actor.error(BukkitUtils.legacyColorize("&cYour entity selector (&e" + e.input() + "&c) only allows players, but it contains non-player entities too."));
   }

   @RuntimeExceptionAdapter.HandleException
   public void onMoreThanOneEntity(MoreThanOneEntityException e, BukkitCommandActor actor) {
      actor.error(BukkitUtils.legacyColorize("&cOnly one entity is allowed, but the provided selector allows more than one"));
   }

   @RuntimeExceptionAdapter.HandleException
   public void onEmptyEntitySelector(EmptyEntitySelectorException e, BukkitCommandActor actor) {
      actor.error(BukkitUtils.legacyColorize("&cNo entities were found."));
   }

   public void onEnumNotFound(@NotNull EnumNotFoundException e, @NotNull BukkitCommandActor actor) {
      actor.error(BukkitUtils.legacyColorize("&cInvalid choice: &e" + e.input() + "&c. Please enter a valid option from the available values."));
   }

   public void onExpectedLiteral(@NotNull ExpectedLiteralException e, @NotNull BukkitCommandActor actor) {
      actor.error(BukkitUtils.legacyColorize("&cExpected &e" + e.node().name() + "&c, found &e" + e.input() + "&c."));
   }

   public void onInputParse(@NotNull InputParseException e, @NotNull BukkitCommandActor actor) {
      switch (e.cause()) {
         case INVALID_ESCAPE_CHARACTER:
            actor.error(BukkitUtils.legacyColorize("&cInvalid input. Use &e\\\\ &cto include a backslash."));
            break;
         case UNCLOSED_QUOTE:
            actor.error(BukkitUtils.legacyColorize("&cUnclosed quote. Make sure to close all quotes."));
            break;
         case EXPECTED_WHITESPACE:
            actor.error(BukkitUtils.legacyColorize("&cExpected whitespace to end one argument, but found trailing data."));
      }

   }

   public void onInvalidListSize(@NotNull InvalidListSizeException e, @NotNull BukkitCommandActor actor, @NotNull ParameterNode<BukkitCommandActor, ?> parameter) {
      if (e.inputSize() < e.minimum()) {
         actor.error(BukkitUtils.legacyColorize("&cYou must input at least &e" + fmt(e.minimum()) + " &centries for &e" + parameter.name() + "&c."));
      }

      if (e.inputSize() > e.maximum()) {
         actor.error(BukkitUtils.legacyColorize("&cYou must input at most &e" + fmt(e.maximum()) + " &centries for &e" + parameter.name() + "&c."));
      }

   }

   public void onInvalidStringSize(@NotNull InvalidStringSizeException e, @NotNull BukkitCommandActor actor, @NotNull ParameterNode<BukkitCommandActor, ?> parameter) {
      if (e.input().length() < e.minimum()) {
         actor.error(BukkitUtils.legacyColorize("&cParameter &e" + parameter.name() + " &cmust be at least &e" + fmt(e.minimum()) + " &ccharacters long."));
      }

      if (e.input().length() > e.maximum()) {
         actor.error(BukkitUtils.legacyColorize("&cParameter &e" + parameter.name() + " &ccan be at most &e" + fmt(e.maximum()) + " &ccharacters long."));
      }

   }

   public void onInvalidBoolean(@NotNull InvalidBooleanException e, @NotNull BukkitCommandActor actor) {
      actor.error(BukkitUtils.legacyColorize("&cExpected &etrue &cor &efalse&c, found &e" + e.input() + "&c."));
   }

   public void onInvalidDecimal(@NotNull InvalidDecimalException e, @NotNull BukkitCommandActor actor) {
      actor.error(BukkitUtils.legacyColorize("&cInvalid number: &e" + e.input() + "&c."));
   }

   public void onInvalidInteger(@NotNull InvalidIntegerException e, @NotNull BukkitCommandActor actor) {
      actor.error(BukkitUtils.legacyColorize("&cInvalid integer: &e" + e.input() + "&c."));
   }

   public void onInvalidUUID(@NotNull InvalidUUIDException e, @NotNull BukkitCommandActor actor) {
      actor.error(BukkitUtils.legacyColorize("&cInvalid UUID: " + e.input() + "&c."));
   }

   public void onMissingArgument(@NotNull MissingArgumentException e, @NotNull BukkitCommandActor actor, @NotNull ParameterNode<BukkitCommandActor, ?> parameter) {
      actor.error(BukkitUtils.legacyColorize("&cRequired parameter is missing: &e" + parameter.name() + "&c. Usage: &e/" + parameter.command().usage() + "&c."));
   }

   public void onNoPermission(@NotNull NoPermissionException e, @NotNull BukkitCommandActor actor) {
      actor.error(BukkitUtils.legacyColorize("&cYou do not have permission to execute this command!"));
   }

   public void onNumberNotInRange(@NotNull NumberNotInRangeException e, @NotNull BukkitCommandActor actor, @NotNull ParameterNode<BukkitCommandActor, Number> parameter) {
      if (e.input().doubleValue() < e.minimum()) {
         actor.error(BukkitUtils.legacyColorize("&c" + parameter.name() + " too small &e(" + fmt(e.input()) + ")&c. Must be at least &e" + fmt(e.minimum()) + "&c."));
      }

      if (e.input().doubleValue() > e.maximum()) {
         actor.error(BukkitUtils.legacyColorize("&c" + parameter.name() + " too large &e(" + fmt(e.input()) + ")&c. Must be at most &e" + fmt(e.maximum()) + "&c."));
      }

   }

   public void onInvalidHelpPage(@NotNull InvalidHelpPageException e, @NotNull BukkitCommandActor actor) {
      if (e.numberOfPages() == 1) {
         actor.error(BukkitUtils.legacyColorize("Invalid help page: &e" + e.page() + "&c. Must be 1."));
      } else {
         actor.error(BukkitUtils.legacyColorize("Invalid help page: &e" + e.page() + "&c. Must be between &e1 &cand &e" + e.numberOfPages()));
      }

   }

   public void onUnknownCommand(@NotNull UnknownCommandException e, @NotNull BukkitCommandActor actor) {
      actor.error(BukkitUtils.legacyColorize("&cUnknown command: &e" + e.input() + "&c."));
   }

   public void onValueNotAllowed(@NotNull ValueNotAllowedException e, @NotNull BukkitCommandActor actor) {
      String allowedValues = String.join("&c, &e", e.allowedValues());
      actor.error(BukkitUtils.legacyColorize("Received an invalid value: &e" + e.input() + "&c. Allowed values: &e" + allowedValues + "&c."));
   }
}

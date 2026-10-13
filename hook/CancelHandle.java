package revxrsal.commands.hook;

public interface CancelHandle {
   boolean wasCancelled();

   void cancel();
}

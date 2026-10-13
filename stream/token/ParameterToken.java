package revxrsal.commands.stream.token;

import java.util.Objects;

public final class ParameterToken implements Token {
   private final String name;

   public ParameterToken(String name) {
      this.name = name;
   }

   public String name() {
      return this.name;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         ParameterToken that = (ParameterToken)obj;
         return Objects.equals(this.name, that.name);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.name});
   }

   public String toString() {
      return "ParameterToken[name=" + this.name + ']';
   }
}

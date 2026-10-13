package revxrsal.commands.stream.token;

import java.util.Objects;

public final class LiteralToken implements Token {
   private final String value;

   public LiteralToken(String value) {
      this.value = value;
   }

   public String value() {
      return this.value;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         LiteralToken that = (LiteralToken)obj;
         return Objects.equals(this.value, that.value);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.value});
   }

   public String toString() {
      return "LiteralToken[value=" + this.value + ']';
   }
}

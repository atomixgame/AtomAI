package sg.atom.ai.testbed.ailang.abl.generated;

import abl.runtime.*;
import wm.WME;
import java.util.*;
import java.lang.reflect.Method;
import abl.learning.*;
import sg.atom.ai.testbed.ailang.abl.wmes.*;
import sg.atom.ai.testbed.ailang.abl.actions.*;
import sg.atom.ai.testbed.ailang.abl.sensors.*;
public class ChaserAgent_SuccessTests {
   static public boolean successTest0(int __$stepID, final Object[] __$behaviorFrame, final BehavingEntity __$thisEntity) {
      switch (__$stepID) {
         case 11: {
            // Wait_1Step2
               if (
                  System.currentTimeMillis() > ((__ValueTypes.LongVar)__$behaviorFrame[1]).l
               )

               {
                  return true;
               }


            return false;
         }
      default:
         throw new AblRuntimeError("Unexpected stepID " + __$stepID);
      }
   }
}
